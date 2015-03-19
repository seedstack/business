/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.helpers;

import com.google.common.collect.Lists;
import org.fest.reflect.core.Reflection;
import org.javatuples.*;
import org.seedstack.seed.core.utils.SeedCheckUtils;

import java.util.Collection;
import java.util.List;

/**
 * Static utility methods to easily create tuples.
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
public final class Tuples {

    private Tuples() {
    }

    /**
     * Transforms an array of object into a tuple. Does not work array of more than ten element.
     *
     * @param objects the array of object
     * @param <TUPLE> the tuple type
     * @return a tuple
     * @throws org.seedstack.seed.core.api.SeedException if the array length is greater than 10
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <TUPLE extends Tuple> TUPLE  createTupleFromList( Object[] objects) {
		return (TUPLE) createTupleFromList((List) Lists.newArrayList(objects));
	}

    /**
     * Transforms a list of object into a tuple. Does not work array of more than ten element.
     *
     * @param objects the list of object
     * @param <TUPLE> the tuple type
     * @return a tuple
     * @throws org.seedstack.seed.core.api.SeedException if the array length is greater than 10
     */
	@SuppressWarnings("unchecked")
    public static <TUPLE extends Tuple> TUPLE  createTupleFromList( List<Object> objects) {
        SeedCheckUtils.checkIf(objects.size() <= 10, "Can't create a Tuple of more than ten element.");
		TUPLE tuple;
		Class<? extends Tuple> tupleClass = null;

		int tupleSize = objects.size();

		switch (tupleSize) {
		case 1:
			tupleClass = Unit.class;
			break;
		case 2:
			tupleClass = Pair.class;
			break;
		case 3:
			tupleClass = Triplet.class;
			break;
		case 4:
			tupleClass = Quartet.class;
			break;
		case 5:
			tupleClass = Quintet.class;
			break;
		case 6:
			tupleClass = Sextet.class;
			break;
		case 7:
			tupleClass = Septet.class;
			break;
		case 8:
			tupleClass = Octet.class;
			break;
		case 9:
			tupleClass = Ennead.class;
			break;
		case 10:
			tupleClass = Decade.class;
			break;
		default:
			break;
		}

		tuple = (TUPLE) Reflection
				.staticMethod("fromCollection")
				.withReturnType(tupleClass)
				.withParameterTypes(Collection.class)
				.in(tupleClass).invoke(objects);

		return tuple;
	}
}