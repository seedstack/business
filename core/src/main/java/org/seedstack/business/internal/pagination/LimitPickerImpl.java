/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.SpecificationPicker;

class LimitPickerImpl<S extends Slice<T>, T> extends SpecificationPickerImpl<S, T>
        implements LimitPicker<S, T> {

    private final AbstractPaginatorContext<T> context;

    LimitPickerImpl(AbstractPaginatorContext<T> context, PaginationMode mode) {
        super(context, mode);
        this.context = context;
    }

    @Override
    public SpecificationPicker<S, T> limit(long limit) {
        this.context.setLimit(limit);
        return this;
    }
}
