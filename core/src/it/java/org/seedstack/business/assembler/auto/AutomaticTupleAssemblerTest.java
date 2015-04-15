/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto;

import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.api.domain.base.BaseAggregateRoot;
import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.core.interfaces.AutomaticTupleAssembler;
import org.seedstack.business.api.Tuples;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 *         24/02/2015
 */
@RunWith(SeedITRunner.class)
public class AutomaticTupleAssemblerTest {

    @Inject
    private AutomaticTupleAssembler<Pair<Order, Customer>, OrderDTO> defaultTupleAssembler;

    @Test
    public void testAssembleDtoFromAggregate() {
        Customer customer = new Customer(new Name("John", "Doe"));
        Order order = new Order(new Address("main street", "bevillecity"));

        Pair<Order, Customer> tuple = Tuples.create(order, customer);
        OrderDTO orderDTO = defaultTupleAssembler.assembleDtoFromAggregate(tuple);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        Assertions.assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        Assertions.assertThat(orderDTO.billingStreet).isEqualTo("main street");
    }

    @Test
    public void testUpdateDtoFromAggregate() {
        Customer customer = new Customer(new Name("John", "Doe"));
        Order order = new Order( new Address("main street", "bevillecity"));
        OrderDTO orderDTO = new OrderDTO("Jane", "Doe", "", "");


        Pair<Order, Customer> tuple = Tuples.create(order, customer);
        defaultTupleAssembler.updateDtoFromAggregate(orderDTO, tuple);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        Assertions.assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        Assertions.assertThat(orderDTO.billingStreet).isEqualTo("main street");
    }

    //@Test
    public void testMergeAggregateWithDto() {
        Customer customer = new Customer(new Name("John", "Doe"));
        Order order = new Order(null);
        OrderDTO orderDTO = new OrderDTO("John", "Doe", "main street", "bevillecity");

        Pair<Order, Customer> tuple = Tuples.create(order, customer);
        defaultTupleAssembler.mergeAggregateWithDto(tuple, orderDTO);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        Assertions.assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        Assertions.assertThat(orderDTO.billingStreet).isEqualTo("main street");
    }

    static class Order extends BaseAggregateRoot<String> {
        String id;

        Address billingAddress;

        @Override
        public String getEntityId() {
            return id;
        }

        public Order() {
        }

        public Order(Address billingAddress) {
            this.billingAddress = billingAddress;
        }

        public Address getBillingAddress() {
            return billingAddress;
        }

        public void setBillingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
        }
    }

    static class Customer extends BaseAggregateRoot<String> {
        String id;

        Name name;

        @Override
        public String getEntityId() {
            return id;
        }

        public Customer() {
        }

        public Customer(Name name) {

            this.name = name;
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }
    }

    static class Name {
        String firstName;
        String lastName;

        public Name() {
        }

        public Name(String firstName, String lastName) {

            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    static class Address {
        String street;
        String city;

        public Address() {
        }

        public Address(String street, String city) {

            this.street = street;
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    @DtoOf({Order.class, Customer.class})
    static class OrderDTO {
        String customerFirstName;
        String customerLastName;
        String billingStreet;
        String billingCity;

        public OrderDTO() {
        }

        public OrderDTO(String customerFirstName, String customerLastName, String billingStreet, String billingCity) {
            this.customerFirstName = customerFirstName;
            this.customerLastName = customerLastName;
            this.billingStreet = billingStreet;
            this.billingCity = billingCity;
        }

        public String getCustomerFirstName() {
            return customerFirstName;
        }

        public void setCustomerFirstName(String customerFirstName) {
            this.customerFirstName = customerFirstName;
        }

        public String getCustomerLastName() {
            return customerLastName;
        }

        public void setCustomerLastName(String customerLastName) {
            this.customerLastName = customerLastName;
        }

        public String getBillingStreet() {
            return billingStreet;
        }

        public void setBillingStreet(String billingStreet) {
            this.billingStreet = billingStreet;
        }

        public String getBillingCity() {
            return billingCity;
        }

        public void setBillingCity(String billingCity) {
            this.billingCity = billingCity;
        }
    }
}
