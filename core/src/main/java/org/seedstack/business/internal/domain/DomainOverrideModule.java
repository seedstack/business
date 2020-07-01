/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import java.util.Map;
import java.util.Map.Entry;
import org.seedstack.shed.reflect.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DomainOverrideModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainOverrideModule.class);
    private final Map<Key<?>, Class<?>> bindings;

    DomainOverrideModule(Map<Key<?>, Class<?>> bindings) {
        this.bindings = bindings;
    }

    @Override
    protected void configure() {
        for (Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
            LOGGER.trace("Overriding {} with {}", binding.getKey(), binding.getValue()
                    .getSimpleName());
            bind(binding.getKey()).to(Classes.cast(binding.getValue()));
        }
    }
}
