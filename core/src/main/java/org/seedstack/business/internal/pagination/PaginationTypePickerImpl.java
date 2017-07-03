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

import static com.google.common.base.Preconditions.checkNotNull;

class PaginationTypePickerImpl<A extends AggregateRoot<ID>, ID> implements PaginationTypePicker<A, ID> {
    private final Repository<A, ID> repository;
    protected Repository.Option[] options = new Repository.Option[0];

    PaginationTypePickerImpl(Repository<A, ID> repository) {
        checkNotNull(repository, "Repository cannot be null");
        this.repository = repository;
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