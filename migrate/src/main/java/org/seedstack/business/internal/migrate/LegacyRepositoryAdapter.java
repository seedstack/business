/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import java.util.Optional;
import java.util.stream.Stream;
import org.seedstack.business.domain.AggregateExistsException;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.LegacyRepository;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;

class LegacyRepositoryAdapter<A extends AggregateRoot<I>, I> implements LegacyRepository<A, I> {
    private final Repository<A, I> repository;

    LegacyRepositoryAdapter(Repository<A, I> repository) {
        this.repository = repository;
    }

    @Override
    public void add(A aggregate) throws AggregateExistsException {
        repository.add(aggregate);
    }

    @Override
    public Stream<A> get(Specification<A> specification, Option... options) {
        return repository.get(specification, options);
    }

    @Override
    public Optional<A> get(I id) {
        return repository.get(id);
    }

    @Override
    public boolean contains(Specification<A> specification) {
        return repository.contains(specification);
    }

    @Override
    public boolean contains(I id) {
        return repository.contains(id);
    }

    @Override
    public boolean contains(A aggregate) {
        return repository.contains(aggregate);
    }

    @Override
    public long count(Specification<A> specification) {
        return repository.count(specification);
    }

    @Override
    public long size() {
        return repository.size();
    }

    @Override
    public boolean isEmpty() {
        return repository.isEmpty();
    }

    @Override
    public long remove(Specification<A> specification) throws AggregateNotFoundException {
        return repository.remove(specification);
    }

    @Override
    public void remove(I id) throws AggregateNotFoundException {
        repository.remove(id);
    }

    @Override
    public void remove(A aggregate) throws AggregateNotFoundException {
        repository.remove(aggregate);
    }

    @Override
    public void update(A aggregate) throws AggregateNotFoundException {
        repository.update(aggregate);
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public Class<A> getAggregateRootClass() {
        return repository.getAggregateRootClass();
    }

    @Override
    public Class<I> getIdentifierClass() {
        return repository.getIdentifierClass();
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return repository.getSpecificationBuilder();
    }
}
