/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.BusinessException;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.domain.IdentityResolver;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.MethodMatcher;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * This class has to be extended to create a domain factory implementation. It offers the plumbing necessary
 * to be fully compliant with {@link FluentAssembler}
 * <p>
 * To be a valid Domain Factory implementation, the implementation must respects the followings:
 * </p>
 * <ul>
 * <li>implements the Domain Factory interface see {@linkplain Factory}</li>
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
 * that extending {@link Factory}.
 * In the GenericFactory javadoc example, productFactory base will be available like this:
 * <pre>
 * // productFactory will contain an instance of ProductFactoryBase.
 * {@literal @}Inject
 * ProductFactory productFactory;
 * </pre>
 *
 * @param <T> Domain Object type to be produced.
 */
public abstract class BaseFactory<T extends Producible> implements Factory<T> {
    private final Class<T> producedClass;
    @Inject
    private IdentityService identityService;

    @SuppressWarnings("unchecked")
    protected BaseFactory() {
        this.producedClass = (Class<T>) BusinessUtils.resolveGenerics(Factory.class, getClass())[0];
    }

    @Override
    public Class<T> getProducedClass() {
        return producedClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T create(Object... args) {
        Class<T> effectivelyProducedClass = getProducedClass();
        Constructor<T> constructor = MethodMatcher.findMatchingConstructor(effectivelyProducedClass, args);
        if (constructor == null) {
            throw BusinessException.createNew(BusinessErrorCode.DOMAIN_OBJECT_CONSTRUCTOR_NOT_FOUND)
                    .put("domainObject", effectivelyProducedClass).put("parameters", Arrays.toString(args));
        }

        T producedInstance;
        try {
            constructor.setAccessible(true);
            producedInstance = constructor.newInstance(args);
        } catch (Exception e) {
            throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_INVOKE_CONSTRUCTOR)
                    .put("constructor", constructor)
                    .put("domainObject", effectivelyProducedClass)
                    .put("parameters", Arrays.toString(args));
        }

        if (producedInstance instanceof Entity) {
            if (IdentityResolver.INSTANCE.test(effectivelyProducedClass)) {
                producedInstance = (T) identityService.identify((Entity<?>) producedInstance);
            }
        }

        return producedInstance;
    }
}
