/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import com.google.common.collect.Lists;
import com.google.inject.name.Names;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.book.BookDto;
import org.seedstack.business.fixtures.assembler.book.BookId;
import org.seedstack.business.fixtures.assembler.book.StoredBook;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.business.specification.Specification;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RunWith(SeedITRunner.class)
public class AggToDtoIT {
    public static final String ALEXANDRE_DUMAS = "Alexandre Dumas";
    public static final String THE_THREE_MUSKETEERS = "The Three Musketeers";
    public static final Date PUBLISH_DATE = new Date();
    public static final String THE_COUNT_OF_MONTE_CRISTO = "The Count of Monte Cristo";

    @Inject
    private FluentAssembler fluently;

    @Inject
    private Repository<Order, String> orderRepository;

    @Inject
    private OrderFactory orderFactory;

    @Test
    public void test_Assemble_Aggregate_With_Value_Object_Id_To_Dto() {
        StoredBook book = new StoredBook(new BookId(THE_THREE_MUSKETEERS, ALEXANDRE_DUMAS));
        book.setEditor("unknown");
        book.setPublishDate(PUBLISH_DATE);

        BookDto dto = fluently.assemble(book).with((Names.named("Book"))).to(BookDto.class); // test qualifiers

        Assertions.assertThat(dto.getAuthor()).isEqualTo(ALEXANDRE_DUMAS);
        Assertions.assertThat(dto.getTitle()).isEqualTo(THE_THREE_MUSKETEERS);
        Assertions.assertThat(dto.getPublishDate()).isEqualTo(PUBLISH_DATE);
    }

    @Test(expected = NullPointerException.class)
    public void test_Assemble_Aggregate_To_Dto_null_case() {
        fluently.assemble((AggregateRoot<?>) null).to(BookDto.class);
    }

    @Test
    public void test_Assemble_Aggregates_To_Dtos() {
        StoredBook book = new StoredBook(new BookId(THE_THREE_MUSKETEERS, ALEXANDRE_DUMAS));
        book.setEditor("unknown");
        book.setPublishDate(PUBLISH_DATE);

        StoredBook book2 = new StoredBook(new BookId(THE_COUNT_OF_MONTE_CRISTO, ALEXANDRE_DUMAS));
        book2.setEditor("other editor");
        book2.setPublishDate(PUBLISH_DATE);

        List<BookDto> dtos = fluently.assemble(Lists.newArrayList(book, book2)).with(Names.named("Book")).to(BookDto.class);

        Assertions.assertThat(dtos).isNotNull();
        Assertions.assertThat(dtos).isNotEmpty();
        BookDto dto = dtos.get(0);
        Assertions.assertThat(dto.getAuthor()).isEqualTo(ALEXANDRE_DUMAS);
        Assertions.assertThat(dto.getTitle()).isEqualTo(THE_THREE_MUSKETEERS);
        Assertions.assertThat(dto.getPublishDate()).isEqualTo(PUBLISH_DATE);
        BookDto dto2 = dtos.get(1);
        Assertions.assertThat(dto2.getAuthor()).isEqualTo(ALEXANDRE_DUMAS);
        Assertions.assertThat(dto2.getTitle()).isEqualTo(THE_COUNT_OF_MONTE_CRISTO);
        Assertions.assertThat(dto2.getPublishDate()).isEqualTo(PUBLISH_DATE);
    }

    @Test
    public void test_Assemble_Aggregates_To_Dtos_null_case() {
        List<BookDto> to = fluently.assemble((List<? extends AggregateRoot<?>>) null).to(BookDto.class);
        Assertions.assertThat(to).isNotNull();
        Assertions.assertThat(to).isEmpty();
    }

    @Test
    public void testAssembleStreamFromRepositoryToDto() throws Exception {
        Order order1 = orderFactory.create("1", "death star");
        Order order2 = orderFactory.create("2", "death star");

        orderRepository.add(order1);
        orderRepository.add(order2);

        List<OrderDto> dtos = fluently.assemble(
                orderRepository.get(Specification.any()))
                .to(OrderDto.class)
                .collect(Collectors.toList());

        Assertions.assertThat(dtos).isNotEmpty();
        Assertions.assertThat(dtos.size()).isEqualTo(2);
        Assertions.assertThat(dtos.get(0).getOrderId()).isEqualTo("1");
        Assertions.assertThat(dtos.get(1).getOrderId()).isEqualTo("2");
        Assertions.assertThat(dtos.get(0).getProduct()).isEqualTo("death star");

        orderRepository.remove(order1);
        orderRepository.remove(order2);
    }

    @Test
    public void testAssembleStreamFromRepositoryToDto2() throws Exception {
        Order order1 = orderFactory.create("1", "death star");
        Order order2 = orderFactory.create("2", "death star");

        orderRepository.add(order1);
        orderRepository.add(order2);

        Set<OrderDto> dtos = fluently.assemble(
                orderRepository.get(Specification.any()))
                .to(OrderDto.class)
                .collect(Collectors.toSet());

        Assertions.assertThat(dtos).isNotEmpty();
        Assertions.assertThat(dtos.size()).isEqualTo(2);
        dtos.forEach(dto -> {
            Assertions.assertThat(dto.getOrderId()).matches("(1|2)"); // Matches "1" or "2"
            Assertions.assertThat(dto.getProduct()).isEqualTo("death star");
        });

        orderRepository.remove(order1);
        orderRepository.remove(order2);
    }
}
