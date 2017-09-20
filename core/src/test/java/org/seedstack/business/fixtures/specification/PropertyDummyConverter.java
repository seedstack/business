/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.specification;

import org.seedstack.business.specification.AttributeSpecification;
import org.seedstack.business.spi.SpecificationConverter;
import org.seedstack.business.spi.SpecificationTranslator;

public class PropertyDummyConverter implements SpecificationConverter<AttributeSpecification<?, ?>, StringBuilder, String> {
    @Override
    public String convert(AttributeSpecification<?, ?> specification, StringBuilder context, SpecificationTranslator<StringBuilder, String> translator) {
        return context.append(specification.getPath()).append(translator.translate(specification.getValueSpecification(), context)).toString();
    }
}
