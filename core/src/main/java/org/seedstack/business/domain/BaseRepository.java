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
 * This class serves as inheritance base for all repositories.
 *
 * @param <A>  the type of the aggregate root class.
 * @param <ID> the type of identifier of the aggregate root class.
 */
public abstract class BaseRepository<A extends AggregateRoot<ID>, ID> implements Repository<A, ID> {
    private static final int AGGREGATE_INDEX = 0;
    private static final int KEY_INDEX = 1;
    private final Class<A> aggregateRootClass;
    private final Class<ID> idClass;

    /**
     * Constructs a base repository.
     * <p>
     * The aggregate root class and the identifier classes are determined by reflection.
     * </p>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected BaseRepository() {
        Type[] generics = BusinessUtils.resolveGenerics(BaseRepository.class, getClass());
        this.aggregateRootClass = (Class<A>) generics[AGGREGATE_INDEX];
        this.idClass = (Class<ID>) generics[KEY_INDEX];
    }

    /**
     * Constructs a base repository settings explicitly the aggregate root class and the identifier class.
     * <p>
     * This is used when the implementation class does not resolve the generics. Since the generic
     * types can't be resolved at runtime, they should be passed explicitly.
     * </p>
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
