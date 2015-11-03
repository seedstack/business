/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.javatuples.Tuple;

import java.util.List;

/**
 * Specifies whether the aggregate root tuples should be retrieved from a repository or created from a factory.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface MergeTuplesWithRepositoryProvider<T extends Tuple> {

    /**
     * Loads the aggregate root tuples from their repository.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingEntityId} annotation on
     * the DTO to find the aggregate IDs.
     * </p>
     *
     * @return next DSL element
     */
    MergeTuplesWithRepositoryThenFactoryProvider<T> fromRepository();

    /**
     * Create the aggregate root tuples from their factory.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the list of aggregate root tuples
     */
    List<T> fromFactory();
}
