/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.RepositoryOptions;
import org.seedstack.business.domain.specification.Specification;

import java.util.Optional;
import java.util.stream.Stream;

public class DummyRepository<A extends AggregateRoot<ID>, ID> extends BaseRepository<A, ID> {
    public DummyRepository() {
    }

    public DummyRepository(Class<A> aggregateRootClass, Class<ID> idClass) {
        super(aggregateRootClass, idClass);
    }

    @Override
    public Optional<A> get(ID id) {
        return Optional.empty();
    }

    @Override
    public Stream<A> get(Specification<A> specification, RepositoryOptions... options) {
        return Stream.empty();
    }

    @Override
    public boolean contains(ID id) {
        return false;
    }

    @Override
    public void add(A aggregate) {

    }

    @Override
    public A update(A aggregate) {
        return null;
    }

    @Override
    public void remove(A aggregate) {

    }

    @Override
    public void remove(ID id) {

    }

    @Override
    public long remove(Specification<A> specification) {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public long count(Specification<A> specification) {
        return 0;
    }

    @Override
    public long count() {
        return 0;
    }
}
