/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
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
import org.seedstack.business.pagination.dsl.PaginationTypePicker;
import org.seedstack.business.pagination.dsl.RepositoryOptionsPicker;

class RepositoryOptionsTypePickerImpl<A extends AggregateRoot<I>, I> extends RepositoryPaginationPickerImpl<A, I>
        implements RepositoryOptionsPicker<A, I> {

    RepositoryOptionsTypePickerImpl(BusinessConfig.PaginationConfig paginationConfig, Repository<A, I> repository) {
        super(paginationConfig, repository);
    }

    @Override
    public PaginationTypePicker<A> withOptions(Repository.Option... options) {
        checkNotNull(options, "Options cannot be null");
        this.options = options;
        return this;
    }
}
