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
 * @param <AGGREGATE> the aggregate root type
 * @param <KEY>       the key type
 */
public abstract class BaseRepository<AGGREGATE extends AggregateRoot<KEY>, KEY> implements Repository<AGGREGATE, KEY> {
    private static final int AGGREGATE_INDEX = 0;
    private static final int KEY_INDEX = 1;

    protected final Class<AGGREGATE> aggregateRootClass;
    protected final Class<KEY> keyClass;

    /**
     * Constructs a base repository.
     * <p>
     * The aggregate root class and the key class are found by reflection.
     * </p>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected BaseRepository() {
        Type[] generics = BusinessUtils.resolveGenerics(BaseRepository.class, getClass());
        this.aggregateRootClass = (Class<AGGREGATE>) generics[AGGREGATE_INDEX];
        this.keyClass = (Class<KEY>) generics[KEY_INDEX];
    }

    /**
     * Constructs a base repository settings explicitly the aggregate root class and the key class.
     * <p>
     * This is used when the implementation class does not resolve the generics. Since the generic
     * types can't be resolve at runtime, they should be passed explicitly.
     * </p>
     *
     * @param aggregateRootClass the aggregate root class
     * @param keyClass           the key class
     */
    protected BaseRepository(Class<AGGREGATE> aggregateRootClass, Class<KEY> keyClass) {
        this.aggregateRootClass = aggregateRootClass;
        this.keyClass = keyClass;
    }

    @Override
    public Class<AGGREGATE> getAggregateRootClass() {
        return aggregateRootClass;
    }

    @Override
    public Class<KEY> getIdentifierClass() {
        return keyClass;
    }
}
