/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import com.google.common.collect.Lists;
import com.google.inject.name.Names;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.assembler.fixtures.book.BookDto;
import org.seedstack.business.assembler.fixtures.book.BookId;
import org.seedstack.business.assembler.fixtures.book.StoredBook;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;


@RunWith(SeedITRunner.class)
public class AggToDtoIT {

    public static final String ALEXANDRE_DUMAS = "Alexandre Dumas";
    public static final String THE_THREE_MUSKETEERS = "The Three Musketeers";
    public static final Date PUBLISH_DATE = new Date();
    public static final String THE_COUNT_OF_MONTE_CRISTO = "The Count of Monte Cristo";

    @Inject
    private FluentAssembler fluently;

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
}
