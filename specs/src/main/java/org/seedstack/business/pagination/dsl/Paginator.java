/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.pagination.dsl;

import java.util.stream.Stream;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

/**
 * Paginator is a DSL aimed at paginating arbitrary streams or iterables, or domain objects provided by a
 * {@link Repository}. The result can either be a {@link org.seedstack.business.pagination.Slice} or a
 * {@link org.seedstack.business.pagination.Page}.
 * It supports page-based, offset-based and key-based pagination.
 */
public interface Paginator {

    /**
     * Initiate a pagination operation using the specified repository.
     *
     * @param repository the repository where the domain objects come from.
     * @param <A>        the aggregate root type that is paginated.
     * @param <I>        the aggregate root identifier type.
     * @return the next operation of the paginator DSL, allowing to specify repository options.
     */
    <A extends AggregateRoot<I>, I> RepositoryOptionsPicker<A, I> paginate(Repository<A, I> repository);

    /**
     * Initiate a pagination operation using a unique object as source.
     *
     * @param object the source object.
     * @param <T>    the type of the source object.
     * @return the next operation of the paginator DSL, allowing to choose the type of pagination.
     */
    <T> SlicePaginationPicker<T> paginate(T object);

    /**
     * Initiate a pagination operation using a stream of objects as source.
     *
     * @param stream the source stream.
     * @param <T>    the type of the source object.
     * @return the next operation of the paginator DSL, allowing to choose the type of pagination.
     */
    <T> SlicePaginationPicker<T> paginate(Stream<T> stream);

    /**
     * Initiate a pagination operation using an {@link Iterable} as source.
     *
     * @param iterable the source iterable.
     * @param <T>      the type of the source object.
     * @return the next operation of the paginator DSL, allowing to choose the type of pagination.
     */
    <T> SlicePaginationPicker<T> paginate(Iterable<T> iterable);
}



