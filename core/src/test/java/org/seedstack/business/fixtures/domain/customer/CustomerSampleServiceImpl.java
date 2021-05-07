/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.customer;

import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

public class CustomerSampleServiceImpl implements CustomerSampleService, CustomerSampleDomainService {

    @Logging
    private Logger logger;
    @Configuration("org.seedstack.toto")
    private String property;

    public CustomerSampleServiceImpl() {
    }

    @Override
    public String property() {
        return property;
    }

    @Override
    public String transfer(Customer source, Customer target) {
        logger.info("Transfering customer " + source.getId() + " to customer " + target.getId());
        return property;
    }
}

