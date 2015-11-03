/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import org.seedstack.business.finder.Result;

import java.util.List;

/**
 * Abstract Class with base building blocs for concrete Views.
 *
 * @param <Item> the paginated item
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class AbstractView<Item> implements View<Item> {

    private static final long serialVersionUID = 5881361314603724860L;

    protected final VirtualList<Item> resultList;
    protected final long resultSize;
    protected final long resultViewOffset;

    protected final long resultViewSize;

    /**
     * This constructor take a list of items that can potentially be huge.
     * <p>
     * Usually it will be a VirtualList.
     * </p>
     *
     * @param items            The big list to be viewed.
     * @param resultViewOffset offset inside the big list
     * @param resultViewSize   size of the view inside the big list
     */
    public AbstractView(VirtualList<Item> items, long resultViewOffset, long resultViewSize) {
        this.resultList = items;
        this.resultSize = items.size();
        this.resultViewOffset = resultViewOffset;
        this.resultViewSize = resultViewOffset + resultViewSize > this.resultSize ? (this.resultSize - resultViewOffset) : resultViewSize;
    }

    /**
     * This constructor directly takes a result.
     *
     * @param result           the result containing the items
     * @param resultViewOffset the result view offset
     * @param resultViewSize   the result view size
     */
    public AbstractView(Result<Item> result, long resultViewOffset, long resultViewSize) {
        this(new VirtualList<Item>(result.getResult(), result.getOffset(), result.getFullSize()), resultViewOffset, resultViewSize);
    }

    /**
     * Constructor.
     *
     * @param list             the list of item
     * @param resultViewOffset the result view offset
     * @param resultViewSize   the result view size
     */
    public AbstractView(List<Item> list, long resultViewOffset, long resultViewSize) {
        this(new VirtualList<Item>(list, 0, list.size()), resultViewOffset, resultViewSize);
    }

    /**
     * Constructor
     *
     * @param items            the list of item
     * @param subListStart     the sub list start
     * @param subListSize      the sub list size
     * @param resultViewOffset the result view offset
     * @param resultViewSize   the result view size
     */
    public AbstractView(List<Item> items, long subListStart, long subListSize, long resultViewOffset, long resultViewSize) {
        this(new VirtualList<Item>(items, subListStart, subListSize), resultViewOffset, resultViewSize);
    }

    /**
     * @return the actual view.
     */
    @Override
    public List<Item> getView() {
        return this.resultList.subList(resultViewOffset, resultViewOffset + resultViewSize);
    }

    /**
     * Return the number of element of the complete list, not the view result.
     * <p>
     * The complete list can be uncountable in that case the result will be -1.
     * this can be useful for infinite list handling.
     * </p>
     *
     * @return the size list or -1 if infinite or
     */
    public long getResultSize() {
        return resultSize;
    }
}
