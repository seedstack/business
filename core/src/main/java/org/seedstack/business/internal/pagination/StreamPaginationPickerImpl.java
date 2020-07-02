/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.stream.Stream;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.SlicePaginationPicker;

class StreamPaginationPickerImpl<T> implements SlicePaginationPicker<T> {
    private final BusinessConfig.PaginationConfig paginationConfig;
    private final Stream<T> source;

    StreamPaginationPickerImpl(BusinessConfig.PaginationConfig paginationConfig, Stream<T> source) {
        this.paginationConfig = paginationConfig;
        this.source = checkNotNull(source, "Pagination source cannot be null");
    }

    @Override
    public LimitPicker<Slice<T>, T> byOffset(long startingOffset) {
        StreamPaginatorContext<T> context = new StreamPaginatorContext<>(paginationConfig, source);
        context.setOffset(startingOffset);
        return new LimitPickerImpl<>(context, PaginationMode.OFFSET);
    }

    @Override
    public KeyValuePicker<T> byAttribute(String attributeName) {
        StreamPaginatorContext<T> context = new StreamPaginatorContext<>(paginationConfig, source);
        context.setAttribute(attributeName);
        return new AfterKeyPickerImpl<>(context, PaginationMode.ATTRIBUTE);
    }
}