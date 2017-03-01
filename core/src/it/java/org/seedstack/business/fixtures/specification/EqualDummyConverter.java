/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.EqualSpecification;
import org.seedstack.business.spi.domain.specification.SpecificationConverter;
import org.seedstack.business.spi.domain.specification.SpecificationTranslator;

import javax.inject.Named;

@Named("dummy")
public class EqualDummyConverter<A extends AggregateRoot<?>> implements SpecificationConverter<A, EqualSpecification<A>, StringBuilder, String> {
    @Override
    public String convert(EqualSpecification<A> specification, StringBuilder builder, SpecificationTranslator<A, StringBuilder, String> translator) {
        return builder.append(specification.getPath()).append(" == ").append(specification.getExpectedValue()).toString();
    }
}
