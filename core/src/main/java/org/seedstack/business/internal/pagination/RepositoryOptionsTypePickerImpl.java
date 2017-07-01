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
import org.seedstack.business.pagination.dsl.PaginationTypePicker;
import org.seedstack.business.pagination.dsl.RepositoryOptionsPicker;

class RepositoryOptionsTypePickerImpl<A extends AggregateRoot<ID>, ID> extends PaginationTypePickerImpl<A, ID> implements RepositoryOptionsPicker<A, ID> {
    RepositoryOptionsTypePickerImpl(Repository<A, ID> repository) {
        super(repository);
    }

    @Override
    public PaginationTypePicker<A, ID> withOptions(Repository.Options... options) {
        this.setOptions(options);
        return this;
    }
}
