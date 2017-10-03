/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkNotNull;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.PaginationTypePicker;
import org.seedstack.business.pagination.dsl.SizePicker;

class PaginationTypePickerImpl<A extends AggregateRoot<I>, I> implements PaginationTypePicker<A, I> {

    private final Repository<A, I> repository;
    protected Repository.Option[] options = new Repository.Option[0];

    PaginationTypePickerImpl(Repository<A, I> repository) {
        checkNotNull(repository, "Repository cannot be null");
        this.repository = repository;
    }

    @Override
    public SizePicker<A, I> byPage(long pageIndex) {
        PaginatorContext<A, I> builder = new PaginatorContext<>(repository, options);
        builder.setPageIndex(pageIndex);
        return new SizePickerImpl<>(builder);
    }

    @Override
    public LimitPicker<Slice<A>, A, I> byOffset(long startingOffset) {
        PaginatorContext<A, I> builder = new PaginatorContext<>(repository, options);
        builder.setOffset(startingOffset);
        return new LimitPickerImpl<>(builder, PaginationMode.OFFSET);
    }

    @Override
    public KeyValuePicker<A, I> byAttribute(String attributeName) {
        PaginatorContext<A, I> builder = new PaginatorContext<>(repository, options);
        builder.setAttribute(attributeName);
        return new AfterKeyPickerImpl<>(builder, PaginationMode.ATTRIBUTE);
    }
}