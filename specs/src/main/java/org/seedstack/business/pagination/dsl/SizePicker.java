/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination.dsl;

import org.seedstack.business.pagination.Page;

/**
 * An element of the {@link Paginator} DSL allowing to specify the size of the page in the case of
 * page-based pagination.
 *
 * @param <T> the type of the paginated object.
 */
public interface SizePicker<T> extends LimitPicker<Page<T>, T> {

    /**
     * Specify the size of the page.
     *
     * @param size the size of a page.
     * @return the next operation of the paginator DSL, allowing to pick a specification for selecting
     * objects returned from the repository.
     */
    default SpecificationPicker<Page<T>, T> ofSize(long size) {
        return limit(size);
    }
}
