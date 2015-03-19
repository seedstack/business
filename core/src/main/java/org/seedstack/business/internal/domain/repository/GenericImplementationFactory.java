/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.repository;


/**
 * This interface is used as a generic Guice assisted factory for create generic type.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 30/09/2014
 * @param <T> the produced type
 */
public interface GenericImplementationFactory<T> {

    /**
     * Create an instance of T, with its resolved type variables.
     *
     * @param typeVariableClasses the resolved type variables
     * @return the produced class
     */
    T createResolvedInstance(Class<?>[] typeVariableClasses);
}
