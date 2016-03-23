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

    protected Class<AGGREGATE> aggregateRootClass;
    protected Class<KEY> keyClass;

    /**
     * Constructs a base repository.
     * <p>
     * The aggregate root class and the key class are found by reflection.
     * </p>
     */
    protected BaseRepository() {
        this.aggregateRootClass = init(AGGREGATE_INDEX);
        this.keyClass = init(KEY_INDEX);
    }

    /**
     * Constructs a base repository settings explicitly the aggregate root class and the key class.
     * <p>
     * This is use when the implementation class does not resolve the generics. Since the generic
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

    private <T> Class<T> init(int index) {
        Class<? extends BaseRepository> class1 = (Class<? extends BaseRepository>) SeedReflectionUtils.cleanProxy(getClass());

        return (Class<T>) TypeResolver.resolveRawArguments(class1.getGenericSuperclass(), class1)[index];
    }

    @Override
    public Class<AGGREGATE> getAggregateRootClass() {
        return aggregateRootClass;
    }

    @Override
    public Class<KEY> getKeyClass() {
        return keyClass;
    }

    @Override
    public final AGGREGATE load(KEY id) {
        return doLoad(id);
    }

    @Override
    public void clear() {
        doClear();
    }

    @Override
    public final void delete(KEY id) {
        doDelete(id);
    }

    @Override
    public final void delete(AGGREGATE aggregate) {
        doDelete(aggregate);
    }

    @Override
    public final void persist(AGGREGATE aggregate) {
        doPersist(aggregate);
    }

    @Override
    public final AGGREGATE save(AGGREGATE aggregate) {
        return doSave(aggregate);
    }


    /**
     * Delegates the load mechanism to the infrastructure.
     *
     * @param id the identifier of the aggregate root to load
     * @return the loaded aggregate
     */
    @Read
    protected abstract AGGREGATE doLoad(KEY id);

    /**
     * Delegates the clear mechanism to the infrastructure.
     */
    @Delete
    protected abstract void doClear();

    /**
     * Delegates the delete mechanism to the infrastructure.
     *
     * @param id the identifier of the aggregate root to delete
     */
    @Delete
    protected abstract void doDelete(KEY id);

    /**
     * Delegates the delete mechanism to the infrastructure.
     *
     * @param aggregate the aggregate to delete
     */
    @Delete
    protected abstract void doDelete(AGGREGATE aggregate);

    /**
     * Delegates the persist mechanism to the infrastructure.
     *
     * @param aggregate the aggregate to persist
     */
    @Persist
    protected abstract void doPersist(AGGREGATE aggregate);

    /**
     * Delegates the save mechanism to the infrastructure.
     *
     * @param aggregate the aggregate to save
     * @return the saved aggregate
     */
    @Persist
    protected abstract AGGREGATE doSave(AGGREGATE aggregate);

}
