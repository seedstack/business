package org.seedstack.business.internal.pagination;

import com.google.common.base.Preconditions;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Chunk;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.specification.GreaterThanSpecification;
import org.seedstack.business.specification.PropertySpecification;
import org.seedstack.business.specification.Specification;

import java.util.stream.Collectors;
import java.util.stream.Stream;

class PaginatorBuilderContext<A extends AggregateRoot<ID>, ID> {
    private final Repository<A, ID> repository;
    private int startElement;
    private int startPage;
    private int limit = -1;
    private int size;
    private String afterKey;
    private Specification<A> afterSpecification;
    private PaginationMode mode = PaginationMode.NONE;
    private Repository.Options[] options;

    PaginatorBuilderContext(Repository<A, ID> repository, Repository.Options... options) {
        Preconditions.checkNotNull(repository, "Repository cannot be null");
        this.repository = repository;
        this.options = options;
    }

    void addLimit(int limit) {
        if (mode.equals(PaginationMode.NONE)) {
            throw new IllegalStateException("No mode (OFFSET/KEY) has been set");
        }
        this.limit = limit;
    }

    void addPageSize(int size) {
        if (mode.equals(PaginationMode.NONE)) {
            throw new IllegalStateException("No mode PAGE has been set");
        }
        Preconditions.checkNotNull(size, "Page size cannot be null");
        this.size = size;
    }

    void filterByPage(int page) {
        this.startPage = page;
        setMode(PaginationMode.PAGE);
    }

    void filterByOffset(int page) {
        this.startElement = page;
        setMode(PaginationMode.OFFSET);
    }

    void filterByKey(String key) {
        this.afterKey = key;
        setMode(PaginationMode.KEY);
    }

    void setOptions(Repository.Options... options) {
        this.options = options;
    }

    <T extends Comparable<? super T>> void addAfter(T value) {
        if (this.afterKey == null) {
            throw new IllegalStateException("No KEY has been set");
        }
        this.afterSpecification = new PropertySpecification<>(afterKey, new GreaterThanSpecification<>(value));
    }

    private void setMode(PaginationMode mode) {
        this.mode = mode;
    }

    Page<A> buildPage(Specification<A> specification) {
        long offset = this.startPage * this.size;
        long globalSize = repository.count(specification);
        Stream<A> streamRepo = repository.get(specification, options).skip(offset);
        if (size != -1) {
            streamRepo = streamRepo.limit(size);
        }
        return new Page<>(streamRepo.collect(Collectors.toList()), offset, startPage, size, globalSize);
    }

    Chunk<A> buildChunk(Specification<A> specification) {
        long globalSize;
        Stream<A> streamRepo;
        if (mode.equals(PaginationMode.KEY)) {
            globalSize = repository.count(specification.and(afterSpecification));
            streamRepo = repository.get(specification.and(afterSpecification), options);
        } else if (mode.equals(PaginationMode.OFFSET)) {
            globalSize = repository.count(specification);
            streamRepo = repository.get(specification, options).skip(startElement);
        } else {
            throw new IllegalStateException("Chunk can only be built in KEY or OFFSET mode");
        }
        if (limit != -1) {
            streamRepo = streamRepo.limit(limit);
        }
        return new Chunk<>(streamRepo.collect(Collectors.toList()), startElement, globalSize);
    }

}