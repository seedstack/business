/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination;

import java.util.Collections;
import java.util.List;

/**
 * Represents a slice of items taken from a bigger list.
 *
 * @param <T> the item type
 */
public class SimpleSlice<T> implements Slice<T> {

    private final long size;
    private final List<T> items;

    /**
     * This constructor take a list of items that can potentially be huge.
     *
     * @param items The big list to be viewed.
     */
    public SimpleSlice(List<T> items) {
        this.items = items;
        this.size = items.size();
    }

    @Override
    public List<T> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    @Override
    public long getSize() {
        return size;
    }
}
