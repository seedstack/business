/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.application.internal;

import org.seedstack.business.fixtures.application.IndexService;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

public class IndexServiceInternal implements IndexService {

    @Logging
    private Logger logger;

    @Override
    public void index(Customer customer) {
        logger.info("Indexing " + customer.getId());
    }
}
