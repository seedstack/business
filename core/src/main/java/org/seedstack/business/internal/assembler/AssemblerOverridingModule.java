/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import org.seedstack.business.assembler.Assembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class AssemblerOverridingModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblerOverridingModule.class);
    private final Map<Key<Assembler>, Class<? extends Assembler>> bindings;

    AssemblerOverridingModule(Map<Key<Assembler>, Class<? extends Assembler>> bindings) {
        this.bindings = bindings;
    }

    @Override
    protected void configure() {
        for (Map.Entry<Key<Assembler>, Class<? extends Assembler>> binding : bindings.entrySet()) {
            LOGGER.trace("Overriding {} with {}", binding.getKey(), binding.getValue().getSimpleName());
            bind(binding.getKey()).to(binding.getValue());
        }
    }
}
