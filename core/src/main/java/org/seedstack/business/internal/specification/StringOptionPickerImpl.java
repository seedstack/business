/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.StringOptionPicker;

class StringOptionPickerImpl<T, SELECTOR extends BaseSelector<T, SELECTOR>> extends OperatorPickerImpl<T, SELECTOR> implements StringOptionPicker<T, SELECTOR> {
    private final StringValueOptionsImpl stringValueOptions;

    StringOptionPickerImpl(SpecificationBuilderContext<T, SELECTOR> context, StringValueOptionsImpl stringValueOptions) {
        super(context);
        this.stringValueOptions = stringValueOptions;
    }

    @Override
    public StringOptionPicker<T, SELECTOR> trimming() {
        stringValueOptions.setTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T, SELECTOR> trimmingLead() {
        stringValueOptions.setLeftTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T, SELECTOR> trimmingTail() {
        stringValueOptions.setRightTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T, SELECTOR> ignoringCase() {
        stringValueOptions.setIgnoringCase(true);
        return this;
    }
}
