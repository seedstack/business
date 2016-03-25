/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import net.jodah.typetools.TypeResolver;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

/**
 * This class serves as inheritance base for all repositories.
 *
 * @param <AGGREGATE> the aggregate root type
 * @param <KEY>       the key type
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseRepository<AGGREGATE extends AggregateRoot<KEY>, KEY> implements Repository<AGGREGATE, KEY> {
    private static final int AGGREGATE_INDEX = 0;
    private static final int KEY_INDEX = 1;

    private final Class<AGGREGATE> aggregateRootClass;
    private final Class<KEY> keyClass;

    /**
     * Constructs a base repository.
     * <p>
     * The aggregate root class and the key class are found by reflection.
     * </p>
     */
    protected BaseRepository() {
        Class<?> subType = SeedReflectionUtils.cleanProxy(getClass());
        Class<?>[] rawArguments = TypeResolver.resolveRawArguments(TypeResolver.resolveGenericType(BaseRepository.class, subType), subType);
        this.aggregateRootClass = (Class<AGGREGATE>) rawArguments[AGGREGATE_INDEX];
        this.keyClass = (Class<KEY>) rawArguments[KEY_INDEX];
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
    public Class<KEY> getKeyClass() {
        return keyClass;
    }
}
