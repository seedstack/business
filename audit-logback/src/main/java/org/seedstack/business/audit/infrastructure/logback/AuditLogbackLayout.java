/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.infrastructure.logback;

import javax.el.ELContext;
import javax.inject.Inject;

import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.seed.core.api.Configuration;
import org.seedstack.seed.el.api.ELContextBuilder;
import org.seedstack.seed.el.api.ELService;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

/**
 * LogbackLayout for trace of audit trails.
 * 
 * @author U236838
 */
public class AuditLogbackLayout extends LayoutBase<ILoggingEvent> {

    @Inject
    private ELService elService;

    @Inject
    private ELContextBuilder elContextBuilder;

    @Configuration("org.seedstack.business.audit.logPattern")
    private String elExpression;

    @Override
    public String doLayout(ILoggingEvent loggingEvent) {
        AuditEvent auditEvent = (AuditEvent) loggingEvent.getArgumentArray()[0];
        ELContext elContext = elContextBuilder.defaultContext().withProperty("event", auditEvent).withProperty("trail", auditEvent.getTrail())
                .withProperty("initiator", auditEvent.getTrail().getInitiator()).withProperty("host", auditEvent.getTrail().getHost()).build();
        return elService.withExpression(elExpression, String.class).withContext(elContext).asValueExpression().eval() + CoreConstants.LINE_SEPARATOR;
    }
}