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
import org.seedstack.business.specification.builder.AggregateSelector;
import org.seedstack.business.specification.builder.BaseOptionPicker;
import org.seedstack.business.specification.builder.IdentityPicker;

class IdentityPickerImpl<A extends AggregateRoot<ID>, ID, SELECTOR extends AggregateSelector<A, ID, SELECTOR>> implements IdentityPicker<A, ID, SELECTOR> {
    private final SpecificationBuilderContext<A, SELECTOR> context;

    IdentityPickerImpl(SpecificationBuilderContext<A, SELECTOR> context) {
        this.context = context;
    }

    @Override
    public BaseOptionPicker<A, SELECTOR> is(ID id) {
        context.addSpecification(new IdentitySpecification<>(id));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public BaseOptionPicker<A, SELECTOR> isNot(ID id) {
        context.addSpecification(new IdentitySpecification<A, ID>(id).not());
        return new BaseOptionPickerImpl<>(context);
    }
}
