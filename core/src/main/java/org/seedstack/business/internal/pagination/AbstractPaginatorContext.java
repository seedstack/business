/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.seedstack.business.BusinessConfig;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.specification.AttributeSpecification;
import org.seedstack.business.specification.GreaterThanSpecification;
import org.seedstack.business.specification.LessThanSpecification;
import org.seedstack.business.specification.Specification;

abstract class AbstractPaginatorContext<T> {
    private final BusinessConfig.PaginationConfig paginationConfig;
    private PaginationMode mode = PaginationMode.NONE;
    private long limit = 10;

    // For page-based slicing
    private long pageIndex = 1;

    // For offset-based slicing
    private long offset = 0;

    // For attribute-based slicing
    private String attribute;
    private Specification<T> attributeSpecification;

    AbstractPaginatorContext(BusinessConfig.PaginationConfig paginationConfig) {
        this.paginationConfig = checkNotNull(paginationConfig, "Pagination configuration cannot be null");
    }

    <C extends Comparable<? super C>> void setBeforeAttributeValue(C value) {
        checkState(mode == PaginationMode.ATTRIBUTE && attribute != null, "A value can only be set in ATTRIBUTE mode");
        this.attributeSpecification = new AttributeSpecification<>(attribute, new LessThanSpecification<>(value));
    }

    <C extends Comparable<? super C>> void setAfterAttributeValue(C value) {
        checkState(mode == PaginationMode.ATTRIBUTE && attribute != null, "A value can only be set in ATTRIBUTE mode");
        this.attributeSpecification = new AttributeSpecification<>(attribute, new GreaterThanSpecification<>(value));
    }

    abstract Page<T> buildPage(Specification<T> specification);

    abstract Slice<T> buildSlice(Specification<T> specification);

    BusinessConfig.PaginationConfig getPaginationConfig() {
        return paginationConfig;
    }

    PaginationMode getMode() {
        return mode;
    }

    long getPageIndex() {
        return pageIndex;
    }

    void setPageIndex(long page) {
        checkState(mode == PaginationMode.NONE, "Pagination mode cannot be changed");
        if (paginationConfig.isZeroBasedPageIndex()) {
            this.pageIndex = page < 0 ? 0 : page;
        } else {
            this.pageIndex = page <= 0 ? 1 : page;
        }
        this.mode = PaginationMode.PAGE;
    }

    long getLimit() {
        return limit;
    }

    void setLimit(long limit) {
        checkState(mode != PaginationMode.NONE, "Limit can only be set after a pagination mode has been defined");
        checkArgument(limit > 0, "Limit must be greater than 0");
        this.limit = limit;
    }

    long getOffset() {
        return offset;
    }

    void setOffset(long offset) {
        checkState(mode == PaginationMode.NONE, "Pagination mode cannot be changed");
        this.offset = offset < 0 ? 0 : offset;
        this.mode = PaginationMode.OFFSET;
    }

    String getAttribute() {
        return attribute;
    }

    void setAttribute(String attribute) {
        checkState(mode == PaginationMode.NONE, "Pagination mode cannot be changed");
        this.attribute = attribute;
        this.mode = PaginationMode.ATTRIBUTE;
    }

    Specification<T> getAttributeSpecification() {
        return attributeSpecification;
    }
}
