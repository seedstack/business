/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler;

import com.google.inject.assistedinject.Assisted;
import org.javatuples.Tuple;
import org.modelmapper.ModelMapper;
import org.seedstack.business.api.interfaces.assembler.ModelMapperTupleAssembler;
import org.seedstack.business.spi.GenericImplementation;

import javax.inject.Inject;

/**
 * This class is a default  tuple assembler based on ModelMapper.
 * <p>
 * This is the same as {@link DefaultModelMapperAssembler} but it supports tuple of aggregates.
 * </p>
 *
 * @see DefaultModelMapperAssembler
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@GenericImplementation
@org.seedstack.business.api.interfaces.assembler.ModelMapper
public class DefaultModelMapperTupleAssembler<T extends Tuple, D> extends ModelMapperTupleAssembler<T, D> {

    @SuppressWarnings("unchecked")
    @Inject
    public DefaultModelMapperTupleAssembler(@Assisted Object[] genericClasses) {
        // TODO the first parameter is useless remove it
        super((Class) genericClasses.clone()[1]);
    }

    @Override
    protected void configureAssembly(ModelMapper modelMapper) {
    }

    @Override
    protected void configureMerge(ModelMapper modelMapper) {
    }
}
