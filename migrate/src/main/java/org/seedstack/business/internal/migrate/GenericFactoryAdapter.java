/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.GenericFactory;
import org.seedstack.business.domain.Producible;

class GenericFactoryAdapter<P extends Producible> implements GenericFactory<P> {
    private final Factory<P> factory;

    GenericFactoryAdapter(Factory<P> factory) {
        this.factory = factory;
    }

    @Override
    public Class<P> getProducedClass() {
        return factory.getProducedClass();
    }

    @Override
    public P create(Object... args) {
        return factory.create(args);
    }
}
