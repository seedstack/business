/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.it;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.Conditions;
import org.modelmapper.PropertyMap;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.ModelMapper;
import org.seedstack.business.assembler.modelmapper.ModelMapperAssembler;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.fixtures.assembler.MyService;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SeedITRunner.class)
public class ModelMapperAssemblerIT {

    @Inject
    @ModelMapper
    private Assembler<Order, OrderDTO> defaultOrderAssembler;

    @Inject
    @Named("custom")
    private Assembler<Order, OrderDTO> customOrderAssembler;

    @Inject
    private Assembler<Customer, Name> customerAssembler;

    @Test
    public void testInjectee() {
        assertThat(defaultOrderAssembler).isNotNull();
        assertThat(customOrderAssembler).isNotNull();
        assertThat(customerAssembler).isNotNull();
    }

    @Test
    public void testInjectionInConfigureMethods() throws Exception {
        Name nameResult = customerAssembler.createDtoFromAggregate(new Customer(new Name("Hello", "Kitty")));
        assertThat(nameResult.getFirstName()).isEqualTo("Hello");
        assertThat(nameResult.getLastName()).isEqualTo("Kitty");

        customerAssembler.mergeAggregateIntoDto(new Customer(new Name("John", "Doe")), nameResult);
        assertThat(nameResult.getFirstName()).isEqualTo("John");
        assertThat(nameResult.getLastName()).isEqualTo("Doe");

        Customer customerResult = new Customer(new Name("dummy", "dummy"));
        customerAssembler.mergeDtoIntoAggregate(new Name("Jane", "Doe"), customerResult);
        assertThat(customerResult.getName().getFirstName()).isEqualTo("Jane");
        assertThat(customerResult.getName().getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testAssembleDtoFromAggregate() {
        Order order = new Order(new Customer(new Name("John", "Doe")), new Address("main street", "bevillecity"), null, null);

        OrderDTO orderDTO = defaultOrderAssembler.createDtoFromAggregate(order);

        assertThat(orderDTO.customerFirstName).isEqualTo("John");
        assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        assertThat(orderDTO.billingStreet).isEqualTo("main street");
    }

    @Test
    public void testAssembleDtoFromAggregateWithMapAndList() {
        List<String> features = Lists.newArrayList("woow", "such meta");
        Map<String, String> specs = new HashMap<>();
        specs.put("screen", "big but not too much");
        specs.put("price", "cheap");
        Order order = new Order(new Customer(new Name("John", "Doe")), new Address("main street", "bevillecity"), features, specs);

        OrderDTO orderDTO = defaultOrderAssembler.createDtoFromAggregate(order);

        assertThat(orderDTO.customerFirstName).isEqualTo("John");
        assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        assertThat(orderDTO.billingStreet).isEqualTo("main street");

        orderDTO = defaultOrderAssembler.createDtoFromAggregate(order);

        assertThat(orderDTO.customerFirstName).isEqualTo("John");
        assertThat(orderDTO.customerLastName).isEqualTo("Doe");
    }

    @Test
    public void testUpdateDtoFromAggregate() {
        Order order = new Order(new Customer(new Name("John", "Doe")), new Address("main street", "bevillecity"), null, null);
        OrderDTO orderDTO = new OrderDTO("Jane", "Doe", "", "");

        defaultOrderAssembler.mergeAggregateIntoDto(order, orderDTO);

        assertThat(orderDTO.customerFirstName).isEqualTo("John");
        assertThat(orderDTO.customerLastName).isEqualTo("Doe");
        assertThat(orderDTO.billingCity).isEqualTo("bevillecity");
        assertThat(orderDTO.billingStreet).isEqualTo("main street");
    }

    @Test
    public void testMergeAggregateWithDto() {
        Order order = new Order(new Customer(new Name("Jane", "Doe")), new Address(), null, null);
        order.setIgnoredProp("this should not be deleted");
        OrderDTO orderDTO = new OrderDTO("John", "Doe", "main street", "bevillecity");

        customOrderAssembler.mergeDtoIntoAggregate(orderDTO, order);

        assertThat(order.getCustomer().getName().getFirstName()).isEqualTo("John");
        assertThat(order.getCustomer().getName().getLastName()).isEqualTo("Doe");
        assertThat(order.getBillingAddress().getCity()).isEqualTo("bevillecity");
        assertThat(order.getBillingAddress().getStreet()).isEqualTo("main street");
        assertThat(order.getIgnoredProp()).isEqualTo("this should not be deleted");
    }

    @Test
    public void testAvoidNullMapping() {
        Order order = new Order(new Customer(new Name("Jane", "Doe")), new Address(), null, null);
        order.setIgnoredProp("this should not be deleted");
        OrderDTO orderDTO = new OrderDTO(null, null, "main street", "bevillecity");

        customOrderAssembler.mergeDtoIntoAggregate(orderDTO, order);

        assertThat(order.getCustomer().getName().getFirstName()).isEqualTo("Jane");
        assertThat(order.getCustomer().getName().getLastName()).isEqualTo("Doe");
        assertThat(order.getBillingAddress().getCity()).isEqualTo("bevillecity");
        assertThat(order.getBillingAddress().getStreet()).isEqualTo("main street");
        assertThat(order.getIgnoredProp()).isEqualTo("this should not be deleted");
    }

    static class CustomerAssembler extends ModelMapperAssembler<Customer, Name> {
        @Inject
        MyService myService;
        @Logging
        Logger logger;

        @Override
        protected void configureAssembly(org.modelmapper.ModelMapper modelMapper) {
            assertThat(myService).isNotNull();
            assertThat(logger).isNotNull();
            modelMapper.addMappings(new PropertyMap<Customer, Name>() {
                protected void configure() {
                    map().setFirstName(source.getName().getFirstName());
                    map().setLastName(source.getName().getLastName());
                }
            });
        }

        @Override
        protected void configureMerge(org.modelmapper.ModelMapper modelMapper) {
            assertThat(myService).isNotNull();
            assertThat(logger).isNotNull();
            modelMapper.addMappings(new PropertyMap<Name, Customer>() {
                protected void configure() {
                    map().getName().setFirstName(source.getFirstName());
                    map().getName().setLastName(source.getLastName());
                }
            });
        }
    }

    static class Order extends BaseAggregateRoot<String> {
        String id;
        Customer customer;
        List<String> features;
        Map<String, String> specs;
        String ignoredProp;
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

    static class Customer extends BaseAggregateRoot<Name> {
        private Name name;

        public Customer(Name name) {
            this.name = name;
        }

        @Override
        public Name getId() {
            return name;
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

    @DtoOf(Order.class)
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

    @Named("custom")
    static class CustomOrderAssembler extends ModelMapperAssembler<Order, OrderDTO> {
        @Inject
        private MyService myService;
        @Logging
        private Logger logger;

        @Override
        protected void configureAssembly(org.modelmapper.ModelMapper modelMapper) {
            assertThat(myService).isNotNull();
            assertThat(logger).isNotNull();
            modelMapper.createTypeMap(Order.class, OrderDTO.class)
                    .addMappings(mapper -> {
                        mapper.map(src -> src.getBillingAddress().getStreet(), OrderDTO::setBillingStreet);
                        mapper.map(src -> src.getBillingAddress().getCity(), OrderDTO::setBillingCity);
                    });
        }

        @Override
        protected void configureMerge(org.modelmapper.ModelMapper modelMapper) {
            assertThat(myService).isNotNull();
            assertThat(logger).isNotNull();
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.createTypeMap(OrderDTO.class, Order.class)
                    .addMappings(mapper -> {
                        mapper.<String>map(OrderDTO::getBillingCity, (dest, v) -> dest.getBillingAddress().setCity(v));
                        mapper.<String>map(OrderDTO::getBillingStreet, (dest, v) -> dest.getBillingAddress().setStreet(v));
                    });
        }
    }
}
