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
 * Abstract Class with base building blocs.
 *
 * @param <Item> the paginated item
 */
public abstract class AbstractPage<Item>  {
    protected List<Item> resultList;
    protected final long resultSize;
    protected final long resultViewOffset;
    protected final long resultViewSize;

    /**
     * This constructor take a list of items that can potentially be huge.
     *
     * @param items            The big list to be viewed.
     * @param resultOffset     Offset inside the big list
     * @param resultViewSize   Size of the view inside the big list
     */
    public AbstractPage(List<Item> items, long resultOffset, long resultViewSize) {
        this.resultList = items;
        this.resultSize = items.size();
        this.resultViewOffset = resultOffset;
        this.resultViewSize = resultViewSize;
    }

    /**
     * @return the actual view.
     */
    public List<Item> getView() {
        return this.resultList;
    }

    /**
     * Return the number of the view result.
     *
     * @return the size list
     */
    public long getResultSize() {
        return resultSize;
    }
}
