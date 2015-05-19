/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.resolver;

import org.seedstack.business.api.interfaces.assembler.resolver.ParameterHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class ParameterHolderInternal implements ParameterHolder {

    private final Map<Integer, Map<Integer, Object>> parametersByAggregate = new HashMap<Integer, Map<Integer, Object>>();

    @Override
    public void put(String source, int index, Object value) {
        put(source, -1, index, value);
    }

    @Override
    public void put(String sourceMethod, int aggregateIndex, int index, Object value) {
        Map<Integer, Object> parameters = parametersByAggregate.get(aggregateIndex);

        if (parameters == null) {
            parameters = new HashMap<Integer, Object>();
        }

        if (parameters.get(index) != null) {
            String message = String.format("%s - the parameter at the index %d is already specified", sourceMethod, index);
            if (aggregateIndex > -1) {
                message += " for the typeIndex=" + aggregateIndex;
            }
            throw new IllegalArgumentException(message);
        }
        parameters.put(index, value);
        parametersByAggregate.put(aggregateIndex, parameters);
    }

    @Override
    public Object[] parameters() {
        return parametersOfAggregateRoot(-1);
    }

    @Override
    public Object[] parametersOfAggregateRoot(int aggregateIndex) {
        if (parametersByAggregate.get(aggregateIndex) != null) {
            return parametersByAggregate.get(aggregateIndex).values().toArray();
        } else {
            return new Object[0];
        }
    }

    @Override
    public Object uniqueElement() {
        if (parametersByAggregate.get(-1) != null) {
            return parametersByAggregate.get(-1).get(-1);
        }
        return null;
    }

    @Override
    public Object uniqueElementForAggregateRoot(int aggregateIndex) {
        if (parametersByAggregate.get(aggregateIndex) != null) {
            return parametersByAggregate.get(aggregateIndex).get(-1);
        }
        return null;
    }

    @Override
    public boolean isEmptyForAggregateRoot(int aggregateIndex) {
        return parametersByAggregate.isEmpty() || parametersByAggregate.get(aggregateIndex) == null || parametersByAggregate.get(aggregateIndex).isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return isEmptyForAggregateRoot(-1) && isEmptyForAggregateRoot(0);
    }
}
