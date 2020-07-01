/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import java.util.List;
import org.seedstack.business.finder.Result;

/**
 * Abstract Class with base building blocs for concrete Views.
 *
 * @param <I> the paginated item
 */
@Deprecated
public abstract class AbstractView<I> implements View<I> {
    private static final long serialVersionUID = 1L;

    protected final VirtualList<I> resultList;
    protected final long resultSize;
    protected final long resultViewOffset;

    protected final long resultViewSize;

    /**
     * Constructs an abstract view.
     *
     * @param items            The big list to be viewed.
     * @param resultViewOffset offset inside the big list
     * @param resultViewSize   size of the view inside the big list
     */
    public AbstractView(VirtualList<I> items, long resultViewOffset, long resultViewSize) {
        this.resultList = items;
        this.resultSize = items.size();
        this.resultViewOffset = resultViewOffset;
        this.resultViewSize = resultViewOffset + resultViewSize > this.resultSize ? (this.resultSize
                - resultViewOffset) : resultViewSize;
    }

    /**
     * Constructs an abstract view.
     *
     * @param result           the result containing the items
     * @param resultViewOffset the result view offset
     * @param resultViewSize   the result view size
     */
    public AbstractView(Result<I> result, long resultViewOffset, long resultViewSize) {
        this(new VirtualList<>(result.getResult(), result.getOffset(), result.getFullSize()),
                resultViewOffset,
                resultViewSize);
    }

    /**
     * Constructs an abstract view.
     *
     * @param list             the list of item
     * @param resultViewOffset the result view offset
     * @param resultViewSize   the result view size
     */
    public AbstractView(List<I> list, long resultViewOffset, long resultViewSize) {
        this(new VirtualList<>(list, 0, list.size()), resultViewOffset, resultViewSize);
    }

    /**
     * Constructs an abstract view.
     *
     * @param items            the list of item
     * @param subListStart     the sub list start
     * @param subListSize      the sub list size
     * @param resultViewOffset the result view offset
     * @param resultViewSize   the result view size
     */
    public AbstractView(List<I> items, long subListStart, long subListSize, long resultViewOffset,
            long resultViewSize) {
        this(new VirtualList<>(items, subListStart, subListSize), resultViewOffset, resultViewSize);
    }

    /**
     * Returns the list of present items.
     *
     * @return the actual view.
     */
    @Override
    public List<I> getView() {
        return this.resultList.subList(resultViewOffset, resultViewOffset + resultViewSize);
    }

    /**
     * Return the number of element of the complete list, not the view result.
     * <p>
     * The complete list can be uncountable in that case the result will be -1.
     * this can be useful for infinite list handling.
     * </p>
     *
     * @return the size list or -1 if infinite.
     */
    public long getResultSize() {
        return resultSize;
    }
}
