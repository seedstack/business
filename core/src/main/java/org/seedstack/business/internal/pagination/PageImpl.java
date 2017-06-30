/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.pagination.Page;

import java.util.List;

/**
 * Represents an indexed page of items taken from a bigger list.
 *
 * @param <T> the item type
 */
public class PageImpl<T> extends SliceImpl<T> implements Page<T> {
    private long index;
    private long capacity;
    private long totalSize;

    /**
     * Constructor.
     *
     * @param items     the sub list of item
     * @param index     the page index
     * @param capacity  the maximum capacity of a page
     * @param totalSize the total size of the bigger list.
     */
    public PageImpl(List<T> items, long index, long capacity, long totalSize) {
        super(items);
        this.index = index;
        this.capacity = capacity;
        this.totalSize = totalSize;
    }

    @Override
    public long getIndex() {
        return index;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    @Override
    public long getTotalSize() {
        return totalSize;
    }
}
