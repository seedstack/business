/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.domain.AggregateRoot;

/**
 * Specifies whether the aggregate root should be retrieved from a repository or created from a factory.
 *
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public interface MergeAggregateWithRepositoryProvider<A extends AggregateRoot<?>> {

    /**
     * Loads the aggregate from its repository.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingEntityId} annotation on
     * the DTO to find the aggregate ID.
     * </p>
     *
     * @return next DSL element
     */
    MergeAggregateWithRepositoryThenFactoryProvider<A> fromRepository();

    /**
     * Create the aggregate from its factory.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the aggregate
     */
    A fromFactory();

}
