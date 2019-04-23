/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.finder;

/**
 * This class represents a Range: an offset and a size.
 */
@Deprecated
public final class Range {

    private long offset;
    private long size;

    /**
     * Constructor.
     *
     * @param offset the range offset
     * @param size   the range size
     */
    public Range(long offset, long size) {
        this.offset = offset;
        this.size = size;
    }

    /**
     * The range from the chunk info.
     *
     * @param chunkOffset the chunk offset
     * @param chunkSize   the chunk size
     * @return the range
     */
    public static Range rangeFromChunkInfo(long chunkOffset, long chunkSize) {
        return new Range(chunkOffset, chunkSize);
    }

    /**
     * The range from the page info.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the range
     */
    public static Range rangeFromPageInfo(long pageIndex, long pageSize) {
        return new Range(pageIndex * pageSize, pageSize);
    }

    /**
     * Returns the offset.
     *
     * @return the range offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * Returns the size.
     *
     * @return the range size
     */
    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("Range [offset=%s, size=%s]", offset, size);
    }

}
