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
import org.javatuples.Tuple;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;

/**
 * This class is a default  tuple assembler based on ModelMapper.
 * <p>
 * This is the same as {@link org.seedstack.business.core.interfaces.DefaultAssembler} but it supports tuple of aggregates.
 * </p>
 *
 * @see org.seedstack.business.core.interfaces.DefaultAssembler
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
@Ignore
public class DefaultTupleAssembler<T extends Tuple, D> extends AutomaticTupleAssembler<T, D> {

    @SuppressWarnings("unchecked")
    @Inject
    public DefaultTupleAssembler(@Assisted Object[] genericClasses) {
        super((ParameterizedType) genericClasses.clone()[0], (Class) genericClasses.clone()[1]);
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
