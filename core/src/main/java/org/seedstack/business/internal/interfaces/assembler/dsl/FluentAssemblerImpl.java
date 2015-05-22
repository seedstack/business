/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl;

import org.seedstack.business.api.interfaces.assembler.AssemblerTypes;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AssemblerProvider;

import javax.inject.Inject;
import java.lang.annotation.Annotation;

/**
 * Implementation of {@link org.seedstack.business.api.interfaces.assembler.FluentAssembler}.
 * <p>
 * It uses a Guice provider to get the DSL entry point. Each time you call the {@code assemble()}
 * method a new DSL instance is provided.
 * </p>
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class FluentAssemblerImpl implements FluentAssembler {

    @Inject
    private AssemblerProviderFactory assemblerProviderFactory;

    /**
     * The assembler DSL entry point.
     *
     * @return the next DSL element
     */
    public AssemblerProvider assemble(){
        return assemblerProviderFactory.create(new AssemblerDslContext());
    }

    @Override
    public AssemblerProvider assemble(Annotation qualifier) {
        AssemblerDslContext context = new AssemblerDslContext();
        context.setAssemblerAnnotationQualifier(qualifier);
        return assemblerProviderFactory.create(context);
    }

    @Override
    public AssemblerProvider assemble(AssemblerTypes qualifier) {
        AssemblerDslContext context = new AssemblerDslContext();
        context.setAssemblerAnnotationClassQualifier(qualifier.get());
        return assemblerProviderFactory.create(context);
    }

    @Override
    public AssemblerProvider assemble(Class<? extends Annotation> qualifier) {
        AssemblerDslContext context = new AssemblerDslContext();
        context.setAssemblerAnnotationClassQualifier(qualifier);
        return assemblerProviderFactory.create(context);
    }
}
