/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * {@link Repository} option for limiting the number of aggregates returned.
 */
public class LimitOption implements Repository.Option {

    private final long limit;

    /**
     * Creates a limit option.
     *
     * @param limit the number of aggregates to limit to.
     */
    public LimitOption(long limit) {
        this.limit = limit;
    }

    /**
     * Returns the limit.
     *
     * @return the number of aggregates to limit to.
     */
    public long getLimit() {
        return limit;
    }
}
