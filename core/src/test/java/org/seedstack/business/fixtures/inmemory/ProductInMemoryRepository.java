/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.inmemory;

import org.seedstack.business.fixtures.domain.product.Product;
import org.seedstack.business.fixtures.domain.product.ProductId;
import org.seedstack.business.fixtures.domain.product.ProductRepository;
import org.seedstack.business.util.inmemory.BaseInMemoryRepository;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

public class ProductInMemoryRepository extends BaseInMemoryRepository<Product, ProductId> implements ProductRepository {

    @Logging
    private Logger logger;

    public void dummyMethod() {
        logger.info("Very useful method execution.");
    }
}
