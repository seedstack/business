/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.builder.SpecificationBuilder;
import org.seedstack.business.specification.builder.SpecificationPropertyPicker;

class SpecificationBuilderImpl implements SpecificationBuilder {
    public <T> SpecificationPropertyPicker<T> of(Class<T> someClass) {
        return new SpecificationPropertyPickerImpl<>(new SpecificationBuilderContext<>(someClass));
    }
}
