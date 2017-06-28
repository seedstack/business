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
import org.seedstack.business.pagination.builder.PaginatorBuilder;
import org.seedstack.business.pagination.builder.RepositoryOptionPicker;

class PaginatorBuilderImpl implements PaginatorBuilder {
    @Override
    public <A extends AggregateRoot<ID>, ID> RepositoryOptionPicker<A, ID> repository(Repository<A, ID> repository){
        return new RepositoryOptionPickerImpl<>(repository);
    }
}