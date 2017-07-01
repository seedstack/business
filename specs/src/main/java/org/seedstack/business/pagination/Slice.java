/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A slice of contiguous items taken from a bigger list.
 *
 * @param <T> the item type
 */
public interface Slice<T> extends Iterable<T> {
    /**
     * @return the items.
     */
    List<T> getItems();

    /**
     * Return the number of the view result.
     *
     * @return the number of items.
     */
    long getSize();

    @Override
    default Iterator<T> iterator() {
        return getItems().iterator();
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        getItems().forEach(action);
    }

    @Override
    default Spliterator<T> spliterator() {
        return getItems().spliterator();
    }
}
