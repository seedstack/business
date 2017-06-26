/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.AbstractPage;
import org.seedstack.business.pagination.builder.PaginatorSpecificationPicker;
import org.seedstack.business.specification.Specification;

class PaginatorSpecificationPickerImpl<V extends AbstractPage<A>, A extends AggregateRoot<ID>, ID> implements PaginatorSpecificationPicker<V, A, ID> {
    private final PaginatorBuilderContext<A, ID> context;
    private final PaginationMode mode;

    PaginatorSpecificationPickerImpl(PaginatorBuilderContext<A, ID> context, PaginationMode mode) {
        this.context = context;
        this.mode = mode;
    }

    @Override
    public V paginate(Specification<A> spec) {
        return buildView(spec);
    }

    @Override
    public V paginate() {
        return buildView(Specification.any());
    }

    @SuppressWarnings("unchecked")
    private V buildView(Specification<A> spec) {
        switch (mode) {
            case KEY:
            case OFFSET:
                return (V) context.buildChunk(spec);
            case PAGE:
                return (V) context.buildPage(spec);
            default:
                throw new IllegalStateException("Illegal mode");
        }
    }
}