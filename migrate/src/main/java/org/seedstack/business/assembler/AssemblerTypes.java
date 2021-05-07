/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import java.lang.annotation.Annotation;
import org.seedstack.business.modelmapper.ModelMapper;

@Deprecated
public enum AssemblerTypes implements Annotation {
    MODEL_MAPPER(ModelMapper.class);

    private final Class<ModelMapper> annoClass;

    AssemblerTypes(Class<ModelMapper> annoClass) {
        this.annoClass = annoClass;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return annoClass;
    }
}
