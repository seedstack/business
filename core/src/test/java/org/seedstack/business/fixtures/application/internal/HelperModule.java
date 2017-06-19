/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.application.internal;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.seedstack.business.fixtures.application.GenericService;
import org.seedstack.seed.Install;

@Install
public class HelperModule extends AbstractModule {
    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        bind(new TypeLiteral<GenericService<String>>(){}).to((Class) GenericServiceInternal.class);
    }
}
