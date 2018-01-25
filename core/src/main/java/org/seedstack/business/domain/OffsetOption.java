/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * {@link Repository} option for skipping a specified amount of aggregates.
 */
public class OffsetOption implements Repository.Option {

    private final long offset;

    /**
     * Creates an offset option.
     *
     * @param offset the number of aggregates to skip.
     */
    public OffsetOption(long offset) {
        this.offset = offset;
    }

    /**
     * Returns the offset.
     *
     * @return the number of aggregates to skip.
     */
    public long getOffset() {
        return offset;
    }
}
