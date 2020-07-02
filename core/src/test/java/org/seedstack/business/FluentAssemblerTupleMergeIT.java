/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import static junit.framework.TestCase.fail;

import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.Customer;
import org.seedstack.business.fixtures.assembler.customer.CustomerRepository;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.business.fixtures.assembler.customer.Recipe;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class FluentAssemblerTupleMergeIT {
    @Inject
    private Repository<Order, String> orderRepository;
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private OrderFactory orderFactory;
    @Inject
    private FluentAssembler fluently;

    @Test
    public void mergeFromFactory() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");

        Pair<Order, Customer> orderCustomerPair = fluently.merge(recipe)
                .into(Order.class, Customer.class)
                .fromFactory();

        Assertions.assertThat(orderCustomerPair.getValue0())
                .isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0()
                .getId())
                .isEqualTo("order1");
        Assertions.assertThat(orderCustomerPair.getValue0()
                .getProduct())
                .isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1()
                .getId())
                .isEqualTo("customer1");
        Assertions.assertThat(orderCustomerPair.getValue1()
                .getName())
                .isEqualTo("luke");
    }

    @Test
    public void mergeFromRepository() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");

        Order order = orderFactory.create("order1", "death star");
        order.setOtherDetails("some details");
        orderRepository.add(order);

        Customer customer = new Customer("customer1");
        customerRepository.add(customer);

        Pair<Order, Customer> orderCustomerPair = null;
        try {
            orderCustomerPair = fluently.merge(recipe)
                    .into(Order.class, Customer.class)
                    .fromRepository()
                    .orFail();
        } catch (AggregateNotFoundException e) {
            fail();
        }
        Assertions.assertThat(orderCustomerPair.getValue0())
                .isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0()
                .getId())
                .isEqualTo("order1");
        Assertions.assertThat(orderCustomerPair.getValue0()
                .getProduct())
                .isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1()
                .getId())
                .isEqualTo("customer1");
        Assertions.assertThat(orderCustomerPair.getValue1()
                .getName())
                .isEqualTo("luke");

        orderRepository.remove(order);
        customerRepository.remove(customer);
    }

    @Test
    public void mergeFromRepositoryFailing() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");
        try {
            fluently.merge(recipe)
                    .into(Order.class, Customer.class)
                    .fromRepository()
                    .orFail();
            fail("should not have loaded from repository");
        } catch (AggregateNotFoundException e) {
            Assertions.assertThat(e)
                    .isNotNull();
        }
    }

    @Test
    public void mergeFromRepositoryOrFactory() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");

        Pair<Order, Customer> orderCustomerPair = fluently.merge(recipe)
                .into(Order.class, Customer.class)
                .fromRepository()
                .orFromFactory();

        Assertions.assertThat(orderCustomerPair.getValue0())
                .isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0()
                .getId())
                .isEqualTo("order1");
        Assertions.assertThat(orderCustomerPair.getValue0()
                .getProduct())
                .isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1()
                .getId())
                .isEqualTo("customer1");
        Assertions.assertThat(orderCustomerPair.getValue1()
                .getName())
                .isEqualTo("luke");
    }

    @Test
    public void mergeIntoInstances() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");
        Customer customer = new Customer("customer1");
        customer.setName("lucky");

        fluently.merge(new Recipe("customer1", "luke", "order1", "light saber"))
                .into(order, customer);

        Assertions.assertThat(order)
                .isNotNull();
        Assertions.assertThat(customer)
                .isNotNull();

        Assertions.assertThat(order.getId())
                .isEqualTo("1");
        Assertions.assertThat(order.getOtherDetails())
                .isEqualTo("some details"); // kept info
        Assertions.assertThat(order.getProduct())
                .isEqualTo("light saber"); // new info
        Assertions.assertThat(customer.getName())
                .isEqualTo("luke"); // updated info
    }
}
