/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.view;

/**
 * Abstraction of a page within a context page set context.
 * <ul>
 * <li>Page have a capacity in term of number of element</li>
 * <li>Page have an actual number of element (needed for the last page of a set of page)</li>
 * <li>Total number of elements of the page set.</li>
 * </ul>
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class Page {

    private final int index;
    private final int capacity;
    private final int numberOfElements;
    private final long totalNumberOfElements;

    /**
     * Create a new page at a certain index, with a certain capacity ,with a maximum number of element (=capacity) and a total
     * number of elements to -1 (infinite).
     * <p>
     * with a number of element maximum.
     * </p>
     *
     * @param pageIndex the page index (start with 0)
     * @param capacity  the number of element
     */
    public Page(int pageIndex, int capacity) {
        this.index = pageIndex;
        this.capacity = capacity;
        this.numberOfElements = capacity;
        this.totalNumberOfElements = -1;
    }

    /**
     * Create a new page at a certain index, with a certain capacity ,with a certain number of element and a total
     * number of elements to -1 (infinite).
     *
     * @param pageIndex        the page index (start with 0)
     * @param capacity         the page capacity size
     * @param numberOfElements the number of element
     */
    public Page(int pageIndex, int capacity, int numberOfElements) {
        this.index = pageIndex;
        this.capacity = capacity;
        this.numberOfElements = numberOfElements;
        this.totalNumberOfElements = -1;
    }

    /**
     * Create a new page at a certain index, with a certain capacity ,with a certain number of element and fixed total
     * number of elements.
     *
     * @param pageIndex             the page index (start with 0)
     * @param capacity              the page capacity size
     * @param numberOfElements      the actual number of element
     * @param totalNumberOfElements the total number of element
     */
    public Page(int pageIndex, int capacity, int numberOfElements, long totalNumberOfElements) {
        this.index = pageIndex;
        this.capacity = capacity;
        this.numberOfElements = numberOfElements;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return the number of element
     */
    public int getNumberOfElements() {
        return numberOfElements;
    }

    /**
     * @return the total number of element
     */
    public long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    /**
     * @return the next page
     */
    public Page next() {
        return new Page(index + 1, capacity, numberOfElements, totalNumberOfElements);
    }

    @Override
    public String toString() {
        return String.format("Page [index=%s, size=%s]", index, capacity);
    }
}