/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.repositories;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.RepositoryOptions;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.spi.GenericImplementation;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.stream.Stream;


@GenericImplementation
@Named("mock")
public class DefaultRepoSample2<A extends AggregateRoot<K>, K> extends BaseRepository<A, K> {
    @Inject
    public DefaultRepoSample2(@Assisted Object[] genericClasses) {
        super((Class) genericClasses[0], (Class) genericClasses[1]);
    }

    @Override
    public Optional<A> get(K id) {
        return Optional.empty();
    }

    @Override
    public Stream<A> get(Specification<A> specification, RepositoryOptions... options) {
        return Stream.empty();
    }

    @Override
    public boolean contains(K id) {
        return false;
    }

    @Override
    public long count() {
        return 0L;
    }

    @Override
    public void clear() {
    }

    @Override
    public long count(Specification<A> specification) {
        return 0;
    }

    @Override
    public void remove(K id) {
    }

    @Override
    public long remove(Specification<A> specification) {
        return 0;
    }

    @Override
    public void remove(A a) {
    }

    @Override
    public void add(A a) {
    }

    @Override
    public A update(A a) {
        return null;
    }
}
