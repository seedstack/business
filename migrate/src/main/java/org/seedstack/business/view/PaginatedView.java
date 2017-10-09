/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.view;

import java.util.List;
import org.seedstack.business.finder.Result;

/**
 * A page oriented View, that brings page behaviour out of the box.
 *
 * @param <I> the paginated item type
 */
@Deprecated
public class PaginatedView<I> extends AbstractView<I> {

    private final long pageIndex;

    private final long pagesCount;

    /**
     * Constructor.
     *
     * @param items     the list of item
     * @param pageSize  the number of item per page
     * @param pageIndex the page index
     */
    public PaginatedView(List<I> items, long pageSize, long pageIndex) {
        super(items, pageIndex * pageSize, pageSize);
        this.pageIndex = pageIndex;
        this.pagesCount = countPages(pageSize, this.resultSize);
    }

    /**
     * Constructor.
     *
     * @param result    the result containing the list of item
     * @param pageSize  the number of item per page
     * @param pageIndex the page index
     */
    public PaginatedView(Result<I> result, long pageSize, long pageIndex) {
        super(result, pageIndex * pageSize, pageSize);
        this.pageIndex = pageIndex;
        this.pagesCount = countPages(pageSize, this.resultSize);
    }

    /**
     * Constructor.
     *
     * @param result the result containing the list of item
     * @param page   the {@link Page}
     */
    public PaginatedView(Result<I> result, Page page) {
        this(result, page.getCapacity(), page.getIndex());
    }

    /**
     * Constructor.
     *
     * @param subList      the sub list of item
     * @param subListStart the first item of the sub list
     * @param realListSize the complete list
     * @param pageSize     the number of item per page
     * @param pageIndex    the page index
     */
    public PaginatedView(List<I> subList, long subListStart, long realListSize, long pageSize, long pageIndex) {
        super(subList, subListStart, realListSize, pageIndex * pageSize, pageSize);
        this.pageIndex = pageIndex;
        this.pagesCount = countPages(pageSize, this.resultSize);
    }

    private long countPages(long pageSize, long totalItems) {
        if (pageSize == 0) {
            throw new IllegalArgumentException("View cannot be computed with a page size of 0");
        }
        return (long) Math.ceil((double) totalItems / pageSize);
    }

    /**
     * Indicates whether the current page has a next page.
     *
     * @return true is the current page is not the last one, false otherwise
     */
    public boolean hasNext() {
        return pageIndex + 1 < pagesCount;
    }

    /**
     * Returns the next page if it exists.
     *
     * @return the next page or null if there is no last page
     */
    public Page next() {
        if (hasNext()) {
            return new Page(pageIndex + 1, resultViewSize);
        } else {
            return null;
        }
    }

    /**
     * Indicates whether the current page has a previous page.
     *
     * @return true is the current page is not the first one, false otherwise.
     */
    public boolean hasPrev() {
        return pageIndex > 0;
    }

    /**
     * Returns the previous page if it exists.
     *
     * @return the next page or null if there is no last page.
     */
    public Page prev() {
        if (hasPrev()) {
            return new Page(pageIndex - 1, resultViewSize);
        } else {
            return null;
        }
    }

    /**
     * Returns the page size.
     *
     * @return the number of item per page.
     */
    public long getPageSize() {
        return this.resultViewSize;
    }

    /**
     * Returns the page index.
     *
     * @return the page index.
     */
    public long getPageIndex() {
        return pageIndex;
    }

    /**
     * Returns the total number of pages.
     *
     * @return the number of page.
     */
    public long getPagesCount() {
        return pagesCount;
    }
}