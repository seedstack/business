/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.specification;

import org.seedstack.business.specification.PropertySpecification;
import org.seedstack.business.spi.specification.SpecificationConverter;
import org.seedstack.business.spi.specification.SpecificationTranslator;

public class PropertyDummyConverter implements SpecificationConverter<PropertySpecification<?, ?>, StringBuilder, String> {
    @Override
    public String convert(PropertySpecification<?, ?> specification, StringBuilder context, SpecificationTranslator<StringBuilder, String> translator) {
        return context.append(specification.getPath()).append(translator.translate(specification.getValueSpecification(), context)).toString();
    }
}
