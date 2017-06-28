/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.builder.ChunkAfterKeyOptionPicker;
import org.seedstack.business.pagination.builder.ChunkLimitOptionPicker;
import org.seedstack.business.pagination.builder.PageSizeOptionPicker;
import org.seedstack.business.pagination.builder.PaginatorPaginationPicker;

class PaginatorPaginationPickerImpl<A extends AggregateRoot<ID>, ID> implements PaginatorPaginationPicker<A, ID> {
    private Repository<A, ID> repository;
    private Repository.Options[] options;

    PaginatorPaginationPickerImpl(Repository<A, ID> repository) {
        this.repository = repository;
    }

    public void setOptions(Repository.Options... options) {
        this.options = options;
    }

    @Override
    public PageSizeOptionPicker<A, ID> page(int page) {
        PaginatorBuilderContext<A, ID> builder = new PaginatorBuilderContext<>(repository, options);
        builder.filterByPage(page);
        return new PageSizeOptionPickerImpl<>(builder);
    }

    @Override
    public ChunkLimitOptionPicker<A, ID> offset(int offset) {
        PaginatorBuilderContext<A, ID> builder = new PaginatorBuilderContext<>(repository, options);
        builder.filterByOffset(offset);
        return new ChunkLimitOptionPickerImpl<>(builder, PaginationMode.OFFSET);
    }

    @Override
    public ChunkAfterKeyOptionPicker<A, ID> key(String key) {
        PaginatorBuilderContext<A, ID> builder = new PaginatorBuilderContext<>(repository, options);
        builder.filterByKey(key);
        return new ChunkAfterKeyOptionPickerImpl<>(builder, PaginationMode.KEY);
    }
}