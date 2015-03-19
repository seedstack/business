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
import org.seedstack.business.api.domain.annotations.DomainFactory;


/**
 * This interface has to be extended in order to create a Domain Factory interface. 
 * <p/>
 * To be a valid factory interface, Type must respect the followings:
 * <ul>
 *   <li> be an interface 
 *   <li> extends {@link GenericFactory}
 *   <li> have at least one method that return an Aggregate Root type or another Domain Concept.
 * </ul> 
 * The following is a valid Domain factory interface.
 * <pre>
 *  public interface ProductFactory extends GenericFactory{@literal <Product>} {
 *      Product createProduct(String productId, EAN13 ean13);
 *  }
 * </pre>
 * Then this interface has to be implemented by the actual factory implementation .
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 * @param <DO> Domain Object type to be produced.
 */
@DomainFactory
public interface GenericFactory<DO extends DomainObject & Producible> extends DomainObject {

    /**
     * @return the produced class
     */
	Class<DO> getProducedClass();
	
}

