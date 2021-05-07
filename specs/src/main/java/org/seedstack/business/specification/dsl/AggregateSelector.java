/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

import org.seedstack.business.domain.AggregateRoot;

/**
 * An element of the {@link SpecificationBuilder} DSL to select the identity of the aggregate a
 * clause will apply to.
 *
 * @param <A> the type of the aggregate
 * @param <I> the type of the identifier of the aggregate.
 * @param <S> the type of the selector.
 */
public interface AggregateSelector<A extends AggregateRoot<I>, I, S extends AggregateSelector<A, I, S>> extends
        PropertySelector<A, S> {

    /**
     * Selects the identity of the aggregate to be the subject of a specification.
     *
     * @return the next operation of the builder DSL, allowing to choose the specification.
     */
    IdentityPicker<A, I, S> identity();
}
