/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.Producible;

import java.lang.reflect.ParameterizedType;

/**
 * This class has to be extended to create a domain factory implementation. It offers the plumbing necessary
 * to be fully compliant with {@link org.seedstack.business.assembler.FluentAssembler}
 * <p>
 * To be a valid Domain Factory implementation, the implementation must respects the followings:
 * </p>
 * <ul>
 *   <li>implements the Domain Factory interface see {@linkplain org.seedstack.business.domain.GenericFactory}</li>
 *   <li>extends this class {@link BaseFactory}.</li>
 * </ul>
 * <pre>
 * public class ProductFactoryBase extends BaseFactory&lt;Product&gt; implements ProductFactory {
 *
 *    public Product createProduct(String productId, EAN13 ean13) {
 *        Product product = new Product()
 *        product.setEntityId(productId);
 *        ...
 *        product.setEAN13 (ean31)
 *        ...
 *        return product;
 *    }
 *
 * }
 * </pre>
 * And it is sufficient enough for the Domain Factory implementation will be available via its interface, the one
 * that extending {@link org.seedstack.business.domain.GenericFactory}.
 * In the GenericFactory javadoc example, productFactory base will be available like this:
 * <pre>
 * // productFactory will contain an instance of ProductFactoryBase.
 * {@literal @}Inject
 * ProductFactory productFactory;
 * </pre>
 *
 * @param <DO> Domain Object type to be produced.
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class BaseFactory<DO extends DomainObject & Producible> implements GenericFactory<DO> {

    private Class<DO> producedClass;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Class<DO> getProducedClass() {
        // TODO <pith> improve the way we get the parametrize type
        if (this.producedClass == null) {
            Class<? extends BaseFactory> class1 = getClass();
            if (class1.getName().contains("EnhancerByGuice")) {
                class1 = (Class<? extends BaseFactory>) class1.getSuperclass();
            }
            this.producedClass = ((Class<DO>) ((ParameterizedType) class1.getGenericSuperclass()).getActualTypeArguments()[0]);
        }

        return producedClass;
    }

}
