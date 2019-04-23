/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.data;

import org.seedstack.business.internal.utils.BusinessUtils;

/**
 * An helper base class that can be extended to create a data importer. If extending this base class is not desirable,
 * you can instead implement {@link DataImporter}.
 *
 * @param <T> The type of exported data.
 */
public abstract class BaseDataImporter<T> implements DataImporter<T> {
    private static final int DATA_CLASS_INDEX = 0;
    private final Class<T> dataClass;

    /**
     * Creates a base data importer. Actual data class is determined by reflection.
     */
    @SuppressWarnings("unchecked")
    protected BaseDataImporter() {
        dataClass = (Class<T>) BusinessUtils.resolveGenerics(BaseDataImporter.class, getClass())[DATA_CLASS_INDEX];
    }

    /**
     * Creates a base data importer. Actual data class is specified explicitly. This can be used to create a dynamic
     * implementation of a data importer.
     *
     * @param dataClass the data class.
     */
    protected BaseDataImporter(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    @Override
    public Class<T> getImportedClass() {
        return dataClass;
    }
}
