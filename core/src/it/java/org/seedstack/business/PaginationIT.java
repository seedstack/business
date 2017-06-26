/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import com.google.inject.name.Names;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.business.pagination.Chunk;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.builder.PaginatorBuilder;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.builder.SpecificationBuilder;
import org.seedstack.seed.it.SeedITRunner;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SeedITRunner.class)
public class PaginationIT {
    @Inject
    private PaginatorBuilder paginator;

    @Inject
    private FluentAssembler fluentAssembler;

    @Inject
    private Repository<Order, String> orderRepository;

    @Inject
    private OrderFactory orderFactory;

    @Inject
    private SpecificationBuilder specificationBuilder;

    private Specification<Order> specFilterProduct;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public PaginationIT() {
    }

    @Before
    public void setUp() throws Exception {
        specFilterProduct = specificationBuilder.of(Order.class)
                .property("product").equalTo("death star")
                .build();

        Date orderDateNew = formatter.parse("01-06-2017");
        Date orderDateOld = formatter.parse("01-06-2016");

        Order order1 = orderFactory.create("1", "death star", orderDateOld);
        Order order2 = orderFactory.create("2", "death star", orderDateOld);
        Order order3 = orderFactory.create("3", "death star", orderDateNew);
        Order order4 = orderFactory.create("4", "death star", orderDateNew);
        Order order5 = orderFactory.create("5", "death star", orderDateNew);
        Order order6 = orderFactory.create("6", "death star", orderDateNew);
        Order order7 = orderFactory.create("7", "C3PO", orderDateNew);

        orderRepository.add(order1);
        orderRepository.add(order2);
        orderRepository.add(order3);
        orderRepository.add(order4);
        orderRepository.add(order5);
        orderRepository.add(order6);
        orderRepository.add(order7);
    }

    @Test
    public void testPageAssembler() throws Exception {
        Page<OrderDto> dtoPage = fluentAssembler.assemble(paginator.repository(orderRepository)
                .options()
                .page(2)
                .size(2)
                .paginate())
                .with(Names.named("Order"))
                .to(OrderDto.class);
        assertThat(dtoPage.getCapacity()).isEqualTo(2);
        assertThat(dtoPage.getView().get(0).getClass().isInstance(OrderDto.class));
        assertThat(dtoPage.getIndex()).isEqualTo(2);
        assertThat(dtoPage.getCapacity()).isEqualTo(2);
        assertThat(dtoPage.getView().size()).isEqualTo(2);
        assertThat(dtoPage.getView().get(0).getOrderId()).isEqualTo("5");
        assertThat(dtoPage.getView().get(1).getOrderId()).isEqualTo("6");
    }

    @Test
    public void testChunkAssembler() throws Exception {
        Chunk<OrderDto> dtoChunk = fluentAssembler.assemble(paginator.repository(orderRepository)
                .offset(2)
                .paginate())
                .with(Names.named("Order"))
                .to(OrderDto.class);
        assertThat(dtoChunk.getResultSize()).isEqualTo(5);
        assertThat(dtoChunk.getView().get(0).getClass().isInstance(OrderDto.class));
        assertThat(dtoChunk.getChunkOffset()).isEqualTo(2);
        assertThat(dtoChunk.getResultViewSize()).isEqualTo(7);
        assertThat(dtoChunk.getView().size()).isEqualTo(5);
        assertThat(dtoChunk.getView().get(0).getOrderId()).isEqualTo("3");
        assertThat(dtoChunk.getView().get(1).getOrderId()).isEqualTo("4");
        assertThat(dtoChunk.getView().get(2).getOrderId()).isEqualTo("5");
        assertThat(dtoChunk.getView().get(3).getOrderId()).isEqualTo("6");
        assertThat(dtoChunk.getView().get(4).getOrderId()).isEqualTo("7");
    }

    @Test
    public void testPageWithoutSpec() throws Exception {
        Page<Order> page = paginator.repository(orderRepository).page(2).size(2).paginate();
        assertThat(page.getIndex()).isEqualTo(2);
        assertThat(page.getResultSize()).isEqualTo(2);
        assertThat(page.getCapacity()).isEqualTo(2);
        assertThat(page.getView().size()).isEqualTo(2);
        assertThat(page.getView().get(0).getId()).isEqualTo("5");
        assertThat(page.getView().get(1).getId()).isEqualTo("6");
    }

    @Test
    public void testOffsetWithSpec() throws Exception {
        Chunk<Order> chunk = paginator.repository(orderRepository).offset(2).paginate(specFilterProduct);
        assertThat(chunk.getResultSize()).isEqualTo(4);
        assertThat(chunk.getChunkOffset()).isEqualTo(2);
        assertThat(chunk.getResultViewSize()).isEqualTo(6);
        assertThat(chunk.getView().size()).isEqualTo(4);
        assertThat(chunk.getView().get(0).getId()).isEqualTo("3");
        assertThat(chunk.getView().get(1).getId()).isEqualTo("4");
        assertThat(chunk.getView().get(2).getId()).isEqualTo("5");
        assertThat(chunk.getView().get(3).getId()).isEqualTo("6");
    }

    @Test
    public void testOffsetWithoutSpec() throws Exception {
        Chunk<Order> chunk = paginator.repository(orderRepository).offset(2).limit(2).paginate();
        assertThat(chunk.getResultSize()).isEqualTo(2);
        assertThat(chunk.getChunkOffset()).isEqualTo(2);
        assertThat(chunk.getResultViewSize()).isEqualTo(7);
        assertThat(chunk.getView().size()).isEqualTo(2);
        assertThat(chunk.getView().get(0).getId()).isEqualTo("3");
        assertThat(chunk.getView().get(1).getId()).isEqualTo("4");
    }

    @Test
    public void testAfterDateWithoutSpec() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Chunk<Order> chunk = paginator.repository(orderRepository).key("orderDate").after(date).paginate();
        assertThat(chunk.getResultSize()).isEqualTo(5);
        assertThat(chunk.getResultViewSize()).isEqualTo(5);
        assertThat(chunk.getView().size()).isEqualTo(5);
        assertThat(chunk.getView().get(0).getId()).isEqualTo("3");
        assertThat(chunk.getView().get(1).getId()).isEqualTo("4");
        assertThat(chunk.getView().get(2).getId()).isEqualTo("5");
        assertThat(chunk.getView().get(3).getId()).isEqualTo("6");
        assertThat(chunk.getView().get(4).getId()).isEqualTo("7");
    }

    @Test
    public void testAfterDateWithSpec() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Chunk<Order> chunk = paginator.repository(orderRepository).key("orderDate").after(date).limit(2).paginate(specFilterProduct);
        assertThat(chunk.getResultSize()).isEqualTo(2);
        assertThat(chunk.getResultViewSize()).isEqualTo(4);
        assertThat(chunk.getView().size()).isEqualTo(2);
        assertThat(chunk.getView().get(0).getId()).isEqualTo("3");
        assertThat(chunk.getView().get(1).getId()).isEqualTo("4");
    }

    @Test
    public void testAfterCastNotMatch() {
        try {
            paginator.repository(orderRepository).key("orderDate").after("3").paginate();
        } catch (Exception e) {
            Assertions.assertThat(e).hasMessageContaining("java.lang.String cannot be cast to java.util.Date");
        }
    }

    @Test
    public void testLastPageNotFull() {
        Page<Order> page = paginator.repository(orderRepository).page(2).size(3).paginate();
        assertThat(page.getIndex()).isEqualTo(2);
        assertThat(page.getResultSize()).isEqualTo(1);
        assertThat(page.getCapacity()).isEqualTo(3);
        assertThat(page.getView().size()).isEqualTo(1);
        assertThat(page.getView().get(0).getId()).isEqualTo("7");
    }
}