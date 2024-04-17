/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.dsl.Paginator;
import org.seedstack.business.pagination.dsl.RepositoryOptionsPicker;
import org.seedstack.business.pagination.dsl.SlicePaginationPicker;
import org.seedstack.seed.Configuration;

class PaginatorImpl implements Paginator {
    @Configuration
    private BusinessConfig.PaginationConfig paginationConfig;

    @Override
    public <A extends AggregateRoot<I>, I> RepositoryOptionsPicker<A, I> paginate(Repository<A, I> repository) {
        return new RepositoryOptionsTypePickerImpl<>(paginationConfig, repository);
    }

    @Override
    public <T> SlicePaginationPicker<T> paginate(T object) {
        return paginate(Stream.of(object));
    }

    @Override
    public <T> SlicePaginationPicker<T> paginate(Stream<T> stream) {
        return new StreamPaginationPickerImpl<>(paginationConfig, stream);
    }

    @Override
    public <T> SlicePaginationPicker<T> paginate(Iterable<T> iterable) {
        return paginate(StreamSupport.stream(iterable.spliterator(), false));
    }
}