/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver.impl;

import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;

import java.util.HashMap;
import java.util.Map;


public class ParameterHolderInternal implements ParameterHolder {
    private final Map<Integer, Map<Integer, Object>> parametersByAggregate = new HashMap<>();

    @Override
    public void put(String source, int index, Object value) {
        put(source, -1, index, value);
    }

    @Override
    public void put(String sourceMethod, int aggregateIndex, int index, Object value) {
        Map<Integer, Object> parameters = parametersByAggregate.get(aggregateIndex);

        if (parameters == null) {
            parameters = new HashMap<>();
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
        Map<Integer, Object> map = parametersByAggregate.get(aggregateIndex);
        if (map != null) {
            return map.values().toArray();
        } else {
            return new Object[0];
        }
    }

    @Override
    public Object uniqueElement() {
        Map<Integer, Object> map = parametersByAggregate.get(-1);
        if (map != null) {
            return map.get(-1);
        }
        return null;
    }

    @Override
    public Object uniqueElementForAggregateRoot(int aggregateIndex) {
        Map<Integer, Object> map = parametersByAggregate.get(aggregateIndex);
        if (map != null) {
            return map.get(-1);
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
