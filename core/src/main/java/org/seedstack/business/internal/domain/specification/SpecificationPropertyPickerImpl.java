/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.builder.SpecificationPicker;
import org.seedstack.business.domain.specification.builder.SpecificationPropertyPicker;

class SpecificationPropertyPickerImpl<A extends AggregateRoot<?>> implements SpecificationPropertyPicker<A> {
    private final SpecificationBuilderContext<A> context;

    SpecificationPropertyPickerImpl(SpecificationBuilderContext<A> context) {
        this.context = context;
    }

    @Override
    public SpecificationPicker<A> property(String path) {
        context.setProperty(path);
        return new SpecificationPickerImpl<>(context);
    }
}
