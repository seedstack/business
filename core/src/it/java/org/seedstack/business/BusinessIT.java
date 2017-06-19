/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import org.junit.Test;
import org.seedstack.business.fixtures.application.internal.GenericServiceInternal;
import org.seedstack.business.fixtures.application.internal.IndexServiceInternal;
import org.seedstack.business.fixtures.domain.activation.Activation;
import org.seedstack.business.fixtures.domain.activation.ActivationException;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerFactoryDefault;
import org.seedstack.business.fixtures.domain.customer.CustomerId;
import org.seedstack.business.fixtures.domain.customer.internal.CustomerSampleServiceInternal;
import org.seedstack.business.fixtures.domain.order.Order;
import org.seedstack.business.fixtures.domain.order.OrderFactoryDefault;
import org.seedstack.business.fixtures.domain.order.OrderId;
import org.seedstack.business.fixtures.domain.product.InternalProductFactory;
import org.seedstack.business.fixtures.domain.product.InternalProductNamePolicy;
import org.seedstack.business.fixtures.domain.product.Product;
import org.seedstack.business.fixtures.domain.product.ProductId;
import org.seedstack.business.fixtures.infrastructure.presentation.customer.JpaCustomerFinder;
import org.seedstack.business.fixtures.interfaces.customer.presentation1.CustomerRepresentation;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.persistence.inmemory.Store;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BusinessIT extends AbstractSeedIT {
    @Inject
    Holder holder;

    @Test
    @Store("business_core_plugin_it_01")
    public void all_bindings_should_be_bound() throws ActivationException {
        assertThat(holder.property()).isNotNull();
        assertThat(holder.property()).isEqualTo("toto");

        assertThat(holder.activationRepository).isNotNull();
        assertThat(holder.activationFactory).isNotNull();
        assertThat(holder.activationFactory.createNewActivation("id", "pok")).isNotNull();

        assertThat(holder.customerRepo).isNotNull();
        assertThat(holder.customerFactory).isNotNull();
        assertThat(CustomerFactoryDefault.class.isAssignableFrom(holder.customerFactory.getClass())).isTrue();

        assertThat(holder.orderRepo).isNotNull();
        assertThat(holder.orderFactory).isNotNull();
        assertThat(OrderFactoryDefault.class.isAssignableFrom(holder.orderFactory.getClass())).isTrue();

        assertThat(holder.productRepo).isNotNull();
        assertThat(holder.productFactory).isNotNull();
        assertThat(InternalProductFactory.class.isAssignableFrom(holder.productFactory.getClass())).isTrue();
        assertThat(holder.productNamePolicy).isNotNull();
        assertThat(InternalProductNamePolicy.class.isAssignableFrom(holder.productNamePolicy.getClass())).isTrue();


        // Application Service
        assertThat(holder.indexService).isNotNull();
        assertThat(IndexServiceInternal.class.isAssignableFrom(holder.indexService.getClass())).isTrue();
        assertThat(holder.genericService).isNotNull();
        assertThat(GenericServiceInternal.class.isAssignableFrom(holder.genericService.getClass())).isTrue();

        // Domain Service
        assertThat(holder.customerService).isNotNull();
        assertThat(CustomerSampleServiceInternal.class.isAssignableFrom(holder.customerService.getClass())).isTrue();

        // Finder
        assertThat(holder.customerFinder).isNotNull();
        assertThat(JpaCustomerFinder.class.isAssignableFrom(holder.customerFinder.getClass())).isTrue();

        // Functional Cases

        // Create customers and launch services
        Customer testCusto = holder.customerFactory.createNewCustomer("key1", "test1", "");
        Customer c2 = holder.customerFactory.createNewCustomer("2", "f1", "l1");

        holder.indexService.index(testCusto);
        holder.customerService.transfer(testCusto, c2);

        assertThat(holder.customerService.property()).isNotNull();
        assertThat(holder.customerService.property()).isEqualTo("toto");

        // Product creation
        Product product1 = holder.productFactory.createProduct((short) 1, (short) 1);
        product1.setName("Basket Air Jordan");
        product1.setDescription("Da Pair of basket evaa!");


        Product product2 = holder.productFactory.createProduct((short) 2, (short) 5);
        product2.setName("Tea mandarin tetley");
        product2.setDescription("White tea for digestion.");

        holder.productRepo.add(product1);
        holder.productRepo.add(product2);

        Product loadedProduct1 = holder.productRepo.get(new ProductId((short) 1, "ean13-1")).get();
        Product loadedProduct2 = holder.productRepo.get(new ProductId((short) 2, (short) 5)).get();

        assertThat(loadedProduct1.getId().getStoreId()).isEqualTo((short) 1);
        assertThat(loadedProduct1.getId().getProductCode()).isEqualTo("ean13-1");
        assertThat(loadedProduct1.getId()).isEqualTo(new ProductId((short) 1, "ean13-1"));
        assertThat(loadedProduct1.getName()).isEqualTo("Basket Air Jordan");
        assertThat(loadedProduct1.getDescription()).isEqualTo("Da Pair of basket evaa!");

        assertThat(loadedProduct2.getId().getStoreId()).isEqualTo((short) 2);
        assertThat(loadedProduct2.getId().getProductCode()).isEqualTo("ean13-5");
        assertThat(loadedProduct2.getId()).isEqualTo(new ProductId((short) 2, "ean13-5"));
        assertThat(loadedProduct2.getName()).isEqualTo("Tea mandarin tetley");
        assertThat(loadedProduct2.getDescription()).isEqualTo("White tea for digestion.");

        assertThat(loadedProduct1).isNotNull();
        assertThat(loadedProduct1.getId().getStoreId()).isEqualTo((short) 1);
        assertThat(loadedProduct1.getId().getProductCode()).isEqualTo("ean13-1");


        Order order1 = holder.orderFactory.createOrder("commande01");
        OrderId oi1 = order1.getId();
        assertThat(oi1).isEqualTo(new OrderId("commande01"));
        order1.setCustomerId(c2.getId());

        // persist
        holder.customerRepo.add(testCusto);
        holder.orderRepo.add(order1);

        // find
        List<CustomerRepresentation> findAll = holder.customerFinder.findAll();
        assertThat(findAll).isNotNull();
        assertThat(findAll).hasSize(1);

        // persist
        holder.customerRepo.add(c2);

        Order load2_ = holder.orderRepo.get(new OrderId("commande01")).get();
        assertThat(load2_).isNotNull();

        Customer custLoadedKey1 = holder.customerRepo.get(new CustomerId("key1")).get();
        assertThat(custLoadedKey1).isNotNull();

        assertThat(load2_.getId().getValue()).isEqualTo(new OrderId("commande01").getValue());
        assertThat(load2_.getCustomerId()).isEqualTo(new CustomerId("2"));

        // find
        findAll = holder.customerFinder.findAll();
        assertThat(findAll).isNotNull();
        assertThat(findAll).hasSize(2);
//		assertThat(findAll).
//		assertThat(findAll).onProperty("name").contains("f1 l1" , "test1 "); // TODO : to re set when fest-util 1.2.4 and 1.1.6 is resolved

        CustomerId entityId = new CustomerId("key1");
        Customer load = holder.customerRepo.get(entityId).get();
        assertThat(load).isNotNull();

        entityId = new CustomerId("2");
        load = holder.customerRepo.get(entityId).get();
        assertThat(load).isNotNull();

        String uuid;
        uuid = UUID.randomUUID().toString();
        Activation activation = holder.activationFactory.createNewActivation(uuid, "new activation");
        activation.setActivationDate(new Date());
        activation.setCreationDate(new Date());
        holder.activationRepository.add(activation);
    }
}
