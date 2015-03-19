/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.domain;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.helpers.Repositories;
import org.seedstack.seed.core.api.SeedException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

/**
 * This class is the main containers of all {@link org.seedstack.business.api.domain.Repository}s.
 * <p/>
 * it can be injected via
 * <pre>
 * {@literal @}Inject
 * Repositories repositories;
 * </pre>
 * and uses like the following.
 * <pre>
 * ...
 * Product product = repositories.load("ean13-12");
 * ...
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 */
@SuppressWarnings("rawtypes")
public class RepositoriesInternal implements Repositories {

    @Inject
    @Named("repositoriesTypes")
    private Set<Key> repositoriesKeys;
    private Multimap<Class, Repository> repositories;

    @Inject
    private Injector injector;

    /**
     * Constructor.
     */
    public RepositoriesInternal() {
    }

    @Override
    public <T> Repository<?, ?> get(Class<T> aggregateClass) {
        Repository aggregate;

        if (repositories == null) {
            initRepositories();
        }

        aggregate = getRepository(aggregateClass);

        return aggregate;
    }

    private <T> Repository getRepository(Class<T> aggregateClass) {
        Collection<Repository> repositories = this.repositories.get(aggregateClass);
        if (repositories.size() > 1) {
            throw SeedException.createNew(RepositoriesErrorCodes.MULTIPLE_REPOSITORIES_FOUND_FOR_AGGREGATE)
                    .put("aggregate", aggregateClass.getName()).put("repositories", repositories);
        }

        Repository repository;
        if (!repositories.isEmpty()) {
            repository = repositories.iterator().next();
        } else {
            repository = null;
        }

        return repository;
    }

    @Override
    public <AGGREGATE extends AggregateRoot<KEY>, KEY> Class<KEY> getKeyClass(Class<AGGREGATE> aggregateClass) {
        Repository repo = get(aggregateClass);

        if (repo != null) {
            return repo.getKeyClass();
        } else {
            return null;
        }
    }

    //TODO PERF : see if we can create an instance if needed (get method maybe!)
    private void initRepositories() {
        repositories = ArrayListMultimap.create();
        for (Key<?> key : repositoriesKeys) {
            Repository repo = (Repository) injector.getInstance(key);
            repositories.put(repo.getAggregateRootClass(), repo);
        }
    }

    @Override
    public Set<Key> findRepositories() {
        return repositoriesKeys;
    }
}

