/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.builder.StringOptionPicker;

class StringOptionPickerImpl<T> extends BaseOptionPickerImpl<T> implements StringOptionPicker<T> {
    private final StringValueOptionsImpl stringValueOptions;

    StringOptionPickerImpl(SpecificationBuilderContext<T> context, StringValueOptionsImpl stringValueOptions) {
        super(context);
        this.stringValueOptions = stringValueOptions;
    }

    @Override
    public StringOptionPicker<T> trimmed() {
        stringValueOptions.setTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T> leftTrimmed() {
        stringValueOptions.setLeftTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T> rightTrimmed() {
        stringValueOptions.setRightTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T> ignoringCase() {
        stringValueOptions.setIgnoringCase(true);
        return this;
    }
}
