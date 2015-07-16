/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa;

import org.seedstack.business.api.interfaces.finder.GenericFinder;
import org.seedstack.business.api.interfaces.finder.Range;
import org.seedstack.business.api.interfaces.finder.Result;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * A base finder providing a simple pagination mechanism.
 *
 * @param <Item> the dto to paginate
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class BaseSimpleJpaFinder<Item> implements GenericFinder<Item, Map<String, Object>> {

    @Override
    public Result<Item> find(Range range, Map<String, Object> criteria) {
        // Count
        long resultSize = computeFullRequestSize(criteria);

        // list
        List<Item> list = computeResultList(range, criteria);

        return new Result<Item>(list, range.getOffset(), resultSize);
    }

    /**
     * Returns a sub list of items corresponding to the required range and criteria.
     *
     * @param range the range
     * @param criteria the criteria
     * @return the sub list of item
     */
    protected abstract List<Item> computeResultList(Range range, Map<String, Object> criteria);

    /**
     * Returns the total number of items available.
     *
     * @param criteria the request criteria
     * @return the total number of item
     */
    protected abstract long computeFullRequestSize(Map<String, Object> criteria);

    protected void updateQuery(Query query, Map<String, Object> criteria) {
        for (String key : criteria.keySet()) {
            query.setParameter(key, criteria.get(key));
        }
    }

}
