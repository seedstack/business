/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.interfaces.query.finder;

import org.seedstack.business.api.interfaces.query.finder.GenericFinder;
import org.seedstack.business.api.interfaces.query.range.Range;
import org.seedstack.business.api.interfaces.query.result.Result;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * A Simple base Finder that handles simple JPA Query.
 * <p>
 * Asks the developers to overrides two methods:
 * <ul>
 * <li>computeFullRequestSize(Map<String, Object> criteria)</li>
 * <li>computeResultList(Range range, Map<String, Object> criteria)</li>
 * </ul>
 * </p>
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

    protected abstract List<Item> computeResultList(Range range, Map<String, Object> criteria);

    protected abstract long computeFullRequestSize(Map<String, Object> criteria);

    protected void updateQuery(Query query, Map<String, Object> criteria) {
        for (String key : criteria.keySet()) {
            query.setParameter(key, criteria.get(key));
        }
    }

}
