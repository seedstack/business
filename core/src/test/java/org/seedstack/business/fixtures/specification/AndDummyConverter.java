/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.specification;

import org.seedstack.business.specification.AndSpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.spi.specification.SpecificationConverter;
import org.seedstack.business.spi.specification.SpecificationTranslator;

public class AndDummyConverter implements SpecificationConverter<AndSpecification<?>, StringBuilder, String> {
    @Override
    public String convert(AndSpecification<?> specification, StringBuilder context, SpecificationTranslator<StringBuilder, String> translator) {
        Specification<?>[] specifications = specification.getSpecifications();
        for (int i = 0; i < specifications.length; i++) {
            context.append(translator.translate(specifications[i], context));
            if (i < specifications.length - 1) {
                context.append(" && ");
            }
        }
        return context.toString();
    }
}
