/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.data;

import org.seedstack.business.data.DataImporter;

/**
 * Holds the definition of a {@link DataImporter}.
 *
 * @param <T> the imported data type.
 */
class DataImporterDefinition<T> {
    private final String group;
    private final String name;
    private final Class<T> importedClass;

    DataImporterDefinition(String group, String name, Class<T> importedClass) {
        this.group = group;
        this.name = name;
        this.importedClass = importedClass;
    }

    Class<T> getImportedClass() {
        return importedClass;
    }

    String getGroup() {
        return group;
    }

    String getName() {
        return name;
    }

}
