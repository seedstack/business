/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.fixtures.book;

import org.seedstack.business.domain.BaseAggregateRoot;

import java.util.Date;


public class StoredBook extends BaseAggregateRoot<BookId> {

    private BookId bookId;

    private Date publishDate;

    private String editor;

    public StoredBook(BookId bookId) {
        this.bookId = bookId;
    }

    @Override
    public BookId getEntityId() {
        return bookId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
