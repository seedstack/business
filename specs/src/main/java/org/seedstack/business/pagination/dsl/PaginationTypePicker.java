/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.pagination.dsl;

/**
 * An element of the {@link Paginator} DSL allowing to choose the pagination type. Three pagination
 * types are supported: <ul> <li>Offset-based which allows to skip a specified amount of
 * objects,</li> <li>Page-based which is similar to offset-based but allows to specify the amount of
 * skipped objects as a number of pages,</li> <li>Key-based which allows to select objects that come
 * after a specific value of a specific attribute.</li> </ul>
 *
 * @param <T> the type of the paginated object.
 */
public interface PaginationTypePicker<T> extends SlicePaginationPicker<T> {

    /**
     * Choose a page-based pagination type. Objects that come before the beginning of specified page
     * will be skipped.
     *
     * @param pageIndex the index of the page containing objects that will be returned.
     * @return the next operation of the paginator DSL, allowing to specify page size.
     */
    SizePicker<T> byPage(long pageIndex);

}
