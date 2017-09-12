/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.OperatorPicker;

class OperatorPickerImpl<T, SELECTOR extends BaseSelector<T, SELECTOR>> extends TerminalOperationImpl<T, SELECTOR> implements OperatorPicker<T, SELECTOR> {
    OperatorPickerImpl(SpecificationBuilderContext<T, SELECTOR> context) {
        super(context);
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
}
