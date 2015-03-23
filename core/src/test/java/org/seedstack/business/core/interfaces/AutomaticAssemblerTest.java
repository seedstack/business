/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.seedstack.business.api.domain.base.BaseAggregateRoot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 *         24/02/2015
 */
public class AutomaticAssemblerTest {

    private AutomaticAssembler<Order, OrderDTO> automaticAssembler;
    private DefaultAssembler<Order, OrderDTO> defaultAssembler;

    static class AutoAssembler extends AutomaticAssembler<Order, OrderDTO> {
        @Override
        protected ModelMapper configureAssembly() {
            return new ModelMapper();
        }

        @Override
        protected ModelMapper configureMerge() {
            ModelMapper modelMapper = new ModelMapper();
            PropertyMap<OrderDTO, Order> orderMap = new PropertyMap<OrderDTO, Order>() {
                protected void configure() {
                    map().getBillingAddress().setStreet(source.billingStreet);
                    map(source.billingCity, destination.billingAddress.getCity());
                }
            };
            modelMapper.addMappings(orderMap);
            return modelMapper;
        }
    }

    @Before
    public void before() {
        automaticAssembler = new AutoAssembler();
        defaultAssembler = new DefaultAssembler<Order, OrderDTO>(new Class[]{Order.class, OrderDTO.class});
    }

    @Test
    public void testAssembleDtoFromAggregate() {
        Order order = new Order(new Customer(new Name("John", "Doe")), new Address("main street", "bevillecity"), null, null);

        OrderDTO orderDTO = automaticAssembler.assembleDtoFromAggregate(order);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        Assertions.assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        Assertions.assertThat(orderDTO.billingStreet).isEqualTo("main street");

        orderDTO = defaultAssembler.assembleDtoFromAggregate(order);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
    }

    @Test
    public void testAssembleDtoFromAggregateWithMapAndList() {
        List<String> features = Lists.newArrayList("woow", "such meta");
        Map<String, String> specs = new HashMap<String, String>();
        specs.put("screen", "big but not too much");
        specs.put("price", "cheap");
        Order order = new Order(new Customer(new Name("John", "Doe")), new Address("main street", "bevillecity"), features, specs);

        OrderDTO orderDTO = automaticAssembler.assembleDtoFromAggregate(order);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        Assertions.assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        Assertions.assertThat(orderDTO.billingStreet).isEqualTo("main street");

        orderDTO = defaultAssembler.assembleDtoFromAggregate(order);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
    }

    @Test
    public void testUpdateDtoFromAggregate() {
        Order order = new Order(new Customer(new Name("John", "Doe")), new Address("main street", "bevillecity"), null, null);
        OrderDTO orderDTO = new OrderDTO("Jane", "Doe", "", "");

        automaticAssembler.updateDtoFromAggregate(orderDTO, order);

        Assertions.assertThat(orderDTO.customerFirstName).isEqualTo("John");
        Assertions.assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        Assertions.assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        Assertions.assertThat(orderDTO.billingStreet).isEqualTo("main street");
    }

    @Test
    public void testMergeAggregateWithDto() {
        Order order = new Order(new Customer(new Name("Jane", "Doe")), new Address(), null, null);
        order.setIgnoredProp("this should not be deleted");
        OrderDTO orderDTO = new OrderDTO("John", "Doe", "main street", "bevillecity");

        // This custom assembler test a custom mapping for the merge
        // this mapping is necessary because the name are not matching billing != billingAddress

        automaticAssembler.mergeAggregateWithDto(order, orderDTO);

        Assertions.assertThat(order.getCustomer().getName().getFirstName()).isEqualTo("John");
        Assertions.assertThat(order.getCustomer().getName().getLastName()).isEqualTo("Doe");
        Assertions.assertThat(order.getBillingAddress().getCity()).isEqualTo("bevillecity");
        Assertions.assertThat(order.getBillingAddress().getStreet()).isEqualTo("main street");
        Assertions.assertThat(order.getIgnoredProp()).isEqualTo("this should not be deleted");
    }

    static class Order extends BaseAggregateRoot<String> {
        String id;
        Customer customer;
        List<String> features;
        Map<String, String> specs;
        String ignoredProp;

        @Override
        public String getEntityId() {
            return id;
        }

        Address billingAddress;

        public Order() {
        }

        public Order(Customer customer, Address billingAddress, List<String> features, Map<String, String> specs) {
            this.customer = customer;
            this.billingAddress = billingAddress;
            this.features = features;
            this.specs = specs;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Address getBillingAddress() {
            return billingAddress;
        }

        public void setBillingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
        }

        public List<String> getFeatures() {
            return features;
        }

        public void setFeatures(List<String> features) {
            this.features = features;
        }

        public Map<String, String> getSpecs() {
            return specs;
        }

        public void setSpecs(Map<String, String> specs) {
            this.specs = specs;
        }

        public String getIgnoredProp() {
            return ignoredProp;
        }

        public void setIgnoredProp(String ignoredProp) {
            this.ignoredProp = ignoredProp;
        }
    }

    static class Customer {
        Name name;

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

    static class OrderDTO {
        String customerFirstName;
        String customerLastName;
        String billingStreet;
        String billingCity;
        List<String> features;
        Map<String, String> specs;

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

        public List<String> getFeatures() {
            return features;
        }

        public void setFeatures(List<String> features) {
            this.features = features;
        }

        public Map<String, String> getSpecs() {
            return specs;
        }

        public void setSpecs(Map<String, String> specs) {
            this.specs = specs;
        }
    }
}
