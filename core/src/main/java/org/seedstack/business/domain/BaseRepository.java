/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.lang.reflect.Type;
import javax.inject.Inject;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.specification.dsl.SpecificationBuilder;

/**
 * An helper base class that can be extended to create an <strong>implementation</strong> of a
 * repository interface which, in turn, must extend {@link Repository}.
 *
 * <p> This class is mainly used as a common base for specialized technology-specific
 * implementations. Client code will often extend these more specialized classes instead of this
 * one. </p>
 *
 * @param <A> Type of the aggregate root.
 * @param <I> Type of the aggregate root identifier.
 * @see Repository
 * @see org.seedstack.business.util.inmemory.BaseInMemoryRepository
 */
public abstract class BaseRepository<A extends AggregateRoot<I>, I> implements Repository<A, I> {

    private static final int AGGREGATE_INDEX = 0;
    private static final int KEY_INDEX = 1;
    private final Class<A> aggregateRootClass;
    private final Class<I> idClass;
    @Inject
    private SpecificationBuilder specificationBuilder;

    /**
     * Creates a base domain repository. Actual classes managed by the repository are determined by
     * reflection.
     */
    @SuppressWarnings("unchecked")
    protected BaseRepository() {
        Type[] generics = BusinessUtils.resolveGenerics(BaseRepository.class, getClass());
        this.aggregateRootClass = (Class<A>) generics[AGGREGATE_INDEX];
        this.idClass = (Class<I>) generics[KEY_INDEX];
    }

    /**
     * Creates a base domain repository. Actual classes managed by the repository are specified
     * explicitly. This can be used to create a dynamic implementation of a repository.
     *
     * @param aggregateRootClass the aggregate root class.
     * @param idClass            the aggregate root identifier class.
     */
    protected BaseRepository(Class<A> aggregateRootClass, Class<I> idClass) {
        this.aggregateRootClass = aggregateRootClass;
        this.idClass = idClass;
    }

    @Override
    public Class<A> getAggregateRootClass() {
        return aggregateRootClass;
    }

    @Override
    public Class<I> getIdentifierClass() {
        return idClass;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }
}
