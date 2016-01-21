/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.events;

import org.seedstack.business.domain.AggregateRoot;

import java.lang.reflect.Method;

/**
 * This event is fired each time method from a subtype of Repository annotated with {@code @Read}, or
 * {@code @Delete}, or {@code @Persist} is called. The event contains the intercepted method with its arguments.
 * <p>
 * To received this event the interception on repository should be enable by adding the following properties in a props file.
 * </p>
 * <pre>
 * org.seedstack.business.event.domain.watch=true
 * </pre>
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
public abstract class BaseAggregateEvent extends DomainEvent {


	private static final long serialVersionUID = 4247250155695647756L;

	private final Class<? extends AggregateRoot<?>> aggregateRoot;

    private final Context context;

    /**
     * Constructor.
     *
     * @param methodCalled  intercepted method
     * @param args          arguments of the intercepted method
     * @param aggregateRoot aggregate root class concern by the event
     */
    public BaseAggregateEvent(Method methodCalled, Object[] args, Class<? extends AggregateRoot<?>> aggregateRoot) {
        this.context = new Context(methodCalled, args.clone());
        this.aggregateRoot = aggregateRoot;
    }

    /**
     * Gets the aggregate root concern by the event
     *
     * @return aggregate root class
     */
    public Class<? extends AggregateRoot<?>> getAggregateRoot() {
        return aggregateRoot;
    }

    /**
     * @return the interception context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Interception context with the method called and its arguments.
     */
    public final class Context {

        private final Object[] args;

        private final Method methodCalled;

        private Context(Method methodCalled, Object[] args) {
            this.methodCalled = methodCalled;
            this.args = args.clone();
        }

        /**
         * Gets the arguments passed to the called method.
         *
         * @return array of arguments
         */
        public Object[] getArgs() {
            return args;
        }

        /**
         * Gets the repository's method which was called.
         *
         * @return a method
         */
        public Method getMethodCalled() {
            return methodCalled;
        }

    }
}
