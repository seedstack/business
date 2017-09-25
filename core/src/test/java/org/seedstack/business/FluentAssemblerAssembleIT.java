/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import com.google.inject.name.Names;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
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


@RunWith(SeedITRunner.class)
public class FluentAssemblerAssembleIT {

  private static final String ALEXANDRE_DUMAS = "Alexandre Dumas";
  private static final String THE_THREE_MUSKETEERS = "The Three Musketeers";
  private static final Date PUBLISH_DATE = new Date();
  private static final String THE_COUNT_OF_MONTE_CRISTO = "The Count of Monte Cristo";

  @Inject
  private FluentAssembler fluently;
  @Inject
  private Repository<Order, String> orderRepository;
  @Inject
  private OrderFactory orderFactory;

  @Before
  public void setUp() throws Exception {
    orderRepository.clear();
  }

  @After
  public void tearDown() throws Exception {
    orderRepository.clear();
  }

  @Test
  public void assembleAggregateWithValueObjectIdToDto() {
    StoredBook book = new StoredBook(new BookId(THE_THREE_MUSKETEERS, ALEXANDRE_DUMAS));
    book.setEditor("unknown");
    book.setPublishDate(PUBLISH_DATE);

    BookDto dto = fluently.assemble(book).with((Names.named("Book")))
        .to(BookDto.class); // test qualifiers

    Assertions.assertThat(dto.getAuthor()).isEqualTo(ALEXANDRE_DUMAS);
    Assertions.assertThat(dto.getTitle()).isEqualTo(THE_THREE_MUSKETEERS);
    Assertions.assertThat(dto.getPublishDate()).isEqualTo(PUBLISH_DATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assembleNullAggregate() {
    fluently.assemble((AggregateRoot<?>) null).to(BookDto.class);
  }

  @Test
  public void assembleStreamToDto() {
    StoredBook book = new StoredBook(new BookId(THE_THREE_MUSKETEERS, ALEXANDRE_DUMAS));
    book.setEditor("unknown");
    book.setPublishDate(PUBLISH_DATE);

    StoredBook book2 = new StoredBook(new BookId(THE_COUNT_OF_MONTE_CRISTO, ALEXANDRE_DUMAS));
    book2.setEditor("other editor");
    book2.setPublishDate(PUBLISH_DATE);

    List<BookDto> dtoStream = fluently.assemble(Stream.of(book, book2))
        .with(Names.named("Book"))
        .toStreamOf(BookDto.class)
        .collect(Collectors.toList());

    Assertions.assertThat(dtoStream).isNotNull();
    Assertions.assertThat(dtoStream).isNotEmpty();
    BookDto dto = dtoStream.get(0);
    Assertions.assertThat(dto.getAuthor()).isEqualTo(ALEXANDRE_DUMAS);
    Assertions.assertThat(dto.getTitle()).isEqualTo(THE_THREE_MUSKETEERS);
    Assertions.assertThat(dto.getPublishDate()).isEqualTo(PUBLISH_DATE);
    BookDto dto2 = dtoStream.get(1);
    Assertions.assertThat(dto2.getAuthor()).isEqualTo(ALEXANDRE_DUMAS);
    Assertions.assertThat(dto2.getTitle()).isEqualTo(THE_COUNT_OF_MONTE_CRISTO);
    Assertions.assertThat(dto2.getPublishDate()).isEqualTo(PUBLISH_DATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assembleNullStream() {
    fluently.assemble((Stream<? extends AggregateRoot<Object>>) null).toListOf(BookDto.class);
  }

  @Test
  public void assembleStreamFromRepositoryToDto() throws Exception {
    Order order1 = orderFactory.create("1", "death star");
    Order order2 = orderFactory.create("2", "death star");

    orderRepository.add(order1);
    orderRepository.add(order2);

    List<OrderDto> dtos = fluently.assemble(
        orderRepository.get(Specification.any()))
        .toStreamOf(OrderDto.class)
        .collect(Collectors.toList());

    Assertions.assertThat(dtos).isNotEmpty();
    Assertions.assertThat(dtos.size()).isEqualTo(2);
    Assertions.assertThat(dtos.get(0).getOrderId()).isEqualTo("1");
    Assertions.assertThat(dtos.get(1).getOrderId()).isEqualTo("2");
    Assertions.assertThat(dtos.get(0).getProduct()).isEqualTo("death star");
  }
}
