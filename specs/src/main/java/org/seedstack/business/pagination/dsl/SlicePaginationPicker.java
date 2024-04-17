/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination.dsl;

import org.seedstack.business.pagination.Slice;

/**
 * An element of the {@link Paginator} DSL allowing to choose the pagination type. Two pagination
 * types are supported: <ul> <li>Offset-based which allows to skip a specified amount of
 * objects,</li> <li>Key-based which allows to select objects that come
 * after a specific value of a specific attribute.</li> </ul>
 *
 * @param <T> the type of the paginated object.
 */
public interface SlicePaginationPicker<T> {
    /**
     * Choose an offset-based pagination type. Objects that come before the specified index will be
     * skipped.
     *
     * @param startingOffset the index of first object that will be returned.
     * @return the next operation of the paginator DSL, allowing to specify a limit to the number of
     * objects returned.
     */
    LimitPicker<Slice<T>, T> byOffset(long startingOffset);

    /**
     * Choose a key-based pagination type.
     *
     * @param attributeName the attribute on which the lessThan/greaterThan comparison will be made.
     * @return the next operation of the paginator DSL, allowing to specify the value used as
     * boundary.
     */
    KeyValuePicker<T> byAttribute(String attributeName);
}
