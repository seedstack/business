/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.data;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.seedstack.business.internal.utils.BusinessUtils.resolveAggregateIdClass;

import com.google.inject.assistedinject.Assisted;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.data.BaseDataImporter;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Repository;

public class DefaultDataImporter<A extends AggregateRoot<I>, I, D> extends BaseDataImporter<D> {
    private final Class<A> aggregateRootClass;
    private final Repository<A, I> repository;
    private final FluentAssembler fluentAssembler;

    @Inject
    @SuppressWarnings("unchecked")
    DefaultDataImporter(@Assisted Object[] genericClasses, DomainRegistry domainRegistry,
            FluentAssembler fluentAssembler) {
        super((Class<D>) genericClasses[0]);
        DtoOf annotation = getImportedClass().getAnnotation(DtoOf.class);
        checkNotNull(annotation, "Default data importer only supports DTO with a @DtoOf annotation");
        checkState(annotation.value().length > 0, "An aggregate must be specified in the @DtoOf annotation");
        checkState(annotation.value().length < 2, "Default data importer doesn't support tuples");
        this.aggregateRootClass = (Class<A>) annotation.value()[0];
        this.repository = domainRegistry.getRepository(aggregateRootClass, resolveAggregateIdClass(aggregateRootClass));
        this.fluentAssembler = fluentAssembler;
    }

    @Override
    public boolean isInitialized() {
        return !repository.isEmpty();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public void importData(Stream<D> data) {
        fluentAssembler.merge(data).into(aggregateRootClass).fromFactory().asStream().forEach(repository::add);
    }
}
