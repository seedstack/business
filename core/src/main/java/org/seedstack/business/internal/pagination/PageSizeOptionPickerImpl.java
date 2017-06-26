/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.builder.PageSizeOptionPicker;
import org.seedstack.business.pagination.builder.PaginatorSpecificationPicker;

class PageSizeOptionPickerImpl<A extends AggregateRoot<ID>, ID> extends PaginatorSpecificationPickerImpl<Page<A>, A, ID> implements PageSizeOptionPicker<A, ID> {
    private final PaginatorBuilderContext<A, ID> context;

    PageSizeOptionPickerImpl(PaginatorBuilderContext<A, ID> context) {
        super(context, PaginationMode.PAGE);
        this.context = context;
    }

    @Override
    public PaginatorSpecificationPicker<Page<A>, A, ID> size(int size){
        this.context.addPageSize(size);
        return this;
    }
}
