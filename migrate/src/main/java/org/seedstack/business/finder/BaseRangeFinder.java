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
 * A base finder providing a simple pagination mechanism.
 *
 * @param <T> the type of the items to return
 * @param <C> the type of criteria used to filter
 */
@Deprecated
public abstract class BaseRangeFinder<T, C> implements RangeFinder<T, C> {

    @Override
    public Result<T> find(Range range, C criteria) {
        long resultSize = computeFullRequestSize(criteria);
        List<T> list = computeResultList(range, criteria);
        return new Result<>(list, range.getOffset(), resultSize);
    }

    /**
     * Returns a sub list of items corresponding to the required range and criteria.
     *
     * @param range    the range
     * @param criteria the criteria
     * @return the sub list of item
     */
    protected abstract List<T> computeResultList(Range range, C criteria);

    /**
     * Returns the total number of items available.
     *
     * @param criteria the request criteria
     * @return the total number of item
     */
    protected abstract long computeFullRequestSize(C criteria);

}
