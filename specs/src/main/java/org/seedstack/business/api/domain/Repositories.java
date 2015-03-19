/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import com.google.inject.Key;

import java.util.Set;

/**
 * This class is the main containers of all {@link Repository}s.
 * <p>
 * it can be injected via
 * <pre>
 * {@literal @}Inject
 * Repositories repositories;
 * </pre>
 * and uses like the following.
 * <pre>
 * ...
 * Repository{@literal <MyAggregateRoot, MyKey>} repository = repositories.get(MyAggregate.class);
 * ...
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 */
public interface Repositories {

    /**
     * Gets the repository corresponding to the aggregate class.
     *
     * @param <T> the aggregate type
     * @param aggregateClass 
     * @return the repository
     */
    @SuppressWarnings("rawtypes")
	<T> Repository get(Class<T> aggregateClass);

    /**
     * Gets the key class corresponding to the aggregate class.
     *
     * @param aggregateClass the aggregate class
     * @param <AGGREGATE>    the aggregate type
     * @param <KEY>          the key type
     * @return the key class
     */
    <AGGREGATE extends AggregateRoot<KEY>,KEY> Class<KEY> getKeyClass(Class<AGGREGATE> aggregateClass);

    /**
     * Finds the repository classes.
     *
     * @return the set of repository classes
     */
    @SuppressWarnings("rawtypes")
	Set<Key> findRepositories();
}
