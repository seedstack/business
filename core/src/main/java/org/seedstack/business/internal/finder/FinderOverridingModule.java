/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.finder;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;

class FinderOverridingModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinderOverridingModule.class);
    private final Map<Key<?>, Class<?>> bindings;

    FinderOverridingModule(Map<Key<?>, Class<?>> bindings) {
        this.bindings = bindings;
    }

    @Override
    protected void configure() {
        for (Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
            LOGGER.trace("Overriding {} with {}", binding.getKey(), binding.getValue().getSimpleName());
            bind(binding.getKey()).to(cast(binding.getValue()));
        }
    }

    @SuppressWarnings("unchecked")
    private <C extends Class<?>> C cast(Class<?> aClass) {
        return (C) aClass;
    }
}
