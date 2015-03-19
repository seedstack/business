/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.seedstack.business.api.Producible;

/**
 * This class is the main containers of all {@linkplain GenericFactory}s.
 * <p>
 * it can be injected via
 * <pre>
 * {@literal @}Inject
 * Factories factories;
 * </pre>
 * and uses like the following.
 * <pre>
 * ...
 * ProductFactory productFactory = factories.get(ProductFactory.class);
 * ...
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 */
public interface Factories {

    /**
     * Return the right factory instance in function of the class given as argument.
     * 
     * @param <DO> The produced class. 
     *
     * @param producedClass the produced class
     * @return the factory
     */
    <DO extends DomainObject & Producible> GenericFactory<DO> get(Class<DO> producedClass);
}
