/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import org.seedstack.coffig.Config;

@Config("business")
public class BusinessConfig {
    private PaginationConfig pagination = new PaginationConfig();
    private DataConfig data = new DataConfig();

    public PaginationConfig pagination() {
        return pagination;
    }

    public DataConfig data() {
        return this.data;
    }

    @Config("pagination")
    public static class PaginationConfig {
        private boolean zeroBasedPageIndex = false;

        public boolean isZeroBasedPageIndex() {
            return zeroBasedPageIndex;
        }

        public PaginationConfig setZeroBasedPageIndex(boolean zeroBasedPageIndex) {
            this.zeroBasedPageIndex = zeroBasedPageIndex;
            return this;
        }
    }

    @Config("data")
    public static class DataConfig {
        private boolean importOnStart = true;
        private boolean clearBeforeImport = true;
        private boolean forceImport = false;

        public boolean isImportOnStart() {
            return importOnStart;
        }

        public DataConfig setImportOnStart(boolean importOnStart) {
            this.importOnStart = importOnStart;
            return this;
        }

        public boolean isClearBeforeImport() {
            return clearBeforeImport;
        }

        public DataConfig setClearBeforeImport(boolean clearBeforeImport) {
            this.clearBeforeImport = clearBeforeImport;
            return this;
        }

        public boolean isForceImport() {
            return forceImport;
        }

        public DataConfig setForceImport(boolean forceImport) {
            this.forceImport = forceImport;
            return this;
        }
    }
}
