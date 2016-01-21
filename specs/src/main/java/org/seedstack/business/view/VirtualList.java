/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import java.util.ArrayList;
import java.util.List;

/**
 * A virtual list that can represents huge portion.
 *
 * @param <T> the item type
 * @author epo.jemba@ext.mpsa.com
 */
class VirtualList<T> {

    private List<T> subList;
    private long subListOffset;
    private long fullListSize;

    /**
     * The Constructor.
     *
     * @param subList       the sub list of item
     * @param subListOffset the sub list offset
     * @param fullListSize  the total number of item available
     */
    public VirtualList(List<T> subList, long subListOffset, long fullListSize) {
        this.subList = subList;
        this.subListOffset = subListOffset;
        this.fullListSize = fullListSize;
    }

    /**
     * Gets the item at the index.
     *
     * @param index the request index
     * @return the item
     */
    public T get(long index) {
        checkRange(index);

        if (checkSubRange(index)) {
            // cannot exceed Integer.MAX_VALUE (checked by checkSubRange)
            return subList.get((int) (index - subListOffset));
        }
        return null;
    }

    /**
     * Returns a portion of this list between the specified from, inclusive, and to, exclusive.
     *
     * @param from low endpoint (inclusive) of the subList
     * @param to   high endpoint (exclusive) of the subList
     * @return the sub list of item
     */
    public List<T> subList(long from, long to) {
        if (fullListSize > 0 && to - from > 0) {
            checkRange(from);
            // subList takes a [from;to[ range
            checkRange(to - 1);

            // check if the data of the required sub list are available
            assertSubRange(from, to - 1);

            // cannot exceed Integer.MAX_VALUE (checked by checkSubRange)
            return subList.subList((int) (from - subListOffset), (int) (to - subListOffset));
        }

        return new ArrayList<T>();
    }

    /**
     * @return the total number of item available
     */
    public long size() {
        return this.fullListSize;
    }

    /**
     * Checks if the index is strictly positive and lower than the full list size.
     *
     * @param index the index to check
     * @throws java.lang.IllegalStateException if the index is out of range
     */
    private void checkRange(long index) {
        if (index < 0 || index >= this.fullListSize) {
            throw new IllegalStateException("Out of range access to virtual list: " + index);
        }
    }

    /**
     * Checks if the index is greater than the sub list offset and lower than the sum of the sub
     * list offset and the sub list size.
     *
     * @param index the index to check
     * @return true if the index is not out of range, false otherwise
     */
    private boolean checkSubRange(long index) {
        return this.subListOffset <= index && index < (this.subListOffset + this.subList.size());
    }

    /**
     * Asserts that the requested sub list is available in the virtual list. Otherwise it throws
     * an IndexOutOfBoundsException.
     *
     * @param from the from index (inclusive)
     * @param to   the to index (inclusive)
     */
    private void assertSubRange(long from, long to) {
        if (!checkSubRange(from) || !checkSubRange(to)) {
            throw new IndexOutOfBoundsException("Required data for the sub list [" + from + "," + (to + 1) + "[ have not been loaded in the virtual list.");
        }
    }
}
