/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.helper;

import com.google.inject.util.Types;
import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.junit.Test;
import org.seedstack.business.helpers.Tuples;

import java.lang.reflect.Type;

/**
 * Tests the helper class {@link org.seedstack.business.helpers.Tuples}.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class TuplesTest {

    @Test
    public void testCreateTupleFromList() {
        Tuple tuple = Tuples.create(String.class, Long.class);
        Pair<Integer, String> pair = Tuples.create(10, "foo");
        Assertions.assertThat((Iterable<?>) pair).isEqualTo(new Pair<Integer, String>(10, "foo"));
        Assertions.assertThat((Iterable<?>) tuple).isInstanceOf(Pair.class);
        Assertions.assertThat(tuple.containsAll(String.class, Long.class)).isTrue();
        Assertions.assertThat(tuple.getSize()).isEqualTo(2);

        tuple = Tuples.create(String.class, Long.class, Float.class);
        Assertions.assertThat((Iterable<?>) tuple).isInstanceOf(Triplet.class);
    }

    @Test
    public void testClassOfTuple() {
        Class<?> type = Tuples.classOfTuple(String.class, Long.class);
        Assertions.assertThat(type).isEqualTo(Pair.class);
    }

    @Test
    public void testTypeOfTuple() {
        Type type = Tuples.typeOfTuple(String.class, Long.class);
        Assertions.assertThat(type).isEqualTo(Types.newParameterizedType(Pair.class, String.class, Long.class));
    }
}
