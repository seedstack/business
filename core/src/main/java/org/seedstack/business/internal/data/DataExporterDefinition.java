/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.data;

import org.seedstack.business.data.DataExporter;

/**
 * Holds the definition of a {@link DataExporter}.
 *
 * @param <T> the exported data type.
 */
class DataExporterDefinition<T> {
    private final String group;
    private final String name;
    private final Class<T> exportedClass;

    DataExporterDefinition(String group, String name, Class<T> exportedClass) {
        this.group = group;
        this.name = name;
        this.exportedClass = exportedClass;
    }

    String getGroup() {
        return group;
    }

    String getName() {
        return name;
    }

    public Class<T> getExportedClass() {
        return exportedClass;
    }
}
