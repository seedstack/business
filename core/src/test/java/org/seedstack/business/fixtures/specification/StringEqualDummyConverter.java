/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.specification;

import org.seedstack.business.specification.StringEqualSpecification;
import org.seedstack.business.spi.SpecificationConverter;
import org.seedstack.business.spi.SpecificationTranslator;

public class StringEqualDummyConverter implements
  SpecificationConverter<StringEqualSpecification, StringBuilder, String> {

  @Override
  public String convert(StringEqualSpecification specification, StringBuilder context,
    SpecificationTranslator<StringBuilder, String> translator) {
    return context.append(" == ").append(specification.getExpectedString()).toString();
  }
}
