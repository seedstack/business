/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import org.seedstack.seed.Ignore;

@Ignore
@Deprecated
public interface GenericFactory<P extends Producible> extends Factory<P> {
    // This interface is marked @Ignore to avoid being detected as an actual factory
}
