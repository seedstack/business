/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination.dsl;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

/**
 * An element of the {@link Paginator} DSL allowing to specify repository options.
 *
 * @param <A> the aggregate root type that is paginated.
 * @param <I> the aggregate root identifier type.
 */
public interface RepositoryOptionsPicker<A extends AggregateRoot<I>, I> extends PaginationTypePicker<A> {

    /**
     * Allows to specify the repository options used when invoking the repository {@link
     * Repository#get(org.seedstack.business.specification.Specification, Repository.Option...)}
     * method.
     *
     * @param options the options to use.
     * @return the next operation of the paginator DSL, allowing to specify the pagination type.
     */
    PaginationTypePicker<A> withOptions(Repository.Option... options);
}
