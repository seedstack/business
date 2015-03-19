/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.internal;

import java.util.Set;

import javax.el.ELContext;
import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.jodah.typetools.TypeResolver;
import org.seedstack.business.audit.api.AuditService;
import org.seedstack.business.audit.api.Trail;
import org.seedstack.business.audit.api.TrailExceptionHandler;
import org.seedstack.business.audit.api.annotations.Audited;
import org.seedstack.seed.el.api.ELContextBuilder;
import org.seedstack.seed.el.api.ELContextBuilder.ELPropertyProvider;
import org.seedstack.seed.el.api.ELService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor intercepts methods annotated with Audited annotation.
 * 
 * @author U236838
 */
public class AuditedInterceptor implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AuditedInterceptor.class);

    private static final ThreadLocal<AuditContext> THREAD_LOCAL = new ThreadLocal<AuditContext>() {
        @Override
        protected AuditContext initialValue() {
            return new AuditContext();
        }
    };

    @Inject
    private AuditService auditService;

    @Inject
    private Set<TrailExceptionHandler> exceptionHandlers;

    @Inject
    private ELService elService;

    @Inject
    private ELContextBuilder elContextBuilder;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Audited annotation = invocation.getMethod().getAnnotation(Audited.class);
        if (annotation == null) {
            return invocation.proceed();
        }

        AuditContext context = THREAD_LOCAL.get();
        Trail trail = context.getTrail();
        if (trail == null) {
            trail = auditService.createTrail();
            context.setTrail(trail);
        }
        context.nbNestedAudits++;

        ELPropertyProvider argsPropertyProvider = elContextBuilder.defaultContext().withProperty("args", invocation.getArguments());

        String messageBefore = annotation.messageBefore();

        if (!StringUtils.isEmpty(messageBefore)) {
            try {
                String evaluatedMessage = (String) elService.withExpression(messageBefore, String.class).withContext(argsPropertyProvider.build())
                        .asValueExpression().eval();
                auditService.trail(evaluatedMessage, trail);
            } catch (Exception e) {
                LOG.error("Audit error (does not affect execution) : could not write before action", e);
            }
        }

        try {
            Object result = invocation.proceed();
            try {
                ELContext elContext = argsPropertyProvider.withProperty("result", result).build();
                String evaluatedMessage = elService.withExpression(annotation.messageAfter(), String.class).withContext(elContext)
                        .asValueExpression().eval().toString();
                auditService.trail(evaluatedMessage, trail);
            } catch (Exception e) {
                LOG.error("Audit error (does not affect execution) : could not write after action", e);
            }
            return result;
        } catch (Exception e) {
            try {
                if (!context.getAuditedExceptions().contains(e)) {
                    context.getAuditedExceptions().add(e);
                    ELContext elContext = argsPropertyProvider.withProperty("exception", e).build();
                    boolean handled = false;
                    for (TrailExceptionHandler handler : exceptionHandlers) {
                        Class<?> handledException = TypeResolver.resolveRawArgument(TrailExceptionHandler.class, handler.getClass());
                        if (handledException.isAssignableFrom(e.getClass())) {
                            handled = true;
                            String message = (String) elService.withExpression(handler.describeException(e), String.class).withContext(elContext)
                                    .asValueExpression().eval();
                            auditService.trail(message, trail);
                        }
                    }
                    if (!handled) {
                        String message;
                        if (!StringUtils.isEmpty(annotation.messageOnException())) {
                            message = annotation.messageOnException();
                        } else {
                            message = "Audited action threw an exception : " + e.getClass() + " : " + e.getMessage();
                        }
                        auditService.trail(
                                (String) elService.withExpression(message, String.class).withContext(elContext).asValueExpression().eval(), trail);
                    }
                }
                // Do nothing if already contains because it was already handled by a deeper AuditedInterceptor
            } catch (Exception auditException) {
                LOG.error("Audit error (does not affect execution) : could not write exception", auditException);
                throw e;
            }
            throw e;
        } finally {
            context.nbNestedAudits--;
            if (context.nbNestedAudits == 0) {
                THREAD_LOCAL.remove();
            }
        }
    }
}
