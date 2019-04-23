/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.data;

import java.util.stream.Stream;

/**
 * Implement this interface to create a data set importer that will handle objects of a specific
 * type. A data importer must be marked with a {@link DataSet} annotation to be recognized.
 *
 * @param <T> the type this data importer handles.
 */
public interface DataImporter<T> {

    /**
     * This method is used to determine if a data importer should be automatically initialized with
     * data.
     *
     * @return true if already initialized (and as such won't be automatically initialized), false
     *         otherwise.
     */
    boolean isInitialized();

    /**
     * This method is called when the backing persistence must be cleared before import.
     */
    void clear();

    /**
     * This method is called when a stream of objects is ready to import.
     *
     * @param data the object to import.
     */
    void importData(Stream<T> data);

    /**
     * Returns the data class managed by the importer.
     *
     * @return the data class.
     */
    Class<T> getImportedClass();
}
