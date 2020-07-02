/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.pagination.dsl;

import org.seedstack.business.pagination.Slice;

/**
 * An element of the {@link Paginator} DSL allowing to specify a limit on the number of objects
 * returned, in the case of an offset-based pagination.
 *
 * @param <S> the type of the slice.
 * @param <T> the type of the paginated object.
 */
public interface LimitPicker<S extends Slice<T>, T> extends SpecificationPicker<S, T> {

    /**
     * Specify a limit on the number of objects returned.
     *
     * @param limit the limit.
     * @return the next operation of the paginator DSL, allowing to pick a specification for selecting
     * objects returned from the repository.
     */
    SpecificationPicker<S, T> limit(long limit);
}
