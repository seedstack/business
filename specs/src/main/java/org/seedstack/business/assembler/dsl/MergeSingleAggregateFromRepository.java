/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.domain.AggregateRoot;

/**
 * Specifies whether the aggregate should be retrieved from its repository or created from its factory.
 * <p>
 * When retrieving the aggregate from its repository, the {@link org.seedstack.business.assembler.MatchingEntityId} annotation(s) placed on
 * the DTO will be used to determine the aggregate identifier.
 * </p>
 * <p>
 * When creation the aggregate from its factory, the {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotation(s) placed on
 * the DTO will be used to determine the factory parameters.
 * </p>
 **/
public interface MergeSingleAggregateFromRepository<A extends AggregateRoot<?>> {

    /**
     * Retrieves the aggregate from its repository, using no qualifier or the default repository qualifier if configured.
     *
     * @return next DSL element
     */
    MergeSingleAggregateFromRepositoryOrFactory<A> fromRepository();

//    /**
//     * Retrieves the aggregate from its repository, using the specified qualifier.
//     *
//     * @param qualifier the qualifier of the repository implementation.
//     * @return next DSL element
//     */
//    MergeSingleAggregateFromRepositoryOrFactory<A> fromRepository(Class<? extends Annotation> qualifier);
//
//    /**
//     * Retrieves the aggregate from its repository, using the a named qualifier with the specified name.
//     *
//     * @param namedQualifier the name used as qualifier of the repository implementation.
//     * @return next DSL element
//     */
//    MergeSingleAggregateFromRepositoryOrFactory<A> fromRepository(String namedQualifier);

    /**
     * Creates the aggregate from its factory, using no qualifier or the default factory qualifier if configured.
     *
     * @return the aggregate
     */
    A fromFactory();

//    /**
//     * Creates the aggregate from its factory, using the specified qualifier.
//     *
//     * @param qualifier the qualifier of the factory implementation.
//     * @return the aggregate
//     */
//    A fromFactory(Class<? extends Annotation> qualifier);
//
//    /**
//     * Creates the aggregate from its factory, using a named qualifier with the specified name.
//     *
//     * @param namedQualifier the name used as qualifier of the factory implementation.
//     * @return the aggregate
//     */
//    A fromFactory(String namedQualifier);

}
