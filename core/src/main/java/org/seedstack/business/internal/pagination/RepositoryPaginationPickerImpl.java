/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkNotNull;

import org.seedstack.business.BusinessConfig;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.PaginationTypePicker;
import org.seedstack.business.pagination.dsl.SizePicker;

class RepositoryPaginationPickerImpl<A extends AggregateRoot<I>, I> implements PaginationTypePicker<A> {
    private final BusinessConfig.PaginationConfig paginationConfig;
    private final Repository<A, I> repository;
    protected Repository.Option[] options = new Repository.Option[0];

    RepositoryPaginationPickerImpl(BusinessConfig.PaginationConfig paginationConfig, Repository<A, I> repository) {
        this.paginationConfig = paginationConfig;
        this.repository = checkNotNull(repository, "Repository cannot be null");
    }

    @Override
    public SizePicker<A> byPage(long pageIndex) {
        RepositoryPaginatorContext<A, I> context = new RepositoryPaginatorContext<>(paginationConfig,
                repository,
                options);
        context.setPageIndex(pageIndex);
        return new SizePickerImpl<>(context);
    }

    @Override
    public LimitPicker<Slice<A>, A> byOffset(long startingOffset) {
        RepositoryPaginatorContext<A, I> context = new RepositoryPaginatorContext<>(paginationConfig,
                repository,
                options);
        context.setOffset(startingOffset);
        return new LimitPickerImpl<>(context, PaginationMode.OFFSET);
    }

    @Override
    public KeyValuePicker<A> byAttribute(String attributeName) {
        RepositoryPaginatorContext<A, I> context = new RepositoryPaginatorContext<>(paginationConfig,
                repository,
                options);
        context.setAttribute(attributeName);
        return new AfterKeyPickerImpl<>(context, PaginationMode.ATTRIBUTE);
    }
}