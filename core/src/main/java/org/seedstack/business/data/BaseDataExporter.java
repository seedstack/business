/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.data;

import org.seedstack.business.internal.utils.BusinessUtils;

/**
 * An helper base class that can be extended to create a data exporter. If extending this base class is not desirable,
 * you can instead implement {@link DataExporter}.
 *
 * @param <T> The type of exported data.
 */
public abstract class BaseDataExporter<T> implements DataExporter<T> {
    private static final int DATA_CLASS_INDEX = 0;
    private final Class<T> dataClass;

    /**
     * Creates a base data exporter. Actual data class is determined by reflection.
     */
    @SuppressWarnings("unchecked")
    protected BaseDataExporter() {
        dataClass = (Class<T>) BusinessUtils.resolveGenerics(BaseDataExporter.class, getClass())[DATA_CLASS_INDEX];
    }

    /**
     * Creates a base data exporter. Actual data class is specified explicitly. This can be used to create a dynamic
     * implementation of a data exporter.
     *
     * @param dataClass the data class.
     */
    protected BaseDataExporter(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    @Override
    public Class<T> getExportedClass() {
        return dataClass;
    }
}
