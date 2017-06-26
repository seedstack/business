/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.builder.ChunkAfterKeyOptionPicker;
import org.seedstack.business.pagination.builder.ChunkLimitOptionPicker;

class ChunkAfterKeyOptionPickerImpl<A extends AggregateRoot<ID>, ID> extends ChunkLimitOptionPickerImpl<A, ID> implements ChunkAfterKeyOptionPicker<A, ID> {
    private final PaginatorBuilderContext<A, ID> context;

    ChunkAfterKeyOptionPickerImpl(PaginatorBuilderContext<A, ID> context, PaginationMode mode) {
        super(context, mode);
        this.context = context;
    }

    @Override
    public <T extends Comparable<? super T>> ChunkLimitOptionPicker<A, ID> after(T value) {
        this.context.addAfter(value);
        return this;
    }
}
