/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import static org.seedstack.shed.reflect.ReflectUtils.makeAccessible;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.MethodMatcher;

/**
 * An helper base class that can be extended to create an <strong>implementation</strong> of a
 * factory interface which, in turn, must extend {@link Factory}.
 *
 * <p> This base implementation provides generic object creation through the {@link
 * #create(Object...)} method that will resolve a constructor with the same argument types as its
 * own and invoke it. </p>
 *
 * @param <P> Type of the produced object.
 * @see Factory
 */
public abstract class BaseFactory<P extends Producible> implements Factory<P> {
    private final Class<P> producedClass;

    /**
     * Creates a base domain factory. Actual class produced by the factory is determined by
     * reflection.
     */
    @SuppressWarnings("unchecked")
    protected BaseFactory() {
        this.producedClass = (Class<P>) BusinessUtils.resolveGenerics(Factory.class, getClass())[0];
    }

    /**
     * Creates a base domain factory. Actual class produced by the factory is specified explicitly.
     * This can be used to create a dynamic implementation of a factory.
     *
     * @param producedClass the produced class.
     */
    protected BaseFactory(Class<P> producedClass) {
        this.producedClass = producedClass;
    }

    @Override
    public Class<P> getProducedClass() {
        return producedClass;
    }

    @Override
    public P create(Object... args) {
        Class<P> effectivelyProducedClass = getProducedClass();
        Constructor<P> constructor = MethodMatcher.findMatchingConstructor(effectivelyProducedClass, args);
        if (constructor == null) {
            throw BusinessException.createNew(BusinessErrorCode.DOMAIN_OBJECT_CONSTRUCTOR_NOT_FOUND)
                    .put("domainObject", effectivelyProducedClass)
                    .put("parameters", Arrays.toString(args));
        }

        try {
            return makeAccessible(constructor).newInstance(args);
        } catch (Exception e) {
            throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_INVOKE_CONSTRUCTOR)
                    .put("constructor", constructor)
                    .put("domainObject", effectivelyProducedClass)
                    .put("parameters", Arrays.toString(args));
        }
    }
}
