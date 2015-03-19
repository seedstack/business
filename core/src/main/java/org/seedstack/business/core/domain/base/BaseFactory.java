/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.domain.base;

import org.seedstack.business.api.Producible;
import org.seedstack.business.api.domain.DomainObject;
import org.seedstack.business.api.domain.GenericFactory;

import java.lang.reflect.ParameterizedType;

/**
 * 
 * This class has to be extended to create a domain factory implementation. See {@link org.seedstack.business.api.domain.GenericFactory}.<br>
 * It offers the plumbing necessary to be fully compliant with concepts like {@link org.seedstack.business.api.interfaces.assembler.Assemblers}.
 * <p>
 * To be a valid Domain Factory implementation, the implementation must respects the followings :
 * <ul>
 *   <li> implements the Domain Factory interface see {@linkplain org.seedstack.business.api.domain.GenericFactory}
 *   <li> extends this class {@link BaseFactory}. 
 * </ul> 
 * <pre>
 * &#9556;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9559; 
 * &#9553;public class ProductFactoryBase extends BaseFactory<Product> implements ProductFactory {&#9553;
 * &#9553;                                                                               &#9553;
 * &#9553;   public Product createProduct(String productId , EAN13 ean13 )               &#9553;
 * &#9553;   {                                                                           &#9553;
 * &#9553;       Product product = new Product()                                         &#9553;
 * &#9553;       product.setEntityId(productId);                                         &#9553;
 * &#9553;       ... 8< ...                                                              &#9553;
 * &#9553;       product.setEAN13 (ean31)                                                &#9553;
 * &#9553;       ... 8< ...                                                              &#9553;
 * &#9553;       return product;                                                         &#9553;
 * &#9553;   }                                                                           &#9553;
 * &#9553;                                                                               &#9553;
 * &#9553;}                                                                              &#9553;
 * &#9562;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9552;&#9565; <br>
 * </pre>
 * And it is sufficient enough for the Domain Factory implementation will be available via its interface, the one that extending  {@link org.seedstack.business.api.domain.GenericFactory}.
 * In the GenericFactory javadoc example, productFactory base will be available like this :
 * 
 *  <pre>
 *  // productFactory will contain an instance of ProductFactoryBase. 
 *  {@literal @}Inject
 *  ProductFactory productFactory;
 *  </pre>
 * 
 * @author epo.jemba@ext.mpsa.com 
 *
 * @param <DO> Domain Object type to be produced.
 */
public abstract class BaseFactory<DO extends DomainObject & Producible> implements GenericFactory<DO> {
	
	private Class<DO> producedClass;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class<DO> getProducedClass() {
		
		if (this.producedClass == null) {
            Class<? extends BaseFactory> class1 = getClass();
			if (class1.getName().contains("EnhancerByGuice") ) {
                class1 = (Class<? extends BaseFactory>) class1.getSuperclass();
			}
			this.producedClass = ((Class<DO>) ((ParameterizedType) class1.getGenericSuperclass()).getActualTypeArguments()[0]);
		}
		
		return producedClass;
	}

}
