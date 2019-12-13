/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.name.Names;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.domain.SortOption;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.Paginator;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class PaginationIT {
    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;
    private Order order5;
    private Order order6;
    private Order order7;
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

    @Before
    public void setUp() throws Exception {
        specFilterProduct = specificationBuilder.of(Order.class)
                .property("product")
                .equalTo("death star")
                .build();

        Date orderDateNew = formatter.parse("01-06-2017");
        Date orderDateOld = formatter.parse("01-06-2016");

        order1 = orderFactory.create("1", "death star", orderDateOld);
        order2 = orderFactory.create("2", "death star", orderDateOld);
        order3 = orderFactory.create("3", "death star", orderDateNew);
        order4 = orderFactory.create("4", "death star", orderDateNew);
        order5 = orderFactory.create("5", "death star", orderDateNew);
        order6 = orderFactory.create("6", "death star", orderDateNew);
        order7 = orderFactory.create("7", "C3PO", orderDateNew);

        orderRepository.clear();
        orderRepository.add(order1);
        orderRepository.add(order2);
        orderRepository.add(order3);
        orderRepository.add(order4);
        orderRepository.add(order5);
        orderRepository.add(order6);
        orderRepository.add(order7);
    }

    @Test
    public void testPageAssembler() {
        Page<OrderDto> dtoPage = fluentAssembler.assemble(paginator.paginate(orderRepository)
                .byPage(2)
                .ofSize(2)
                .all())
                .with(Names.named("Order"))
                .toPageOf(OrderDto.class);
        assertThat(dtoPage.getMaxSize()).isEqualTo(2);
        assertThat(dtoPage.getItems().get(0).getClass().isInstance(OrderDto.class));
        assertThat(dtoPage.getIndex()).isEqualTo(2);
        assertThat(dtoPage.getMaxSize()).isEqualTo(2);
        assertThat(dtoPage.getItems().size()).isEqualTo(2);
        assertThat(dtoPage.getItems().get(0).getOrderId()).isEqualTo("3");
        assertThat(dtoPage.getItems().get(1).getOrderId()).isEqualTo("4");
    }

    @Test
    public void testSliceAssembler() {
        Slice<OrderDto> dtoSlice = fluentAssembler.assemble(paginator.paginate(orderRepository)
                .byOffset(2)
                .all())
                .with(Names.named("Order"))
                .toSliceOf(OrderDto.class);
        assertThat(dtoSlice.getSize()).isEqualTo(5);
        assertThat(dtoSlice.getItems().get(0).getClass().isInstance(OrderDto.class));
        assertThat(dtoSlice.getItems().size()).isEqualTo(5);
        assertThat(dtoSlice.getItems().get(0).getOrderId()).isEqualTo("3");
        assertThat(dtoSlice.getItems().get(1).getOrderId()).isEqualTo("4");
        assertThat(dtoSlice.getItems().get(2).getOrderId()).isEqualTo("5");
        assertThat(dtoSlice.getItems().get(3).getOrderId()).isEqualTo("6");
        assertThat(dtoSlice.getItems().get(4).getOrderId()).isEqualTo("7");
    }

    @Test
    public void testPageWithoutSpec() {
        Page<Order> page = paginator.paginate(orderRepository)
                .byPage(2)
                .ofSize(2)
                .all();
        assertThat(page.getIndex()).isEqualTo(2);
        assertThat(page.getSize()).isEqualTo(2);
        assertThat(page.getMaxSize()).isEqualTo(2);
        assertThat(page.getItems()).containsExactly(order3, order4);
    }

    @Test
    public void testOffsetWithSpec() {
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byOffset(2)
                .matching(specFilterProduct);
        assertThat(slice.getSize()).isEqualTo(4);
        assertThat(slice.getItems()).containsExactly(order3, order4, order5, order6);
    }

    @Test
    public void testOffsetWithoutSpec() {
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byOffset(2)
                .limit(2)
                .all();
        assertThat(slice.getSize()).isEqualTo(2);
        assertThat(slice.getItems()).containsExactly(order3, order4);
    }

    @Test
    public void testAfterDateWithoutSpec() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Slice<Order> slice = paginator.paginate(orderRepository)
                .byAttribute("orderDate")
                .after(date)
                .all();
        assertThat(slice.getSize()).isEqualTo(5);
        assertThat(slice.getItems().size()).isEqualTo(5);
        assertThat(slice.getItems()).containsExactly(order3, order4, order5, order6, order7);
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
        assertThat(slice.getItems().size()).isEqualTo(2);
        assertThat(slice.getItems()).containsExactly(order3, order4);
    }

    @Test
    public void testAfterCastNotMatch() {
        try {
            paginator.paginate(orderRepository)
                    .byAttribute("orderDate")
                    .after("3")
                    .all();
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("String cannot be cast to class java.util.Date");
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
        assertThat(page.getMaxSize()).isEqualTo(3);
        assertThat(page.getItems().size()).isEqualTo(1);
        assertThat(page.getItems()).containsExactly(order7);
    }

    @Test
    public void testStreamOffset() {
        Slice<Order> slice = paginator.paginate(Stream.of(order1, order2, order3, order4, order5, order6, order7))
                .byOffset(3)
                .limit(3)
                .all();
        assertThat(slice.getItems()).containsExactly(order4, order5, order6);
        assertThat(slice.getSize()).isEqualTo(3);
    }

    @Test
    public void testStreamAttribute() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Slice<Order> slice = paginator.paginate(Stream.of(order1, order2, order3, order4, order5, order6, order7))
                .byAttribute("orderDate")
                .after(date)
                .limit(2)
                .matching(specFilterProduct);

        assertThat(slice.getSize()).isEqualTo(2);
        assertThat(slice.getItems()).containsExactly(order3, order4);
    }

    @Test
    public void testListOffset() {
        Slice<Order> slice = paginator.paginate(newArrayList(order1, order2, order3, order4, order5, order6, order7))
                .byOffset(3)
                .limit(3)
                .all();
        assertThat(slice.getItems()).containsExactly(order4, order5, order6);
        assertThat(slice.getSize()).isEqualTo(3);
    }

    @Test
    public void testListAttribute() throws Exception {
        Date date = formatter.parse("01-01-2017");
        Slice<Order> slice = paginator.paginate(newArrayList(order1, order2, order3, order4, order5, order6, order7))
                .byAttribute("orderDate")
                .after(date)
                .limit(2)
                .matching(specFilterProduct);

        assertThat(slice.getSize()).isEqualTo(2);
        assertThat(slice.getItems()).containsExactly(order3, order4);
    }

    @Test
    @Ignore("not working, a refactor may be needed to allow sub-sorting")
    public void testOrderIsAppliedToSubSpecification() {
        Slice<Order> slice = paginator.paginate(orderRepository)
                .withOptions(new SortOption().add("id", SortOption.Direction.DESCENDING))
                .byOffset(2)
                .matching(specFilterProduct);
        assertThat(slice.getSize()).isEqualTo(4);
        assertThat(slice.getItems()).containsExactly(order6, order5, order4, order3);
    }
}
