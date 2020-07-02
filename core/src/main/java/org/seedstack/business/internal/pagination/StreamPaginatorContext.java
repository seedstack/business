/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.SimpleSlice;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.specification.Specification;

class StreamPaginatorContext<T> extends AbstractPaginatorContext<T> {
    private final Stream<T> source;

    StreamPaginatorContext(BusinessConfig.PaginationConfig paginationConfig, Stream<T> source) {
        super(paginationConfig);
        this.source = checkNotNull(source, "Pagination source cannot be null");
    }

    @Override
    Page<T> buildPage(Specification<T> specification) {
        throw new UnsupportedOperationException("Page mode is only-supported when paginating a repository");
    }

    @Override
    Slice<T> buildSlice(Specification<T> specification) {
        return new SimpleSlice<>(buildStream(specification).collect(Collectors.toList()));
    }

    private Stream<T> buildStream(Specification<T> specification) {
        Stream<T> resultingStream;
        PaginationMode mode = getMode();
        if (mode.equals(PaginationMode.ATTRIBUTE)) {
            resultingStream = source.filter(specification.and(getAttributeSpecification()).asPredicate())
                    .limit(getLimit());
        } else if (mode.equals(PaginationMode.OFFSET)) {
            resultingStream = source.skip(getOffset()).limit(getLimit());
        } else {
            throw new IllegalStateException("Unsupported pagination mode " + mode);
        }
        return resultingStream;
    }
}