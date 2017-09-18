/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination.dsl;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

/**
 * The paginator is an helper aimed at paginating domain objects provided by a {@link Repository} into
 * {@link org.seedstack.business.pagination.Slice}s or {@link org.seedstack.business.pagination.Page}s. It supports
 * offset-based and key-based pagination.
 */
public interface Paginator {
    /**
     * Initiate a pagination operation using the specified repository.
     *
     * @param repository the repository where the domain objects come from.
     * @param <A>        the aggregate root type that is paginated.
     * @param <ID>       the aggregate root identifier type.
     * @return the next operation of the paginator DSL, allowing to specify repository options.
     */
    <A extends AggregateRoot<ID>, ID> RepositoryOptionsPicker<A, ID> paginate(Repository<A, ID> repository);
}



