/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.builder.StringOptionPicker;

class StringOptionPickerImpl<A extends AggregateRoot<?>> extends BaseOptionPickerImpl<A> implements StringOptionPicker<A> {
    private final StringValueOptionsImpl stringValueOptions;

    StringOptionPickerImpl(SpecificationBuilderContext<A> context, StringValueOptionsImpl stringValueOptions) {
        super(context);
        this.stringValueOptions = stringValueOptions;
    }

    @Override
    public StringOptionPicker<A> trimmed() {
        stringValueOptions.setTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<A> leftTrimmed() {
        stringValueOptions.setLeftTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<A> rightTrimmed() {
        stringValueOptions.setRightTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<A> ignoringCase() {
        stringValueOptions.setIgnoringCase(true);
        return this;
    }
}
