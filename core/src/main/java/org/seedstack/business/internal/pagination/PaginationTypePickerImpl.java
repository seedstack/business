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
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.PaginationTypePicker;
import org.seedstack.business.pagination.dsl.SizePicker;

class PaginationTypePickerImpl<A extends AggregateRoot<ID>, ID> implements PaginationTypePicker<A, ID> {
    private Repository<A, ID> repository;
    private Repository.Options[] options;

    PaginationTypePickerImpl(Repository<A, ID> repository) {
        this.repository = repository;
    }

    public void setOptions(Repository.Options... options) {
        this.options = options;
    }

    @Override
    public SizePicker<A, ID> byPage(long pageIndex) {
        PaginatorContext<A, ID> builder = new PaginatorContext<>(repository, options);
        builder.setPageIndex(pageIndex);
        return new SizePickerImpl<>(builder);
    }

    @Override
    public LimitPicker<Slice<A>, A, ID> byOffset(long startingOffset) {
        PaginatorContext<A, ID> builder = new PaginatorContext<>(repository, options);
        builder.setOffset(startingOffset);
        return new LimitPickerImpl<>(builder, PaginationMode.OFFSET);
    }

    @Override
    public KeyValuePicker<A, ID> byAttribute(String attributeName) {
        PaginatorContext<A, ID> builder = new PaginatorContext<>(repository, options);
        builder.setAttribute(attributeName);
        return new AfterKeyPickerImpl<>(builder, PaginationMode.ATTRIBUTE);
    }
}