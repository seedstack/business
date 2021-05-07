/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination;

import java.util.List;

/**
 * Represents an indexed page of items taken from a bigger list.
 *
 * @param <T> the item type
 */
public class SimplePage<T> extends SimpleSlice<T> implements Page<T> {

    private final long index;
    private final long maxSize;
    private final long totalSize;

    /**
     * Constructor.
     *
     * @param items     the sub list of item
     * @param index     the page index
     * @param maxSize   the maximum size of a page
     * @param totalSize the total size of the bigger list.
     */
    public SimplePage(List<T> items, long index, long maxSize, long totalSize) {
        super(items);
        this.index = index;
        this.maxSize = maxSize;
        this.totalSize = totalSize;
    }

    @Override
    public long getIndex() {
        return index;
    }

    @Override
    public long getMaxSize() {
        return maxSize;
    }

    @Override
    public long getTotalSize() {
        return totalSize;
    }
}
