/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver;

/**
 * This class holds the parameter of a method. It is used to collect parameters and then match them to a method.
 *
 * @see DtoInfoResolver
 */
public interface ParameterHolder {

    /**
     * Adds a parameter value at its position in the expected method.
     *
     * @param source the source used to get the value. Used for debugging information.
     * @param index  the position in the method
     * @param value  the parameter value
     */
    void put(String source, int index, Object value);

    /**
     * Adds a parameter value for the specified aggregate root at its position in the expected method.
     *
     * @param sourceMethod   the source method used to get the value. Used for debugging information.
     * @param aggregateIndex the index corresponding to the aggregate root position in the tuple of aggregate roots.
     * @param index          the position in the method
     * @param value          the parameter value
     */
    void put(String sourceMethod, int aggregateIndex, int index, Object value);

    /**
     * Gives the ordered list of parameters.
     *
     * @return array of parameters
     */
    Object[] parameters();

    /**
     * Gives the ordered list of parameters for specified index.
     *
     * @param aggregateIndex the index corresponding to the aggregate root position in the tuple of aggregate roots.
     * @return the array of parameters
     */
    Object[] parametersOfAggregateRoot(int aggregateIndex);

    /**
     * Gives the first parameter of the first aggregate.
     *
     * @return array of parameters
     */
    Object uniqueElement();

    /**
     * Gives the first parameter of the specified aggregate.
     *
     * @param aggregateIndex the index corresponding to the aggregate root position in the tuple of aggregate roots.
     * @return array of parameters
     */
    Object uniqueElementForAggregateRoot(int aggregateIndex);

    /**
     * Indicates whether the holder contains parameters. If there
     * <p>
     * This is equivalent to {@code isEmptyForAggregateRoot(-1) || isEmptyForAggregateRoot(0)}.
     * If there is only one aggregate root, the index is -1, otherwise it starts at 0.
     * </p>
     *
     * @return true if the holder doesn't contain parameters, false otherwise
     */
    boolean isEmpty();

    /**
     * Indicates whether the holder contains parameters for the aggregate root at the
     * aggregateIndex position in the tuple.
     * <p>
     * It starts at the index 0, or -1 if there is only one aggregate defined.
     * </p>
     * <p>
     * It checks if there is at least one aggregate defined and if the first aggregate
     * as at least one parameter.
     * </p>
     *
     * @param aggregateIndex the index corresponding to the aggregate root position in the tuple of aggregate roots.
     * @return true if the holder doesn't contain parameters, false otherwise
     */
    boolean isEmptyForAggregateRoot(int aggregateIndex);
}
