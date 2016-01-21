/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.Producible;


/**
 * This interface has to be extended in order to create a Domain Factory interface. 
 * <p>
 * To be a valid factory interface, Type must respect the followings:
 * </p>
 * <ul>
 *   <li>be an interface</li>
 *   <li>extends {@link org.seedstack.business.domain.GenericFactory}</li>
 *   <li>have at least one method that return an Aggregate Root type or another Domain Concept.</li>
 * </ul> 
 * The following is a valid Domain factory interface.
 * <pre>
 *  public interface ProductFactory extends GenericFactory&lt;Product&gt; {
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

