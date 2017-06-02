/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.builder.BaseOptionPicker;
import org.seedstack.business.specification.builder.SpecificationPropertyPicker;

class BaseOptionPickerImpl<T> implements BaseOptionPicker<T> {
    private final SpecificationBuilderContext<T> context;

    BaseOptionPickerImpl(SpecificationBuilderContext<T> context) {
        this.context = context;
    }

    @Override
    public SpecificationPropertyPicker<T> and() {
        context.setMode(SpecificationBuilderContext.Mode.CONJUNCTION);
        return new SpecificationPropertyPickerImpl<>(context);
    }

    @Override
    public SpecificationPropertyPicker<T> or() {
        context.setMode(SpecificationBuilderContext.Mode.DISJUNCTION);
        return new SpecificationPropertyPickerImpl<>(context);
    }

    @Override
    public SpecificationPropertyPicker<T> orNot() {
        context.setMode(SpecificationBuilderContext.Mode.NEGATIVE_DISJUNCTION);
        return new SpecificationPropertyPickerImpl<>(context);
    }

    @Override
    public Specification<T> build() {
        return context.build();
    }
}
