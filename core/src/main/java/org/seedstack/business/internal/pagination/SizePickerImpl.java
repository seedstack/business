/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.dsl.SizePicker;
import org.seedstack.business.pagination.dsl.SpecificationPicker;

class SizePickerImpl<T> extends SpecificationPickerImpl<Page<T>, T> implements SizePicker<T> {

    private final AbstractPaginatorContext<T> context;

    SizePickerImpl(AbstractPaginatorContext<T> context) {
        super(context, PaginationMode.PAGE);
        this.context = context;
    }

    @Override
    public SpecificationPicker<Page<T>, T> limit(long size) {
        this.context.setLimit(size);
        return this;
    }
}
