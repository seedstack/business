/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.finder;

import java.util.List;

/**
 * Symbolises a ranged result of representation retrieved from the persistence. It exposes:
 * <ul>
 * <li>The list: {@code getResult()},</li>
 * <li>The list size: {@code getResultSize()},</li>
 * <li>The full size of the whole request: {@code getFullSizeRequest()}.</li>
 * </ul>
 * @param <Item> the representation type
 * @author epo.jemba@ext.mpsa.com
 */
public final class Result<Item> {
    protected final List<Item> list;
    private final long fullSize;
    private final long offset;

    /**
     * Constructor.
     *
     * @param list     the list of item
     * @param offset   the offset
     * @param fullSize the total number of item available
     */
    public Result(List<Item> list, long offset, long fullSize) {
        this.list = list;
        this.offset = offset;
        this.fullSize = fullSize;
    }

    /**
     * @return the list of items
     */
    public List<Item> getResult() {
        return this.list;
    }

    /**
     * @return the number of item returned
     */
    public int getSize() {
        return this.list.size();
    }

    /**
     * @return the total number of item available
     */
    public long getFullSize() {
        return this.fullSize;
    }

    /**
     * @return the offset size
     */
    public long getOffset() {
        return this.offset;
    }

    /**
     * Creates a new Result.
     *
     * @param result          the list of item
     * @param offset          the offset
     * @param fullRequestSize the number of item available
     * @param <Item>          the item type
     * @return the result range
     */
    public static <Item> Result<Item> rangeResult(List<Item> result, long offset, long fullRequestSize) {
        return new Result<Item>(result, offset, fullRequestSize);
    }
}
