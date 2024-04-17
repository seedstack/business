/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.spi;

import org.seedstack.business.specification.Specification;

/**
 * Interface for classes implementing conversion from a particular {@link Specification} type to a
 * target object representing this specification. Converters are used by {@link
 * SpecificationTranslator}s during the translation process.
 *
 * <p> If the converted specification contains nested specifications (like {@link
 * org.seedstack.business.specification.AndSpecification} or
 * {@link org.seedstack.business.specification.OrSpecification}),
 * the translator must be invoked on those to further compose the target object. </p>
 *
 * @param <S> the converted specification type (must be a concrete type).
 * @param <C> the translation context type.
 * @param <T> the type of the target object resulting from the conversion.
 */
public interface SpecificationConverter<S extends Specification<?>, C, T> {

    /**
     * Invoked by the translator to convert a particular type of specification.
     *
     * @param specification the specification to convert.
     * @param context       the translation context.
     * @param translator    the specification translator to invoke if the specification contains
     *                      nested specifications.
     * @return the target object representing the converted specification.
     */
    T convert(S specification, C context, SpecificationTranslator<C, T> translator);

}
