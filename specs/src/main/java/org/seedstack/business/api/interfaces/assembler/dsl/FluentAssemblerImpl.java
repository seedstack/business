/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;

import org.seedstack.business.api.interfaces.assembler.FluentAssembler;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Implementation of {@link org.seedstack.business.api.interfaces.assembler.FluentAssembler}.
 * <p>
 * It uses a Guice provider to get the DSL entry point. Each time you call the {@code assemble()}
 * method a new DSL instance is provided.
 * </p>
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class FluentAssemblerImpl implements FluentAssembler {

    @Inject
    private Provider<Assemble> assembleSecurelyProvider;

    /**
     * The assembler DSL entry point.
     *
     * @return the next DSL element
     */
    public Assemble assemble(){
        return assembleSecurelyProvider.get();
    }
}
