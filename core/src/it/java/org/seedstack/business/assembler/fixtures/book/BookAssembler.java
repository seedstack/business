/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.fixtures.book;

import org.seedstack.business.api.interfaces.assembler.BaseAssembler;

import javax.inject.Named;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Named("Book") // just to test the DSL with qualifier
public class BookAssembler extends BaseAssembler<StoredBook, BookDto> {

    @Override
    protected void doAssembleDtoFromAggregate(BookDto targetDto, StoredBook sourceAggregate) {
        targetDto.setTitle(sourceAggregate.getEntityId().getTitle());
        targetDto.setAuthor(sourceAggregate.getEntityId().getAuthor());
        targetDto.setPublishDate(sourceAggregate.getPublishDate());
        targetDto.setEditor(sourceAggregate.getEditor());
    }

    @Override
    protected void doMergeAggregateWithDto(StoredBook targetAggregate, BookDto sourceDto) {
        targetAggregate.setPublishDate(sourceDto.getPublishDate());
        targetAggregate.setEditor(sourceDto.getEditor());
        // identity never change
    }
}
