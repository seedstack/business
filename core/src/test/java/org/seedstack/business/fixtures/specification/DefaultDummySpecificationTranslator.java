/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.specification;

import org.seedstack.business.specification.Specification;
import org.seedstack.business.spi.specification.BaseSpecificationTranslator;

public class DefaultDummySpecificationTranslator extends BaseSpecificationTranslator<StringBuilder, String> {
    @Override
    public <S extends Specification<?>> String translate(S specification, StringBuilder context) {
        return convert(specification, new StringBuilder());
    }
}
