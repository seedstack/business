/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.pagination.Slice;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface MergeAs<T> {
    Stream<T> asStream();

    <C extends Collection<T>> C asCollection(Supplier<C> collectionSupplier);

    List<T> asList();

    Set<T> asSet();

    Slice<T> asSlice();

    T[] asArray();
}
