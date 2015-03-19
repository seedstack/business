/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;


import org.seedstack.business.api.domain.AggregateRoot;
import org.javatuples.Tuple;

import java.util.Arrays;

/**
 * This class is a Java type for Tuple.
 *
 * @author epo.jemba@ext.mpsa.com
 * @see java.lang.reflect.Type
 * @see org.javatuples.Tuple
 * @see BaseTupleAssembler
 */
public class TupleType implements java.lang.reflect.Type {

    /**
     * Raw type.
     */
	public final Class<? extends Tuple> rawType;

    /**
     * Aggregate root type.
     */
    public final Class<? extends AggregateRoot<?>>[] aggregateRootTypes;

    /**
     * Constructor.
     *
     * @param rawType the tuple type (eg. Pair, Triplet, etc.)
     * @param aggregateRootTypes the array of aggregate class (order matters)
     */
    public TupleType(Class<? extends Tuple> rawType, Class<? extends AggregateRoot<?>>[] aggregateRootTypes) {
        this.rawType = rawType;
        this.aggregateRootTypes = aggregateRootTypes.clone();
    }

    @Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (o == null) {
            return false;
        }
		if (o.getClass() != getClass()) {
            return false;
        }
		TupleType other = (TupleType) o;
        return (rawType == other.rawType) && equalsTypes(aggregateRootTypes, other.aggregateRootTypes);
    }

    @Override
    public int hashCode() {
        int result = rawType.hashCode();
        result = 31 * result + Arrays.hashCode(aggregateRootTypes);
        return result;
    }

    private boolean equalsTypes(Class<? extends AggregateRoot<?>>[] aggregateRootTypes1,
			Class<? extends AggregateRoot<?>>[] aggregateRootTypes2) {
		
		if (aggregateRootTypes1.length != aggregateRootTypes2.length) {
			return false;
		}
		
		for (int i = 0; i < aggregateRootTypes2.length; i++) {
			Class<? extends AggregateRoot<?>> class1 = aggregateRootTypes1[i];
			Class<? extends AggregateRoot<?>> class2 = aggregateRootTypes2[i];
            if (!class1.equals(class2)) {
                return false;
            }
        }
		return true;
	}

}
