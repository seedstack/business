/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ObjectArrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.LimitOption;
import org.seedstack.business.domain.OffsetOption;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.SimplePage;
import org.seedstack.business.pagination.SimpleSlice;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.specification.Specification;

class RepositoryPaginatorContext<A extends AggregateRoot<I>, I> extends AbstractPaginatorContext<A> {
    private final Repository<A, I> repository;
    private final Repository.Option[] options;

    RepositoryPaginatorContext(BusinessConfig.PaginationConfig paginationConfig, Repository<A, I> repository,
            Repository.Option... options) {
        super(paginationConfig);
        this.repository = checkNotNull(repository, "Repository cannot be null");
        checkNotNull(options, "Options cannot be null");
        for (Repository.Option option : options) {
            if (option instanceof OffsetOption) {
                throw new IllegalArgumentException("Cannot specify an offset when using pagination");
            } else if (option instanceof LimitOption) {
                throw new IllegalArgumentException("Cannot specify a limit when using pagination");
            }
        }
        this.options = options;
    }

    @Override
    Page<A> buildPage(Specification<A> specification) {
        checkState(getMode() == PaginationMode.PAGE, "A page can only be built in PAGE pagination mode");
        Stream<A> stream = buildStream(specification);
        return new SimplePage<>(stream.collect(Collectors.toList()),
                getPageIndex(),
                getLimit(),
                repository.count(specification));
    }

    @Override
    Slice<A> buildSlice(Specification<A> specification) {
        return new SimpleSlice<>(buildStream(specification).collect(Collectors.toList()));
    }

    private Stream<A> buildStream(Specification<A> specification) {
        Stream<A> streamRepo;
        PaginationMode mode = getMode();
        if (mode.equals(PaginationMode.ATTRIBUTE)) {
            streamRepo = repository.get(specification.and(getAttributeSpecification()),
                    applyLimit(options, getLimit()));
        } else if (mode.equals(PaginationMode.OFFSET)) {
            streamRepo = repository.get(specification, applyLimit(applyOffset(options, getOffset()), getLimit()));
        } else if (mode.equals(PaginationMode.PAGE)) {
            streamRepo = repository.get(specification, applyLimit(applyOffset(
                    options,
                    (getPaginationConfig().isZeroBasedPageIndex() ? getPageIndex() : getPageIndex() - 1) * getLimit()),
                    getLimit()));
        } else {
            throw new IllegalStateException("Unsupported pagination mode " + mode);
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