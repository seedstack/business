/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.seedstack.business.assembler.dsl.MergeAs;
import org.seedstack.business.pagination.SimpleSlice;
import org.seedstack.business.pagination.Slice;

class MergeAsImpl<T> implements MergeAs<T> {

    private final Stream<T> stream;

    MergeAsImpl(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public Stream<T> asStream() {
        return stream;
    }

    @Override
    public <C extends Collection<T>> C asCollection(Supplier<C> collectionSupplier) {
        C collection = collectionSupplier.get();
        stream.forEach(collection::add);
        return collection;
    }

    @Override
    public List<T> asList() {
        return asCollection(ArrayList::new);
    }

    @Override
    public Set<T> asSet() {
        return asCollection(HashSet::new);
    }

    @Override
    public Slice<T> asSlice() {
        return new SimpleSlice<>(asList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] asArray() {
        return stream.toArray(size -> (T[]) new Object[size]);
    }
}
