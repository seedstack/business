/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

import org.seedstack.business.assembler.FactoryArgument;
import org.seedstack.business.domain.AggregateNotFoundException;

/**
 * An element of the {@link FluentAssembler DSL} allowing to specify the behavior when aggregates
 * cannot be found in the repository.
 **/
public interface MergeFromRepositoryOrFactory<T> {

    /**
     * Returns the aggregates or throws an {@link AggregateNotFoundException} if at least one
     * aggregate cannot be loaded from their repository.
     *
     * @return the next element in the DSL.
     * @throws AggregateNotFoundException if an aggregate cannot be retrieved from the repository.
     */
    T orFail() throws AggregateNotFoundException;

    /**
     * Returns the aggregates, allowing to create aggregates with its {@link
     * org.seedstack.business.domain.Factory} when they cannot be loaded from the repository.It uses
     * the {@link FactoryArgument} annotation on the DTO to find the factory method parameters.
     *
     * @return the next element in the DSL.
     */
    T orFromFactory();
}
