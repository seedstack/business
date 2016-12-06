/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.seed.persistence.inmemory.internal;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.seedstack.seed.persistence.inmemory.InMemory;

import java.lang.reflect.Field;
import java.util.Map;


class InMemoryTypeListener implements TypeListener {
    private final InMemoryTransactionLink inMemoryTransactionLink;

    InMemoryTypeListener(InMemoryTransactionLink inMemoryTransactionLink) {
        this.inMemoryTransactionLink = inMemoryTransactionLink;
    }

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        for (Class<?> c = type.getRawType(); c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (Map.class.isAssignableFrom(field.getType()) && (field.getAnnotation(InMemory.class)) != null) {
                    encounter.register(new InMemoryMapMembersInjector<>(field, inMemoryTransactionLink));
                }
            }
        }

    }

}