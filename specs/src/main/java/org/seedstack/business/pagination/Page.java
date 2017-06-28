/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination;

import java.util.List;

/**
 * Abstraction of a page within a context page set context.
 * Pages have:
 * <ul>
 *   <li>an index,</li>
 *   <li>a capacity in term of number of element.</li>
 * </ul>
 */
public class Page<Item> extends AbstractPage<Item> {

    private long index;
    private long capacity;

    /**
     * Constructor.
     *
     * @param subList           the sub list of item
     * @param resultOffset      offset inside the big list
     * @param capacity          the initial capacity of a page
     * @param pageIndex         the page index
     */
    public Page(List<Item> subList, long resultOffset, long pageIndex, long capacity, long resultViewSize) {
        super(subList, resultOffset, resultViewSize);
        this.index = pageIndex;
        this.capacity = capacity;
    }

    /**
     * @return the page index
     */
    public long getIndex() {
        return index;
    }

    /**
     * @return the capacity of the page
     */
    public long getCapacity() {
        return capacity;
    }

    public long getResultViewSize() {
        return resultViewSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "index=" + index +
                ", capacity=" + capacity +
                ", resultSize=" + resultSize +
                ", resultOffset=" + resultViewOffset +
                '}';
    }
}
