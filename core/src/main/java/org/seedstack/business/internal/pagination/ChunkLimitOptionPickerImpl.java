/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Chunk;
import org.seedstack.business.pagination.builder.ChunkLimitOptionPicker;
import org.seedstack.business.pagination.builder.PaginatorSpecificationPicker;

class ChunkLimitOptionPickerImpl<A extends AggregateRoot<ID>, ID> extends PaginatorSpecificationPickerImpl<Chunk<A>, A, ID> implements ChunkLimitOptionPicker<A, ID> {
    private final PaginatorBuilderContext<A, ID> context;

    ChunkLimitOptionPickerImpl(PaginatorBuilderContext<A, ID> context, PaginationMode mode) {
        super(context, mode);
        this.context = context;
    }

    @Override
    public PaginatorSpecificationPicker<Chunk<A>, A, ID> limit(int limit) {
        this.context.addLimit(limit);
        return this;
    }
}
