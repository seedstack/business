/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination;

import java.util.List;

public class Chunk<Item> extends AbstractPage<Item>{

    /**
     * Constructor.
     *
     * @param items      the list of item
     * @param chunkStart the chunk start
     */
    public Chunk(List<Item> items, long chunkStart, long resultViewSize) {
        super(items, chunkStart, resultViewSize);
    }

    /**
     * Return the position of the first element in the complete list.
     *
     * @return the offset inside the complete list
     */
    public long getChunkOffset() {
        return resultViewOffset;
    }

    /**
     * Return the number of element in the complete list.
     *
     * @return
     */
    public long getResultViewSize() {
        return resultViewSize;
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "resultList=" + resultList +
                ", resultSize=" + resultSize +
                ", resultOffset=" + resultViewOffset +
                '}';
    }
}
