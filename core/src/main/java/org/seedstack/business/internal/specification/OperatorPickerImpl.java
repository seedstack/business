/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.OperatorPicker;

class OperatorPickerImpl<T, S extends BaseSelector<T, S>> extends TerminalOperationImpl<T, S> implements
        OperatorPicker<T, S> {

    OperatorPickerImpl(SpecificationBuilderContext<T, S> context) {
        super(context);
    }

    @Override
    public S and() {
        context.setMode(SpecificationBuilderContext.Mode.CONJUNCTION);
        return context.getSelector();
    }

    @Override
    public S or() {
        context.setMode(SpecificationBuilderContext.Mode.DISJUNCTION);
        return context.getSelector();
    }
}
