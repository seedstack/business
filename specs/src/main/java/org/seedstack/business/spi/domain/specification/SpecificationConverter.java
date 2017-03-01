/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.spi.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.Specification;

public interface SpecificationConverter<A extends AggregateRoot<?>, S extends Specification<A>, B, C> {

    C convert(S specification, B builder, SpecificationTranslator<A, B, C> translator);

}
