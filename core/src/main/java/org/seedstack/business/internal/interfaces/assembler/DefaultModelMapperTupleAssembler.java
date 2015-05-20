/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler;

import com.google.inject.assistedinject.Assisted;
import org.javatuples.Tuple;
import org.modelmapper.ModelMapper;
import org.seedstack.business.api.DefaultImpl;
import org.seedstack.business.core.interfaces.assembler.ModelMapperTupleAssembler;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class is a default  tuple assembler based on ModelMapper.
 * <p>
 * This is the same as {@link DefaultModelMappedAssembler} but it supports tuple of aggregates.
 * </p>
 *
 * @see DefaultModelMappedAssembler
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DefaultImpl
@Named("ModelMapper")
public class DefaultModelMapperTupleAssembler<T extends Tuple, D> extends ModelMapperTupleAssembler<T, D> {

    @SuppressWarnings("unchecked")
    @Inject
    public DefaultModelMapperTupleAssembler(@Assisted Object[] genericClasses) {
        // TODO the first parameter is useless remove it
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
