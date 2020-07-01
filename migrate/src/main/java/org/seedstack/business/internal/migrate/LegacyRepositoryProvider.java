/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.migrate;

import com.google.inject.Injector;
import com.google.inject.Key;
import javax.inject.Inject;
import javax.inject.Provider;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

class LegacyRepositoryProvider<A extends AggregateRoot<I>, I, T extends Repository<A, I>> implements Provider<T> {
    private final Key<T> key;
    @Inject
    private Injector injector;

    LegacyRepositoryProvider(Key<T> key) {
        this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        return (T) new LegacyRepositoryAdapter<>(injector.getInstance(key));
    }
}
