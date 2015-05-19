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
 * Specifies whether the aggregate root should be retrieved from a repository or created from a factory.
 *
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public interface AggAssemblerWithRepoProvider<A extends AggregateRoot<?>> {

    /**
     * Loads the aggregate from its repository.
     * <p>
     * It uses the {@link org.seedstack.business.api.interfaces.assembler.MatchingEntityId} annotation on
     * the DTO to find the aggregate ID.
     * </p>
     *
     * @return next DSL element
     */
    AggAssemblerWithRepoAndFactProvider<A> fromRepository();

    /**
     * Create the aggregate from its factory.
     * <p>
     * It uses the {@link org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter} annotation on
     * the DTO to find the factory method parameters.
     * </p>
     *
     * @return the aggregate
     */
    A fromFactory();

}
