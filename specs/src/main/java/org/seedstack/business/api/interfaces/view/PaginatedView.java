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

import org.seedstack.business.api.interfaces.finder.Result;

import java.util.List;

/**
 * A page oriented View, that brings page behaviour out of the box.
 *
 * @param <Item> the paginated item type
 * @author epo.jemba@ext.mpsa.com
 */
public class PaginatedView<Item> extends AbstractView<Item> {

    private static final long serialVersionUID = 8025757060932521691L;

    private final long pageIndex;

    private final long pagesCount;

    /**
     * Constructor.
     *
     * @param items     the list of item
     * @param pageSize  the number of item per page
     * @param pageIndex the page index
     */
    public PaginatedView(List<Item> items, int pageSize, long pageIndex) {
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
    public PaginatedView(Result<Item> result, int pageSize, long pageIndex) {
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
    public PaginatedView(Result<Item> result, Page page) {
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
    public PaginatedView(List<Item> subList, long subListStart, long realListSize, int pageSize, long pageIndex) {
        super(subList, subListStart, realListSize, pageIndex * pageSize, pageSize);
        this.pageIndex = pageIndex;
        this.pagesCount = countPages(pageSize, this.resultSize);
    }

    private long countPages(int size, long totalItemsCount2) {
        if (size == 0) {
            throw new IllegalArgumentException("View cannot be computed with a page size of 0");
        }
        return (long) Math.ceil((double) totalItemsCount2 / size);
    }

    /**
     * @return the number of item per page
     */
    public int getPageSize() {
        return this.resultViewSize;
    }

    /**
     * @return the page index
     */
    public long getPageIndex() {
        return pageIndex;
    }

    /**
     * @return the number of page
     */
    public long getPagesCount() {
        return pagesCount;
    }
}