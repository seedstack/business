/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.events;

import org.seedstack.business.api.EventHandler;
import org.seedstack.business.api.domain.events.AggregateReadEvent;
import org.seedstack.business.api.domain.events.BaseAggregateEvent;
import org.seedstack.business.fixtures.domain.product.Product;
import org.seedstack.business.fixtures.domain.product.ProductFactory;
import org.seedstack.business.fixtures.domain.product.ProductId;
import org.seedstack.business.fixtures.domain.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.core.api.Logging;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.persistence.inmemory.api.Store;
import org.slf4j.Logger;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@Store("ProductInMemoryRepository")
@RunWith(SeedITRunner.class)
public class AggregateEventIT {

    private static ProductId receivedId = null;
    private static Product receivedAggregate = null;
    private static boolean aVeryCoolMethodWasCalled = false;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductFactory productFactory;

    private Product product;

    static class Handler implements EventHandler<BaseAggregateEvent> {

        @Logging
        private Logger logger;

        @Override
        public void handle(BaseAggregateEvent event) {
            Assertions.assertThat(logger).isNotNull();
            if (event.getContext().getMethodCalled().getName().equals("doLoad") || event.getContext().getMethodCalled().getName().equals("doDelete")) {
                Object o = event.getContext().getArgs()[0];
                if (ProductId.class.isAssignableFrom(o.getClass())) {
                    receivedId = (ProductId) o;
                }
            }
            if (event.getContext().getMethodCalled().getName().equals("doSave") || event.getContext().getMethodCalled().getName().equals("doPersist")) {
                Object o = event.getContext().getArgs()[0];
                if (Product.class.isAssignableFrom(o.getClass())) {
                    receivedAggregate = (Product) o;
                }
            }
        }
    }

    static class AggregateReadEventHandler implements EventHandler<AggregateReadEvent> {
        @Override
        public void handle(AggregateReadEvent event) {
            if (event.getContext().getMethodCalled().getName().equals("dummyMethod")) {
                aVeryCoolMethodWasCalled = true;
            }
        }
    }

    @Before
    public void initialize() {
        product = productFactory.createProduct(Short.MIN_VALUE, Short.MIN_VALUE);
    }

    @Test
    public void base_repository_event_was_received() {
        productRepository.persist(product);
        Assertions.assertThat(product).isEqualTo(receivedAggregate);

        ProductId id = new ProductId(Short.MIN_VALUE, "pok");
        productRepository.load(id);
        Assertions.assertThat(id).isEqualTo(receivedId);

        productRepository.dummyMethod();
        Assertions.assertThat(aVeryCoolMethodWasCalled).isTrue();
    }
}
