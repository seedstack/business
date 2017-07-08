/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.repositories;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.specification.Specification;

import java.util.stream.Stream;

public class DummyRepository<A extends AggregateRoot<ID>, ID> extends BaseRepository<A, ID> {
    public DummyRepository() {
    }

    public DummyRepository(Class<A> aggregateRootClass, Class<ID> idClass) {
        super(aggregateRootClass, idClass);
    }

    @Override
    public void add(A aggregate) {
    }

    @Override
    public Stream<A> get(Specification<A> specification, Repository.Option... options) {
        return Stream.empty();
    }

    @Override
    public long remove(Specification<A> specification) {
        return 0;
    }
}
