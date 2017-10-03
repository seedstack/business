/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.name.Names;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.Paginator;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;
import org.seedstack.seed.it.SeedITRunner;

@RunWith(SeedITRunner.class)
public class PaginationIT {

    @Inject
    private Paginator paginator;

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
                .property("product")
                .equalTo("death star")
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
        Page<OrderDto> dtoPage = fluentAssembler.assemble(paginator.paginate(orderRepository)
                .byPage(2)
                .ofSize(2)
                .all())
                .with(Names.named("Order"))
                .toPageOf(OrderDto.class);
        assertThat(dtoPage.getCapacity()).isEqualTo(2);
        assertThat(dtoPage.getItems()
                .get(0)
                .getClass()
                .isInstance(OrderDto.class));
        assertThat(dtoPage.getIndex()).isEqualTo(2);
        assertThat(dtoPage.getCapacity()).isEqualTo(2);
        assertThat(dtoPage.getItems()
                .size()).isEqualTo(2);
        assertThat(dtoPage.getItems()
                .get(0)
                .getOrderId()).isEqualTo("3");
        assertThat(dtoPage.getItems()
                .get(1)
                .getOrderId()).isEqualTo("4");
    }

    @Test
    public void testSliceAssembler() throws Exception {
        Slice<OrderDto> dtoSlice = fluentAssembler.assemble(paginator.paginate(orderRepository)
                .byOffset(2)
                .all())
                .with(Names.named("Order"))
                .toSliceOf(OrderDto.class);
        assertThat(dtoSlice.getSize()).isEqualTo(5);
        assertThat(dtoSlice.getItems()
                .get(0)
                .getClass()
                .isInstance(OrderDto.class));
        //assertThat(dtoSlice.getOffset()).isEqualTo(2);
        //assertThat(dtoSlice.getTotalSize()).isEqualTo(7);
        assertThat(dtoSlice.getItems()
                .size()).isEqualTo(5);
        assertThat(dtoSlice.getItems()
                .get(0)
                .getOrderId()).isEqualTo("3");
        assertThat(dtoSlice.getItems()
                .get(1)
                .getOrderId()).isEqualTo("4");
        assertThat(dtoSlice.getItems()
                .get(2)
                .getOrderId()).isEqualTo("5");
        assertThat(dtoSlice.getItems()
                .get(3)
                .getOrderId()).isEqualTo("6");
        assertThat(dtoSlice.getItems()
                .get(4)
                .getOrderId()).isEqualTo("7");
    }

    @Test
    public void testPageWithoutSpec() throws Exception {
        Page<Order> page = paginator.paginate(orderRepository)
                .byPage(2)
                .ofSize(2)
                .all();
        assertThat(page.getIndex()).isEqualTo(2);
        assertThat(page.getSize()).isEqualTo(2);
        assertThat(page.getCapacity()).isEqualTo(2);
        assertThat(page.getItems()
                .size()).isEqualTo(2);
        assertThat(page.getItems()
                .get(0)
                .getId()).isEqualTo("3");
        assertThat(page.getItems()
                .get(1)
                .getId()).isEqualTo("4");
    }

    @Test
    public void testOffsetWithSpec() throws Exception {
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byOffset(2)
                .matching(specFilterProduct);
        assertThat(slice.getSize()).isEqualTo(4);
        //assertThat(slice.getOffset()).isEqualTo(2);
        //assertThat(slice.getTotalSize()).isEqualTo(6);
        assertThat(slice.getItems()
                .size()).isEqualTo(4);
        assertThat(slice.getItems()
                .get(0)
                .getId()).isEqualTo("3");
        assertThat(slice.getItems()
                .get(1)
                .getId()).isEqualTo("4");
        assertThat(slice.getItems()
                .get(2)
                .getId()).isEqualTo("5");
        assertThat(slice.getItems()
                .get(3)
                .getId()).isEqualTo("6");
    }

    @Test
    public void testOffsetWithoutSpec() throws Exception {
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byOffset(2)
                .limit(2)
                .all();
        assertThat(slice.getSize()).isEqualTo(2);
        //assertThat(slice.getOffset()).isEqualTo(2);
        //assertThat(slice.getTotalSize()).isEqualTo(7);
        assertThat(slice.getItems()
                .size()).isEqualTo(2);
        assertThat(slice.getItems()
                .get(0)
                .getId()).isEqualTo("3");
        assertThat(slice.getItems()
                .get(1)
                .getId()).isEqualTo("4");
    }

    @Test
    public void testAfterDateWithoutSpec() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byAttribute("orderDate")
                .after(date)
                .all();
        assertThat(slice.getSize()).isEqualTo(5);
        //assertThat(slice.getTotalSize()).isEqualTo(5);
        assertThat(slice.getItems()
                .size()).isEqualTo(5);
        assertThat(slice.getItems()
                .get(0)
                .getId()).isEqualTo("3");
        assertThat(slice.getItems()
                .get(1)
                .getId()).isEqualTo("4");
        assertThat(slice.getItems()
                .get(2)
                .getId()).isEqualTo("5");
        assertThat(slice.getItems()
                .get(3)
                .getId()).isEqualTo("6");
        assertThat(slice.getItems()
                .get(4)
                .getId()).isEqualTo("7");
    }

    @Test
    public void testAfterDateWithSpec() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byAttribute("orderDate")
                .after(date)
                .limit(2)
                .matching(specFilterProduct);
        assertThat(slice.getSize()).isEqualTo(2);
        //assertThat(slice.getTotalSize()).isEqualTo(4);
        assertThat(slice.getItems()
                .size()).isEqualTo(2);
        assertThat(slice.getItems()
                .get(0)
                .getId()).isEqualTo("3");
        assertThat(slice.getItems()
                .get(1)
                .getId()).isEqualTo("4");
    }

    @Test
    public void testAfterCastNotMatch() {
        try {
            paginator.paginate(orderRepository)
                    .byAttribute("orderDate")
                    .after("3")
                    .all();
        } catch (Exception e) {
            Assertions.assertThat(e)
                    .hasMessageContaining("java.lang.String cannot be cast to java.util.Date");
        }
    }

    @Test
    public void testLastPageNotFull() {
        Page<Order> page = paginator.paginate(orderRepository)
                .byPage(3)
                .ofSize(3)
                .all();
        assertThat(page.getIndex()).isEqualTo(3);
        assertThat(page.getSize()).isEqualTo(1);
        assertThat(page.getCapacity()).isEqualTo(3);
        assertThat(page.getItems()
                .size()).isEqualTo(1);
        assertThat(page.getItems()
                .get(0)
                .getId()).isEqualTo("7");
    }
}