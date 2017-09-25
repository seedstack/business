/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ObjectArrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.LimitOption;
import org.seedstack.business.domain.OffsetOption;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.SimplePage;
import org.seedstack.business.pagination.SimpleSlice;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.specification.AttributeSpecification;
import org.seedstack.business.specification.GreaterThanSpecification;
import org.seedstack.business.specification.LessThanSpecification;
import org.seedstack.business.specification.Specification;

class PaginatorContext<A extends AggregateRoot<I>, I> {

  private final Repository<A, I> repository;
  private final Repository.Option[] options;
  private PaginationMode mode = PaginationMode.NONE;
  private long limit = 10;

  // For offset-based slicing
  private long offset = 0;

  // For page-based slicing
  private long pageIndex = 1;

  // For attribute-based slicing
  private String attribute;
  private Specification<A> attributeSpecification;

  PaginatorContext(Repository<A, I> repository, Repository.Option... options) {
    checkNotNull(repository, "Repository cannot be null");
    checkNotNull(options, "Options cannot be null");
    this.repository = repository;
    for (Repository.Option option : options) {
      if (option instanceof OffsetOption) {
        throw new IllegalArgumentException("Cannot specify an offset when using pagination");
      } else if (option instanceof LimitOption) {
        throw new IllegalArgumentException("Cannot specify a limit when using pagination");
      }
    }
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

  <T extends Comparable<? super T>> void setBeforeAttributeValue(T value) {
    checkState(mode == PaginationMode.ATTRIBUTE && attribute != null,
        "A value can only be set in ATTRIBUTE mode");
    this.attributeSpecification = new AttributeSpecification<>(attribute,
        new LessThanSpecification<>(value));
  }

  <T extends Comparable<? super T>> void setAfterAttributeValue(T value) {
    checkState(mode == PaginationMode.ATTRIBUTE && attribute != null,
        "A value can only be set in ATTRIBUTE mode");
    this.attributeSpecification = new AttributeSpecification<>(attribute,
        new GreaterThanSpecification<>(value));
  }

  void setLimit(long limit) {
    checkState(mode != PaginationMode.NONE,
        "Limit can only be set after a pagination mode has been defined");
    checkArgument(limit > 0, "Limit must be greater than 0");
    this.limit = limit;
  }

  Page<A> buildPage(Specification<A> specification) {
    checkState(mode == PaginationMode.PAGE, "A page can only be built in PAGE pagination mode");
    Stream<A> stream = buildStream(specification);
    return new SimplePage<>(stream.collect(Collectors.toList()), pageIndex, limit,
        repository.count(specification));
  }

  Slice<A> buildSlice(Specification<A> specification) {
    return new SimpleSlice<>(buildStream(specification).collect(Collectors.toList()));
  }

  private Stream<A> buildStream(Specification<A> specification) {
    Stream<A> streamRepo;
    if (mode.equals(PaginationMode.ATTRIBUTE)) {
      streamRepo = repository
          .get(specification.and(attributeSpecification), applyLimit(options, limit));
    } else if (mode.equals(PaginationMode.OFFSET)) {
      streamRepo = repository.get(specification, applyLimit(applyOffset(options, offset), limit));
    } else if (mode.equals(PaginationMode.PAGE)) {
      streamRepo = repository
          .get(specification, applyLimit(applyOffset(options, (pageIndex - 1) * limit), limit));
    } else {
      throw new IllegalStateException("Unknown pagination mode " + mode);
    }
    return streamRepo;
  }

  private Repository.Option[] applyOffset(Repository.Option[] options, long offset) {
    return ObjectArrays.concat(options, new OffsetOption(offset));
  }

  private Repository.Option[] applyLimit(Repository.Option[] options, long limit) {
    return ObjectArrays.concat(options, new LimitOption(limit));
  }
}