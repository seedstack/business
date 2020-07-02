/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.StringSpecification;
import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.StringOptionPicker;

class StringOptionPickerImpl<T, S extends BaseSelector<T, S>> extends OperatorPickerImpl<T, S> implements
        StringOptionPicker<T, S> {

    private final StringSpecification.Options stringValueOptions;

    StringOptionPickerImpl(SpecificationBuilderContext<T, S> context, StringSpecification.Options stringValueOptions) {
        super(context);
        this.stringValueOptions = stringValueOptions;
    }

    @Override
    public StringOptionPicker<T, S> trimming() {
        stringValueOptions.setTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T, S> trimmingLead() {
        stringValueOptions.setLeadTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T, S> trimmingTail() {
        stringValueOptions.setTailTrimmed(true);
        return this;
    }

    @Override
    public StringOptionPicker<T, S> ignoringCase() {
        stringValueOptions.setIgnoringCase(true);
        return this;
    }
}
