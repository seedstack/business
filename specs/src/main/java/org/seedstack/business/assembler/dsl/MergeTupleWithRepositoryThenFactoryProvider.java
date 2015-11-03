/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.javatuples.Tuple;

/**
 * Specifies the behavior in the case where the aggregate tuple cannot be loaded from the repository.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface MergeTupleWithRepositoryThenFactoryProvider<T extends Tuple> {

    /**
     * Returns the aggregate root tuple or throws an {@code AggregateNotFoundException}
     * if the one of the aggregate roots cannot be loaded from their repository.
     *
     * @return the assembled aggregate root
     * @throws AggregateNotFoundException if the aggregate doesn't exist
     */
    T orFail() throws AggregateNotFoundException;

    /**
     * Returns the aggregate root tuple. If the all the aggregate roots cannot be loaded from their repository,
     * they are created from their factory. If some of the aggregate roots can be loaded but not all an
     * IllegalStateException is thrown.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the assembled aggregate root tuple
     * @throws IllegalStateException if some but not all aggregate roots are loaded
     */
    T orFromFactory();
}
