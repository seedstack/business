/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.IdentitySpecification;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.IdentityPicker;
import org.seedstack.business.specification.dsl.OperatorPicker;

class IdentityPickerImpl<A extends AggregateRoot<I>, I, S extends AggregateSelector<A, I, S>> implements
        IdentityPicker<A, I, S> {

    private final SpecificationBuilderContext<A, S> context;

    IdentityPickerImpl(SpecificationBuilderContext<A, S> context) {
        this.context = context;
    }

    @Override
    public OperatorPicker<A, S> is(I id) {
        context.addSpecification(new IdentitySpecification<>(id));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public OperatorPicker<A, S> isNot(I id) {
        context.addSpecification(new IdentitySpecification<A, I>(id).negate());
        return new OperatorPickerImpl<>(context);
    }
}
