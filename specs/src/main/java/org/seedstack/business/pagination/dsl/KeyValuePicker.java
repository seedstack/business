/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination.dsl;

import org.seedstack.business.pagination.Slice;

/**
 * An element of the {@link Paginator} DSL allowing to specify the value used as boundary in the
 * case of a key-based pagination.
 *
 * @param <T> the type of the paginated object.
 */
public interface KeyValuePicker<T> {

    /**
     * Specify the value used as upper-boundary of the previously specified attribute. Objects having
     * an attribute value that is greater than or equal to specified value will be skipped.
     *
     * @param value the value used as upper-boundary.
     * @param <C>   the type of the value.
     * @return the next operation of the paginator DSL, allowing to specify a limit to the number of
     *         objects returned.
     */
    <C extends Comparable<? super C>> LimitPicker<Slice<T>, T> before(C value);

    /**
     * Specify the value used as lower-boundary of the previously specified attribute. Objects having
     * an attribute value that is less than or equal to specified value will be skipped.
     *
     * @param value the value used as lower-boundary.
     * @param <C>   the type of the value.
     * @return the next operation of the paginator DSL, allowing to specify a limit to the number of
     *         objects returned.
     */
    <C extends Comparable<? super C>> LimitPicker<Slice<T>, T> after(C value);
}
