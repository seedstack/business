/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.finder;

/**
 * This interface is a helper for building specialized finders handling a range of data. It can be useful
 * for pagination purposes.
 *
 * @param <I> the representation type
 * @param <C> the criteria
 */
@Finder
@Deprecated
public interface RangeFinder<I, C> {
    /**
     * Find items according a range and a criteria.
     *
     * @param range    the range
     * @param criteria the criteria
     * @return a result object containing the items
     */
    Result<I> find(Range range, C criteria);
}
