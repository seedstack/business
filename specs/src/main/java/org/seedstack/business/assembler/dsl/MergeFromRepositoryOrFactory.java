/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.domain.AggregateNotFoundException;

/**
 * Specifies the behavior in the case where the aggregates cannot be loaded from the repository.
 **/
public interface MergeFromRepositoryOrFactory<T> {

    /**
     * Returns the aggregate roots or throws an {@code AggregateNotFoundException}
     * if one of the aggregate roots cannot be loaded from their repository.
     *
     * @return the stream of merged aggregate roots.
     * @throws AggregateNotFoundException if the aggregate doesn't exist
     */
    T orFail() throws AggregateNotFoundException;

    /**
     * Returns the aggregate roots, allowing them to come both from repository and factory.
     * <p>
     * It uses the {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the stream of merged aggregate roots
     */
    T orFromFactory();

}
