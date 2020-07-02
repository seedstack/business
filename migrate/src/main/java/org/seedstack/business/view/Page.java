/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.view;

/**
 * Abstraction of a page within a context page set context.
 * Pages have:
 * <ul>
 * <li>an index,</li>
 * <li>a capacity in term of number of element.</li>
 * </ul>
 */
@Deprecated
public class Page {
    private final long index;
    private final long capacity;

    /**
     * Creates a new page at a certain index and a certain capacity.
     *
     * @param pageIndex the page index (start with 0)
     * @param capacity  the number of element
     */
    public Page(long pageIndex, long capacity) {
        this.index = pageIndex;
        this.capacity = capacity;
    }

    /**
     * Returns the page index.
     *
     * @return the page index
     */
    public long getIndex() {
        return index;
    }

    /**
     * Returns the page capacity.
     *
     * @return the capacity of the page
     */
    public long getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return String.format("Page [index=%s, size=%s]", index, capacity);
    }
}