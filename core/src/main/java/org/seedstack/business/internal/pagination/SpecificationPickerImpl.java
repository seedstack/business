/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.SpecificationPicker;
import org.seedstack.business.specification.Specification;

class SpecificationPickerImpl<V extends Slice<A>, A extends AggregateRoot<ID>, ID> implements SpecificationPicker<V, A, ID> {
    private final PaginatorContext<A, ID> context;
    private final PaginationMode mode;

    SpecificationPickerImpl(PaginatorContext<A, ID> context, PaginationMode mode) {
        this.context = context;
        this.mode = mode;
    }

    @Override
    public V matching(Specification<A> spec) {
        return buildView(spec);
    }

    @Override
    public V all() {
        return buildView(Specification.any());
    }

    @SuppressWarnings("unchecked")
    private V buildView(Specification<A> spec) {
        switch (mode) {
            case ATTRIBUTE:
                return (V) context.buildSlice(spec);
            case OFFSET:
                return (V) context.buildSlice(spec);
            case PAGE:
                return (V) context.buildPage(spec);
            default:
                throw new IllegalStateException("Unknown pagination mode " + mode);
        }
    }
}