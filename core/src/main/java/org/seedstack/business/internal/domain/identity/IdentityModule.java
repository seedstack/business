/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.identity;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.identity.IdentityHandler;
import org.seedstack.business.domain.identity.IdentityService;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.core.internal.utils.MethodMatcherBuilder;
import org.seedstack.shed.reflect.ClassPredicates;
import org.seedstack.shed.reflect.Classes;
import org.seedstack.shed.reflect.ExecutablePredicates;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

class IdentityModule extends AbstractModule {
    private final Collection<Class<? extends IdentityHandler>> identityHandlerClasses;

    IdentityModule(Collection<Class<? extends IdentityHandler>> identityHandlerClasses) {
        this.identityHandlerClasses = identityHandlerClasses;
    }

    @Override
    protected void configure() {
        bindIdentityHandler();
        bind(IdentityService.class).to(IdentityServiceInternal.class);
        IdentityInterceptor identityInterceptor = new IdentityInterceptor();
        requestInjection(identityInterceptor);
        bindInterceptor(Matchers.subclassesOf(Factory.class), factoryMethods(), identityInterceptor);
    }

    private void bindIdentityHandler() {
        for (Class<? extends IdentityHandler> identityHandlerClass : identityHandlerClasses) {
            bind(identityHandlerClass);
            Named named = identityHandlerClass.getAnnotation(Named.class);
            if (named != null) {
                bind(findIdentityHandlerInterface(identityHandlerClass)).annotatedWith(Names.named(named.value())).to(identityHandlerClass);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Class<IdentityHandler> findIdentityHandlerInterface(Class<? extends IdentityHandler> identityHandlerClass) {
        Optional<Class<?>> first = Classes.from(identityHandlerClass)
                .traversingInterfaces()
                .traversingSuperclasses()
                .classes()
                .filter(ClassPredicates.classIsInterface().and(ClassPredicates.classIsAssignableFrom(IdentityHandler.class)))
                .findFirst();

        if (first.isPresent()) {
            return (Class<IdentityHandler>) first.get();
        } else {
            throw SeedException.createNew(BusinessErrorCode.ILLEGAL_IDENTITY_HANDLER).put("class", identityHandlerClass.getName());
        }
    }

    private Matcher<Method> factoryMethods() {
        return new MethodMatcherBuilder(ExecutablePredicates.<Method>executableBelongsToClassAssignableTo(Factory.class)
                .and(CreateResolver.INSTANCE)
        ).build();
    }
}
