/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.data;

import java.util.stream.Stream;

/**
 * Marker of a data set beginning in a exported stream.
 */
class DataSetMarker<T> {
    private String group;
    private String name;
    private Stream<T> items;

    DataSetMarker() {
    }

    DataSetMarker(String group, String name, Stream<T> items) {
        this.group = group;
        this.name = name;
        this.items = items;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stream<T> getItems() {
        return items;
    }

    public void setItems(Stream<T> items) {
        this.items = items;
    }
}
