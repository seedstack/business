/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.internal.utils.BusinessUtils;

import java.lang.reflect.Type;

/**
 * An helper base class that can be extended to create an <strong>implementation</strong> of a repository interface which,
 * in turn, must extend {@link Repository}.
 *
 * <p>
 * This class is mainly used as a common base for specialized technology-specific implementations. Client code will often
 * extend these more specialized classes instead of this one.
 * </p>
 *
 * @param <A>  Type of the aggregate root.
 * @param <ID> Type of the aggregate root identifier.
 * @see Repository
 * @see org.seedstack.business.util.inmemory.BaseInMemoryRepository
 */
public abstract class BaseRepository<A extends AggregateRoot<ID>, ID> implements Repository<A, ID> {
    private static final int AGGREGATE_INDEX = 0;
    private static final int KEY_INDEX = 1;
    private final Class<A> aggregateRootClass;
    private final Class<ID> idClass;

    /**
     * Creates a base domain repository. Actual classes managed by the repository are determined by reflection.
     */
    @SuppressWarnings("unchecked")
    protected BaseRepository() {
        Type[] generics = BusinessUtils.resolveGenerics(BaseRepository.class, getClass());
        this.aggregateRootClass = (Class<A>) generics[AGGREGATE_INDEX];
        this.idClass = (Class<ID>) generics[KEY_INDEX];
    }

    /**
     * Creates a base domain repository. Actual classes managed by the repository are specified explicitly. This can
     * be used to create a dynamic implementation of a repository.
     *
     * @param aggregateRootClass the aggregate root class.
     * @param idClass            the aggregate root identifier class.
     */
    protected BaseRepository(Class<A> aggregateRootClass, Class<ID> idClass) {
        this.aggregateRootClass = aggregateRootClass;
        this.idClass = idClass;
    }

    @Override
    public Class<A> getAggregateRootClass() {
        return aggregateRootClass;
    }

    @Override
    public Class<ID> getIdentifierClass() {
        return idClass;
    }
}
