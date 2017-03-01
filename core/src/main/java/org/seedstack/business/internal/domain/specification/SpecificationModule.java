/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import com.google.inject.AbstractModule;
import org.seedstack.business.domain.specification.builder.SpecificationBuilder;
import org.seedstack.seed.core.internal.guice.BindingStrategy;

import java.util.Collection;

class SpecificationModule extends AbstractModule {
    private final Collection<BindingStrategy> bindingStrategies;

    public SpecificationModule(Collection<BindingStrategy> bindingStrategies) {
        this.bindingStrategies = bindingStrategies;
    }

    @Override
    protected void configure() {
        for (BindingStrategy bindingStrategy : bindingStrategies) {
            bindingStrategy.resolve(binder());
        }

        bind(SpecificationBuilder.class).to(SpecificationBuilderImpl.class);
    }
}
