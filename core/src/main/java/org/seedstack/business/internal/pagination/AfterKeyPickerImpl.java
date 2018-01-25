/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;

class AfterKeyPickerImpl<T> extends LimitPickerImpl<Slice<T>, T> implements
        KeyValuePicker<T> {

    private final AbstractPaginatorContext<T> context;

    AfterKeyPickerImpl(AbstractPaginatorContext<T> context, PaginationMode mode) {
        super(context, mode);
        this.context = context;
    }

    @Override
    public <C extends Comparable<? super C>> LimitPicker<Slice<T>, T> before(C value) {
        this.context.setBeforeAttributeValue(value);
        return this;
    }

    @Override
    public <C extends Comparable<? super C>> LimitPicker<Slice<T>, T> after(C value) {
        this.context.setAfterAttributeValue(value);
        return this;
    }
}
