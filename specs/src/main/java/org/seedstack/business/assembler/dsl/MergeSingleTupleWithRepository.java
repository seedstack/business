/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.javatuples.Tuple;

/**
 * Specifies whether the aggregate root tuple should be retrieved from a repository or created from a factory.
 */
public interface MergeSingleTupleWithRepository<T extends Tuple> {

    /**
     * Loads the aggregate root tuple from their repository.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingEntityId} annotation on
     * the DTO to find the aggregate IDs.
     * </p>
     *
     * @return next DSL element
     */
    MergeSingleTupleFromRepositoryOrFactory<T> fromRepository();

    /**
     * Create the aggregate root tuple from their factory.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the list of aggregate tuple
     */
    T fromFactory();
}
