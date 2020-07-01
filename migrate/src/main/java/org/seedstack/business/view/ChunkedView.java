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
 * A chunked view is an arbitrary portion of result organized in
 * arbitrary chunk.
 *
 * @param <T> the item type
 */
@Deprecated
public class ChunkedView<T> extends AbstractView<T> {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param items       the list of item
     * @param chunkOffset the chunk offset
     * @param chunkSize   the chunk size
     */
    public ChunkedView(Result<T> items, long chunkOffset, long chunkSize) {
        super(items, chunkOffset, chunkSize);
    }

    /**
     * Constructor.
     *
     * @param items      the list of item
     * @param chunkStart the chunk start
     * @param chunkSize  the chunk size
     */
    public ChunkedView(List<T> items, long chunkStart, long chunkSize) {
        super(items, chunkStart, chunkSize);
    }

    /**
     * Returns the offset.
     *
     * @return the chunk offset
     */
    public long getChunkOffset() {
        return resultViewOffset;
    }

    /**
     * Returns the size.
     *
     * @return the chunk size
     */
    public long getChunkSize() {
        return resultViewSize;
    }

}
