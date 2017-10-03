/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;

class AfterKeyPickerImpl<A extends AggregateRoot<I>, I> extends LimitPickerImpl<Slice<A>, A, I> implements
        KeyValuePicker<A, I> {

    private final PaginatorContext<A, I> context;

    AfterKeyPickerImpl(PaginatorContext<A, I> context, PaginationMode mode) {
        super(context, mode);
        this.context = context;
    }

    @Override
    public <T extends Comparable<? super T>> LimitPicker<Slice<A>, A, I> before(T value) {
        this.context.setBeforeAttributeValue(value);
        return this;
    }

    @Override
    public <T extends Comparable<? super T>> LimitPicker<Slice<A>, A, I> after(T value) {
        this.context.setAfterAttributeValue(value);
        return this;
    }
}
