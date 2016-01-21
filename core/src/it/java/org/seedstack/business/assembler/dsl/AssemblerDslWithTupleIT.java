/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.assembler.AssemblerTypes;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.*;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

import static junit.framework.TestCase.fail;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@RunWith(SeedITRunner.class)
public class AssemblerDslWithTupleIT {

    @Inject
    private Repository<Order, String> orderRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private OrderFactory orderFactory;

    @Inject
    private FluentAssembler fluently;

    @Test
    public void testAssembleFromFactory() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");

        Pair<Order, Customer> orderCustomerPair = fluently.merge(recipe).with(AssemblerTypes.MODEL_MAPPER).into(Order.class, Customer.class).fromFactory();

        Assertions.assertThat(orderCustomerPair.getValue0()).isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0().getEntityId()).isEqualTo("order1");
        Assertions.assertThat(orderCustomerPair.getValue0().getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1().getEntityId()).isEqualTo("customer1");
        Assertions.assertThat(orderCustomerPair.getValue1().getName()).isEqualTo("luke");
    }

    @Test
    public void testAssembleFromRepository() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");

        Order order = orderFactory.create("order1", "death star");
        order.setOtherDetails("some details");
        orderRepository.persist(order);

        Customer customer = new Customer("customer1");
        customerRepository.persist(customer);

        Pair<Order, Customer> orderCustomerPair = null;
        try {
            orderCustomerPair = fluently.merge(recipe).with(AssemblerTypes.MODEL_MAPPER).into(Order.class, Customer.class).fromRepository().orFail();
        } catch (AggregateNotFoundException e) {
            fail();
        }
        Assertions.assertThat(orderCustomerPair.getValue0()).isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0().getEntityId()).isEqualTo("order1");
        Assertions.assertThat(orderCustomerPair.getValue0().getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1().getEntityId()).isEqualTo("customer1");
        Assertions.assertThat(orderCustomerPair.getValue1().getName()).isEqualTo("luke");

        orderRepository.delete(order);
        customerRepository.delete(customer);
    }

    @Test
    public void testAssembleFromRepositoryOrFail() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");
        try {
            fluently.merge(recipe).with(AssemblerTypes.MODEL_MAPPER).into(Order.class, Customer.class).fromRepository().orFail();
            fail();
        } catch (AggregateNotFoundException e) {
            Assertions.assertThat(e).isNotNull();
        }
    }

    @Test
    public void testAssembleFromRepositoryOrFactory() {
        Recipe recipe = new Recipe("customer1", "luke", "order1", "light saber");

        Pair<Order, Customer> orderCustomerPair = fluently.merge(recipe).with(AssemblerTypes.MODEL_MAPPER).into(Order.class, Customer.class).fromRepository().orFromFactory();

        Assertions.assertThat(orderCustomerPair.getValue0()).isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0().getEntityId()).isEqualTo("order1");
        Assertions.assertThat(orderCustomerPair.getValue0().getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1().getEntityId()).isEqualTo("customer1");
        Assertions.assertThat(orderCustomerPair.getValue1().getName()).isEqualTo("luke");
    }

    @Test
    public void testAssembleFromDto() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");
        Customer customer = new Customer("customer1");
        customer.setName("lucky");

        fluently.merge(new Recipe("customer1", "luke", "order1", "light saber")).with(AssemblerTypes.MODEL_MAPPER).into(order, customer);

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(customer).isNotNull();

        Assertions.assertThat(order.getEntityId()).isEqualTo("1");
        Assertions.assertThat(order.getOtherDetails()).isEqualTo("some details"); // kept info
        Assertions.assertThat(order.getProduct()).isEqualTo("light saber"); // new info
        Assertions.assertThat(customer.getName()).isEqualTo("luke"); // updated info
    }
}
