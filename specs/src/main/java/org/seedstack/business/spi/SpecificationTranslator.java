/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.spi;


import org.seedstack.business.specification.Specification;

/**
 * Translates a {@link Specification} into a target object by invoking the relevant {@link SpecificationConverter}s.
 *
 * @param <C> the type of the translation context object.
 * @param <T> the type of the translated target object.
 */
public interface SpecificationTranslator<C, T> {

    /**
     * Translates the specified composite specification into a target object.
     *
     * @param specification the {@link Specification} to translate.
     * @param context       the translation context.
     * @param <S>           the type of the specification to translate.
     * @return the target object representing the fully translated specification.
     */
    <S extends Specification<?>> T translate(S specification, C context);

    /**
     * @return the class of the translation context object.
     */
    Class<C> getContextClass();

    /**
     * @return the class of the target object.
     */
    Class<T> getTargetClass();

}
