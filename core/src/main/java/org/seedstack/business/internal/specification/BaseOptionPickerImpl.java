/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.BaseOptionPicker;
import org.seedstack.business.specification.dsl.BaseSelector;

class BaseOptionPickerImpl<T, SELECTOR extends BaseSelector<T, SELECTOR>> implements BaseOptionPicker<T, SELECTOR> {
    private final SpecificationBuilderContext<T, SELECTOR> context;

    BaseOptionPickerImpl(SpecificationBuilderContext<T, SELECTOR> context) {
        this.context = context;
    }

    @Override
    public SELECTOR and() {
        context.setMode(SpecificationBuilderContext.Mode.CONJUNCTION);
        return context.getSelector();
    }

    @Override
    public SELECTOR or() {
        context.setMode(SpecificationBuilderContext.Mode.DISJUNCTION);
        return context.getSelector();
    }

    @Override
    public SELECTOR orNot() {
        context.setMode(SpecificationBuilderContext.Mode.NEGATIVE_DISJUNCTION);
        return context.getSelector();
    }

    @Override
    public Specification<T> build() {
        return context.build();
    }
}
