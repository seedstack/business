/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.IdentitySpecification;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.OperatorPicker;
import org.seedstack.business.specification.dsl.IdentityPicker;

class IdentityPickerImpl<A extends AggregateRoot<ID>, ID, SELECTOR extends AggregateSelector<A, ID, SELECTOR>> implements IdentityPicker<A, ID, SELECTOR> {
    private final SpecificationBuilderContext<A, SELECTOR> context;

    IdentityPickerImpl(SpecificationBuilderContext<A, SELECTOR> context) {
        this.context = context;
    }

    @Override
    public OperatorPicker<A, SELECTOR> is(ID id) {
        context.addSpecification(new IdentitySpecification<>(id));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public OperatorPicker<A, SELECTOR> isNot(ID id) {
        context.addSpecification(new IdentitySpecification<A, ID>(id).negate());
        return new OperatorPickerImpl<>(context);
    }
}
