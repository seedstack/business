/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.domain.specification.builder.BaseOptionPicker;
import org.seedstack.business.domain.specification.builder.SpecificationPropertyPicker;

class BaseOptionPickerImpl<A extends AggregateRoot<?>> implements BaseOptionPicker<A> {
    private final SpecificationBuilderContext<A> context;

    BaseOptionPickerImpl(SpecificationBuilderContext<A> context) {
        this.context = context;
    }

    @Override
    public SpecificationPropertyPicker<A> and() {
        context.setMode(SpecificationBuilderContext.Mode.CONJUNCTION);
        return new SpecificationPropertyPickerImpl<>(context);
    }

    @Override
    public SpecificationPropertyPicker<A> or() {
        context.setMode(SpecificationBuilderContext.Mode.DISJUNCTION);
        return new SpecificationPropertyPickerImpl<>(context);
    }

    @Override
    public SpecificationPropertyPicker<A> orNot() {
        context.setMode(SpecificationBuilderContext.Mode.NEGATIVE_DISJUNCTION);
        return new SpecificationPropertyPickerImpl<>(context);
    }

    @Override
    public Specification<A> build() {
        return context.build();
    }
}
