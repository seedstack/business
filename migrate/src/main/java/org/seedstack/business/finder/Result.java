/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.finder;

import java.util.List;

/**
 * Symbolises a ranged result of representation retrieved from the persistence. It exposes:
 * <ul>
 * <li>The list: {@code getResult()},</li>
 * <li>The list size: {@code getResultSize()},</li>
 * <li>The full size of the whole request: {@code getFullSizeRequest()}.</li>
 * </ul>
 *
 * @param <I> the representation type
 */
@Deprecated
public final class Result<I> {
    private final List<I> list;
    private final long fullSize;
    private final long offset;

    /**
     * Constructor.
     *
     * @param list     the list of item
     * @param offset   the offset
     * @param fullSize the total number of item available
     */
    public Result(List<I> list, long offset, long fullSize) {
        this.list = list;
        this.offset = offset;
        this.fullSize = fullSize;
    }

    /**
     * Creates a new Result.
     *
     * @param result          the list of item
     * @param offset          the offset
     * @param fullRequestSize the number of item available
     * @param <I>             the item type
     * @return the result range
     */
    public static <I> Result<I> rangeResult(List<I> result, long offset, long fullRequestSize) {
        return new Result<>(result, offset, fullRequestSize);
    }

    /**
     * Returns the items.
     *
     * @return the list of items
     */
    public List<I> getResult() {
        return this.list;
    }

    /**
     * Returns the size.
     *
     * @return the number of item returned
     */
    public int getSize() {
        return this.list.size();
    }

    /**
     * Returns the full size.
     *
     * @return the total number of item available
     */
    public long getFullSize() {
        return this.fullSize;
    }

    /**
     * Returns the offset.
     *
     * @return the offset
     */
    public long getOffset() {
        return this.offset;
    }
}
