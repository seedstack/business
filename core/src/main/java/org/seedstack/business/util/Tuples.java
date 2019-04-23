/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.util;

import com.google.inject.util.Types;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.javatuples.Unit;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;

/**
 * Static utility methods to manage tuples.
 */
public final class Tuples {

    private Tuples() {
    }

    /**
     * Builds a tuple from a collection of objects.
     *
     * @param objects the collection of objects (size must be less or equal than 10).
     * @param <T>     the tuple type.
     * @return the constructed tuple.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Tuple> T create(Collection<?> objects) {
        Class<? extends Tuple> tupleClass = classOfTuple(objects.size());
        try {
            return (T) tupleClass.getMethod("fromCollection", Collection.class)
                    .invoke(null, objects);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_CREATE_TUPLE);
        }
    }

    /**
     * Builds a tuple from an array of objects.
     *
     * @param objects the collection of objects (size must be less or equal than 10).
     * @param <T>     the tuple type.
     * @return the constructed tuple.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Tuple> T create(Object... objects) {
        Class<? extends Tuple> tupleClass = classOfTuple(objects.length);
        try {
            return (T) tupleClass.getMethod("fromArray", Object[].class)
                    .invoke(null, new Object[]{objects});
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_CREATE_TUPLE);
        }
    }

    /**
     * Builds a tuple from an {@link Iterable}, optionally limiting the number of items.
     *
     * @param objects the iterable of objects (size must be less or equal than 10).
     * @param limit   the item number limit (-1 means no limit).
     * @param <T>     the tuple type.
     * @return the constructed tuple.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Tuple> T create(Iterable<?> objects, int limit) {
        List<Object> list = new ArrayList<>();
        int index = 0;
        for (Object object : objects) {
            if (limit != -1 && ++index > limit) {
                break;
            }
            list.add(object);
        }
        return create(list);
    }

    /**
     * Builds a tuple from an {@link Iterable}.
     *
     * @param objects the iterable of objects (size must be less or equal than 10).
     * @param <T>     the tuple type.
     * @return the constructed tuple.
     */
    public static <T extends Tuple> T create(Iterable<?> objects) {
        return create(objects, -1);
    }

    /**
     * Returns the tuple class corresponding to the collection size.
     *
     * @param objects the collection of objects (size must be less or equal than 10).
     * @return the corresponding tuple class.
     */
    public static Class<? extends Tuple> classOfTuple(Collection<?> objects) {
        return classOfTuple(objects.size());
    }

    /**
     * Returns the tuple class corresponding to the array size.
     *
     * @param objects the array of objects (size must be less or equal than 10).
     * @return the corresponding tuple class.
     */
    public static Class<? extends Tuple> classOfTuple(Object... objects) {
        return classOfTuple(objects.length);
    }

    /**
     * Returns the tuple class corresponding to the specified cardinality.
     *
     * @param cardinality the cardinality (must be less or equal than 10).
     * @return the corresponding tuple class.
     */
    public static Class<? extends Tuple> classOfTuple(int cardinality) {
        switch (cardinality) {
            case 1:
                return Unit.class;
            case 2:
                return Pair.class;
            case 3:
                return Triplet.class;
            case 4:
                return Quartet.class;
            case 5:
                return Quintet.class;
            case 6:
                return Sextet.class;
            case 7:
                return Septet.class;
            case 8:
                return Octet.class;
            case 9:
                return Ennead.class;
            case 10:
                return Decade.class;
            default:
                throw new IllegalArgumentException("Cannot create a tuple with " + cardinality + " element(s)");
        }
    }

    /**
     * Returns the {@link ParameterizedType} of the Tuple class corresponding to the specified
     * classes. <p> For instance, for a list with Customer.class and Order.class the method will
     * return Pair&lt;Customer, Order&gt;. </p>
     *
     * @param classes the tuple classes.
     * @return the tuple type.
     */
    public static ParameterizedType typeOfTuple(final Class<?>... classes) {
        return Types.newParameterizedType(classOfTuple(classes.length), (Type[]) classes);
    }

    /**
     * Returns a list containing the classes of the elements of the tuple.
     *
     * @param <T>   the common of the classes contained by the tuple.
     * @param tuple the tuple to convert.
     * @return the list of classes.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T>[] itemClasses(Tuple tuple) {
        Class<?>[] itemClasses = new Class[tuple.getSize()];
        int index = 0;
        for (Object o : tuple) {
            itemClasses[index++] = o.getClass();
        }
        return (Class<T>[]) itemClasses;
    }
}