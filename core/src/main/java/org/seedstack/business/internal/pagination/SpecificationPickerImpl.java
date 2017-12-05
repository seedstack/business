/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.SpecificationPicker;
import org.seedstack.business.specification.Specification;

class SpecificationPickerImpl<S extends Slice<T>, T>
        implements SpecificationPicker<S, T> {
    private final AbstractPaginatorContext<T> context;
    private final PaginationMode mode;

    SpecificationPickerImpl(AbstractPaginatorContext<T> context, PaginationMode mode) {
        this.context = context;
        this.mode = mode;
    }

    @Override
    public S matching(Specification<T> spec) {
        return buildView(spec);
    }

    @Override
    public S all() {
        return buildView(Specification.any());
    }

    @SuppressWarnings("unchecked")
    @SuppressFBWarnings(value = "DB_DUPLICATE_SWITCH_CLAUSES", justification = "Better this than falling through cases")
    private S buildView(Specification<T> spec) {
        switch (mode) {
            case ATTRIBUTE:
                return (S) context.buildSlice(spec);
            case OFFSET:
                return (S) context.buildSlice(spec);
            case PAGE:
                return (S) context.buildPage(spec);
            default:
                throw new IllegalStateException("Unknown pagination mode " + mode);
        }
    }
}