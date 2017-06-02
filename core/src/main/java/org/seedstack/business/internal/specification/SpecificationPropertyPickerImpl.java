/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.builder.SpecificationPicker;
import org.seedstack.business.specification.builder.SpecificationPropertyPicker;

class SpecificationPropertyPickerImpl<T> implements SpecificationPropertyPicker<T> {
    private final SpecificationBuilderContext<T> context;

    SpecificationPropertyPickerImpl(SpecificationBuilderContext<T> context) {
        this.context = context;
    }

    @Override
    public SpecificationPicker<T> property(String path) {
        context.setProperty(path);
        return new SpecificationPickerImpl<>(context);
    }
}
