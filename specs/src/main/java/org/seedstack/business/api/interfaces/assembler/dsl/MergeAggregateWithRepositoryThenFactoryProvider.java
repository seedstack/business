/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;

import org.seedstack.business.api.domain.AggregateRoot;

/**
 * Specifies the behavior in the case where the aggregate cannot be loaded from the repository.
 *
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public interface MergeAggregateWithRepositoryThenFactoryProvider<A extends AggregateRoot<?>> {

    /**
     * Returns the aggregate root or throws an {@code AggregateNotFoundException}
     * if the aggregate root cannot be loaded from the repository.
     *
     * @return the assembled aggregate root
     * @throws AggregateNotFoundException if the aggregate doesn't exist
     */
    A orFail() throws AggregateNotFoundException;

    /**
     * Returns the aggregate root. If the aggregate root cannot be loaded from the repository,
     * it is created from the factory.
     * <p>
     * It uses the {@link org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the assembled aggregate root
     */
    A orFromFactory();

}
