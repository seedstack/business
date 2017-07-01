/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.SpecificationPicker;

class BaseSelectorImpl<T, SELECTOR extends BaseSelector<T, SELECTOR>> implements BaseSelector<T, SELECTOR> {
    protected final SpecificationBuilderContext<T, SELECTOR> context;

    @SuppressWarnings("unchecked")
    BaseSelectorImpl(SpecificationBuilderContext<T, SELECTOR> context) {
        this.context = context;
        this.context.setSelector((SELECTOR) this);
    }

    @Override
    public SpecificationPicker<T, SELECTOR> property(String path) {
        context.setProperty(path);
        return new SpecificationPickerImpl<>(context);
    }
}
