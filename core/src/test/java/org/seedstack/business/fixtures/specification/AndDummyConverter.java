/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.specification;

import org.seedstack.business.specification.AndSpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.spi.SpecificationConverter;
import org.seedstack.business.spi.SpecificationTranslator;

public class AndDummyConverter implements
    SpecificationConverter<AndSpecification<?>, StringBuilder, String> {

  @Override
  public String convert(AndSpecification<?> specification, StringBuilder context,
      SpecificationTranslator<StringBuilder, String> translator) {
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
