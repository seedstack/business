/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import java.lang.annotation.Annotation;

/**
 * AssemblerTypes regroups all the default assemblers provided by the framework. It can be used in the {@link org.seedstack.business.assembler.FluentAssembler}
 */
public enum AssemblerTypes {

    MODEL_MAPPER(ModelMapper.class);

    private final Class<? extends Annotation> annotationClass;

    AssemblerTypes(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public Class<? extends Annotation> get() {
        return annotationClass;
    }
}
