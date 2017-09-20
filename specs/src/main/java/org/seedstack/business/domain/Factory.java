/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;


/**
 * A factory is responsible for creating a whole, internally consistent aggregate when it is too complicated to do it
 * in a constructor.
 *
 * <p>
 * A factory is part of the domain and responsible for creating some domain objects. In the business framework a
 * factory can only create domain objects implementing {@link Producible}:
 * </p>
 * <ul>
 * <li>Aggregates through their aggregate root,</li>
 * <li>Value objects,</li>
 * <li>Domain events.</li>
 * </ul>
 * <p>
 * Note that non-root entities are not produced by factories but should be created by their aggregate root.
 * </p>
 *
 * <p>
 * Being responsible for creating valid aggregates, factories may need to create their identity. This can be done
 * from input parameters given to the factory or by using a generation mechanism. This mechanism can be automated by the
 * business framework, see below.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 * <pre>
 * public interface SomeFactory extends Factory&lt;SomeAggregate&gt; {
 *     SomeAggregate createFromName(String name);
 * }
 *
 * public class SomeFactoryImpl implements SomeFactory {
 *     SomeAggregate createFromName(String name) {
 *         // create and return the aggregate
 *     }
 * }
 * </pre>
 *
 * @param <P> the type of the produced object.
 */
@DomainFactory
public interface Factory<P extends Producible> {
    /**
     * @return the produced class
     */
    Class<P> getProducedClass();

    /**
     * Creates a domain object.
     *
     * @param args arguments
     * @return an instance of DomainObject
     */
    default P create(Object... args) {
        throw new UnsupportedOperationException("Generic creation is not supported by this factory");
    }
}

