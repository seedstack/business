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
import org.seedstack.business.pagination.dsl.SizePicker;
import org.seedstack.business.pagination.dsl.SpecificationPicker;

class SizePickerImpl<A extends AggregateRoot<ID>, ID> extends SpecificationPickerImpl<Page<A>, A, ID> implements SizePicker<A, ID> {
    private final PaginatorContext<A, ID> context;

    SizePickerImpl(PaginatorContext<A, ID> context) {
        super(context, PaginationMode.PAGE);
        this.context = context;
    }

    @Override
    public SpecificationPicker<Page<A>, A, ID> ofSize(long size) {
        this.context.setLimit(size);
        return this;
    }
}
