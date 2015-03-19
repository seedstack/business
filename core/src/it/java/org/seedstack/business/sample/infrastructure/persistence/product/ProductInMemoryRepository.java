/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.infrastructure.persistence.product;

import org.seedstack.business.core.domain.InMemoryRepository;
import org.seedstack.business.sample.domain.product.Product;
import org.seedstack.business.sample.domain.product.ProductId;
import org.seedstack.business.sample.domain.product.ProductRepository;
import org.seedstack.seed.core.api.Logging;
import org.seedstack.seed.persistence.inmemory.api.Store;
import org.slf4j.Logger;

@Store("ProductInMemoryRepository")
public class ProductInMemoryRepository extends InMemoryRepository<Product, ProductId> implements ProductRepository{


    @Logging
    private Logger logger;

    public void dummyMethod() {
        logger.info("Very useful method execution.");
    }
}
