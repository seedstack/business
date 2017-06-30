/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import com.google.common.base.Preconditions;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.specification.GreaterThanSpecification;
import org.seedstack.business.specification.PropertySpecification;
import org.seedstack.business.specification.Specification;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

class PaginatorContext<A extends AggregateRoot<ID>, ID> {
    private final Repository<A, ID> repository;
    private final Repository.Options[] options;
    private PaginationMode mode = PaginationMode.NONE;
    private long limit = 10;

    // For offset-based slicing
    private long offset = 0;

    // For page-based slicing
    private long pageIndex = 1;

    // For attribute-based slicing
    private String attribute;
    private Specification<A> attributeSpecification;

    PaginatorContext(Repository<A, ID> repository, Repository.Options... options) {
        Preconditions.checkNotNull(repository, "Repository cannot be null");
        this.repository = repository;
        this.options = options;
    }

    void setPageIndex(long page) {
        checkState(mode == PaginationMode.NONE, "Pagination mode cannot be changed");
        checkArgument(page > 0, "Page index must be greater than 0");
        this.pageIndex = page;
        this.mode = PaginationMode.PAGE;
    }

    void setOffset(long offset) {
        checkState(mode == PaginationMode.NONE, "Pagination mode cannot be changed");
        checkArgument(offset >= 0, "Offset must be equal or greater than 0");
        this.offset = offset;
        this.mode = PaginationMode.OFFSET;
    }

    void setAttribute(String attribute) {
        checkState(mode == PaginationMode.NONE, "Pagination mode cannot be changed");
        this.attribute = attribute;
        this.mode = PaginationMode.ATTRIBUTE;
    }

    <T extends Comparable<? super T>> void setAfterAttributeValue(T value) {
        checkState(mode == PaginationMode.ATTRIBUTE && attribute != null, "A value can only be set in ATTRIBUTE mode");
        this.attributeSpecification = new PropertySpecification<>(attribute, new GreaterThanSpecification<>(value));
    }

    void setLimit(long limit) {
        checkState(mode != PaginationMode.NONE, "Limit can only be set after a pagination mode has been defined");
        checkArgument(limit > 0, "Limit must be greater than 0");
        this.limit = limit;
    }

    Page<A> buildPage(Specification<A> specification) {
        checkState(mode == PaginationMode.PAGE, "A page can only be built in PAGE pagination mode");
        Stream<A> stream = buildStream(specification);
        return new PageImpl<>(stream.collect(Collectors.toList()), pageIndex, limit, repository.count(specification));
    }

    Slice<A> buildSlice(Specification<A> specification) {
        return new SliceImpl<>(buildStream(specification).collect(Collectors.toList()));
    }

    private Stream<A> buildStream(Specification<A> specification) {
        Stream<A> streamRepo;
        if (mode.equals(PaginationMode.ATTRIBUTE)) {
            streamRepo = repository.get(specification.and(attributeSpecification), options);
        } else if (mode.equals(PaginationMode.OFFSET)) {
            streamRepo = repository.get(specification, options).skip(offset); // TODO change from skip() to repository option
        } else if (mode.equals(PaginationMode.PAGE)) {
            streamRepo = repository.get(specification, options).skip((pageIndex - 1) * limit); // TODO change from skip() to repository option
        } else {
            throw new IllegalStateException("Unknown pagination mode " + mode);
        }
        streamRepo = streamRepo.limit(limit);
        return streamRepo;
    }
}