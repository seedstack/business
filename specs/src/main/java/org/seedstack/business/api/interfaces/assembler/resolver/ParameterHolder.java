/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.resolver;

/**
 * This class holds the parameter of a method. It is used to collect parameters and then match them to a method.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public interface ParameterHolder {

    /**
     * Adds a parameter value at its position in the expected method.
     *
     * @param source the source used to get the value. Used for debugging information.
     * @param index the position in the method
     * @param value the parameter value
     */
    void put(String source, int index, Object value);

    /**
     * Adds a parameter value in a tuple at its position in the expected method.
     *
     * @param source the source used to get the value. Used for debugging information.
     * @param index the position in the method
     * @param tupleIndex the position in the tuple
     * @param value the parameter value
     */
    void put(String source, int index, int tupleIndex, Object value);

    /**
     * Gives the ordered list of parameters.
     *
     * @return array of parameters
     */
    Object[] parameters();

    /**
     * Gives the ordered list of parameters.
     *
     * @return array of parameters
     */
    Object first();

    /**
     * Indicates whether the holder contains parameters or not.
     *
     * @return true if the holder doesn't contain parameters, false otherwise
     */
    boolean isEmpty();
}
