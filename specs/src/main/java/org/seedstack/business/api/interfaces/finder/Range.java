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

/**
 * This class represents a Range : an offset + a size.
 *
 * @author epo.jemba@ext.mpsa.com
 */
public final class Range {

    private long offset;
    private int size;

    /**
     * Constructor.
     *
     * @param offset the range offset
     * @param size   the range size
     */
    public Range(long offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    /**
     * @return the range offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * @return the range size
     */
    public int getSize() {
        return size;
    }

    /**
     * The range from the chunk info.
     *
     * @param chunkOffset the chunk offset
     * @param chunkSize   the chunk size
     * @return the range
     */
    public static Range rangeFromChunkInfo(long chunkOffset, int chunkSize) {
        return new Range(chunkOffset, chunkSize);
    }

    /**
     * The range from the page info.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the range
     */
    public static Range rangeFromPageInfo(long pageIndex, int pageSize) {
        return new Range(pageIndex * pageSize, pageSize);
    }

    @Override
    public String toString() {
        return String.format("Range [offset=%s, size=%s]", offset, size);
    }

}
