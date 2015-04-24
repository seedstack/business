/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces;

import com.google.inject.assistedinject.Assisted;
import io.nuun.kernel.api.annotations.Ignore;
import org.modelmapper.ModelMapper;
import org.seedstack.business.api.domain.AggregateRoot;

import javax.inject.Inject;

/**
 * This class is a default assembler based on ModelMapper.
 * <p>
 * If an injection point {@code AutomaticAssembler&lt;A, D&gt;} is defined and any class extending {@code AutomaticAssembler}
 * for A and D exists, this default assembler will be injected.
 * </p>
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
@Ignore
public class DefaultAssembler<A extends AggregateRoot<?>,D> extends AutomaticAssembler<A, D> {

    @SuppressWarnings("unchecked")
    @Inject
    public DefaultAssembler(@Assisted Object[] genericClasses) {
        super((Class) genericClasses.clone()[1]);
    }

    @Override
    protected ModelMapper configureAssembly() {
        return new ModelMapper();
    }

    @Override
    protected ModelMapper configureMerge() {
        return new ModelMapper();
    }
}
