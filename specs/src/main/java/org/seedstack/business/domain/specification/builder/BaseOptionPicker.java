/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification.builder;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.Specification;

public interface BaseOptionPicker<A extends AggregateRoot<?>> {
    SpecificationPropertyPicker<A> and();

    SpecificationPropertyPicker<A> or();

    SpecificationPropertyPicker<A> orNot();

    Specification<A> build();
}
