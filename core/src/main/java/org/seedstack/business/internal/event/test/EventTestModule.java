/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import org.seedstack.business.test.EventFixture;
import org.seedstack.business.EventHandler;
import org.seedstack.seed.Install;

import java.lang.reflect.Method;

/**
 * Module for event fixtures.
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
@Install
class EventTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventFixture.class).to(EventFixtureInternal.class);
        bind(ContextLink.class).in(Scopes.SINGLETON);
        EventHandlerInterceptor interceptor = new EventHandlerInterceptor();
        requestInjection(interceptor);
        bindInterceptor(Matchers.subclassesOf(EventHandler.class), handlerMethod(), interceptor);
    }

    Matcher<Method> handlerMethod() {

        return new AbstractMatcher<Method>() {

            @Override
            public boolean matches(Method candidate) {
                return "handle".equals(candidate.getName()) && !candidate.isSynthetic();
            }

        };
    }


}
