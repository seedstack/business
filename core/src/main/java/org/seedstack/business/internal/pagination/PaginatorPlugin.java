/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.internal.pagination;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;

/**
 * Plugin handling specifications.
 */
public class PaginatorPlugin extends AbstractSeedPlugin {

    @Override
    public String name() {
        return "business-pagination";
    }

    @Override
    public InitState initialize(InitContext initContext) {
        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new PaginatorModule();
    }
}
