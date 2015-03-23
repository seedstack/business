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
import org.seedstack.business.helpers.Tuples;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class ParameterHolderInternal implements ParameterHolder {

    private Object[] parameters = new Object[2];

    @Override
    public void put(String source, int index, Object value) {
        if (parameters[index] != null) {
            throw new IllegalArgumentException(source + ": the index " + index + " is already used. You might have forgotten to specify the tupleIndex");
        }
        parameters[index] = value;
    }

    @Override
    public void put(String source, int index, int tupleIndex, Object value) {
        TupleHolder tupleHolder;
        Object o = parameters[index];
        if (o == null) {
            tupleHolder = new TupleHolder();
        } else {
            if (o instanceof TupleHolder) {
                tupleHolder = (TupleHolder) o;
            } else {
                throw new IllegalArgumentException(source + ": the  index " + index + " is already used. You might have forgotten to specify the tupleIndex");
            }
        }

        tupleHolder.put(source, tupleIndex, value);
        parameters[index] = tupleHolder;
    }

    class TupleHolder {

        private Object[] attributes = new Object[2];

        public void put(String source, int tupleIndex, Object value) {
            if (attributes[tupleIndex] != null) {
                throw new IllegalArgumentException(source + ": the tuple index " + tupleIndex + " is already used.");
            }
            attributes[tupleIndex] = value;
        }

        public Object[] attributes() {
            return attributes;
        }
    }

    @Override
    public Object[] parameters() {
        return transformTupleHoldersToTuples(parameters);
    }

    @Override
    public Object first() {
        return parameters[0];
    }

    @Override
    public boolean isEmpty() {
        return parameters == null || parameters.length == 0;
    }

    private Object[] transformTupleHoldersToTuples(Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            Object parameter = parameters[i];
            if (parameter instanceof TupleHolder) {
                TupleHolder tupleHolder = (TupleHolder) parameter;
                parameters[i] = Tuples.create(tupleHolder.attributes);
            }
        }
        return parameters;
    }
}
