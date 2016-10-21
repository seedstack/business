/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto;

import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.Tuples;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.ModelMapper;
import org.seedstack.business.assembler.auto.fixture.*;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;


@RunWith(SeedITRunner.class)
public class ModelMapperTupleAssemblerIT {

    @Inject
    @ModelMapper
    private Assembler<Pair<Order, Customer>, Recipe> defaultTupleAssembler;

    @Test
    public void testAssembleDtoFromAggregate() {
        Customer customer = new Customer(new Name("John", "Doe"));
        Order order = new Order(new Address("main street", "bevillecity"));

        Pair<Order, Customer> tuple = Tuples.create(order, customer);
        Recipe recipe = defaultTupleAssembler.assembleDtoFromAggregate(tuple);

        Assertions.assertThat(recipe.getCustomerFirstName()).isEqualTo("John");
        Assertions.assertThat(recipe.getCustomerLastName()).isEqualTo("Doe");
        Assertions.assertThat(recipe.getBillingCity()).isEqualTo("bevillecity");
        Assertions.assertThat(recipe.getBillingStreet()).isEqualTo("main street");
    }

    @Test
    public void testUpdateDtoFromAggregate() {
        Customer customer = new Customer(new Name("John", "Doe"));
        Order order = new Order( new Address("main street", "bevillecity"));
        Recipe recipe = new Recipe("Jane", "Doe", "", "");


        Pair<Order, Customer> tuple = Tuples.create(order, customer);
        defaultTupleAssembler.assembleDtoFromAggregate(recipe, tuple);

        Assertions.assertThat(recipe.getCustomerFirstName()).isEqualTo("John");
        Assertions.assertThat(recipe.getCustomerLastName()).isEqualTo("Doe");
        Assertions.assertThat(recipe.getBillingCity()).isEqualTo("bevillecity");
        Assertions.assertThat(recipe.getBillingStreet()).isEqualTo("main street");
    }

    @Test
    @Ignore
    public void testMergeAggregateWithDto() {
        Customer customer = new Customer(new Name("John", "Doe"));
        Order order = new Order(null);
        Recipe recipe = new Recipe("John", "Doe", "main street", "bevillecity");

        Pair<Order, Customer> tuple = Tuples.create(order, customer);
        defaultTupleAssembler.mergeAggregateWithDto(tuple, recipe);

        Assertions.assertThat(recipe.getCustomerFirstName()).isEqualTo("John");
        Assertions.assertThat(recipe.getCustomerLastName()).isEqualTo("Doe");
        Assertions.assertThat(recipe.getBillingCity()).isEqualTo("bevillecity");
        Assertions.assertThat(recipe.getBillingStreet()).isEqualTo("main street");
    }

}
