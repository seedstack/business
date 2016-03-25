/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import net.jodah.typetools.TypeResolver;
import org.seedstack.business.Producible;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

/**
 * This class has to be extended to create a domain factory implementation. It offers the plumbing necessary
 * to be fully compliant with {@link org.seedstack.business.assembler.FluentAssembler}
 * <p>
 * To be a valid Domain Factory implementation, the implementation must respects the followings:
 * </p>
 * <ul>
 * <li>implements the Domain Factory interface see {@linkplain org.seedstack.business.domain.GenericFactory}</li>
 * <li>extends this class {@link BaseFactory}.</li>
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
    private final Class<DO> producedClass;

    @SuppressWarnings("unchecked")
    protected BaseFactory() {
        Class<?> subType = SeedReflectionUtils.cleanProxy(getClass());
        producedClass = (Class<DO>) TypeResolver.resolveRawArguments(TypeResolver.resolveGenericType(BaseFactory.class, subType), subType)[0];
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Class<DO> getProducedClass() {
        return producedClass;
    }

}
