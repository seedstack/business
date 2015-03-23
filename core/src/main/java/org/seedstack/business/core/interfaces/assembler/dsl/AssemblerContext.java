/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as a shared context inside the assembler DSL.
 * <p>
 * It is populated by the DSL methods and called at the end to build
 * the final object according to the required behaviour.
 * </p>
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
class AssemblerContext {

    private boolean secured = false;

    private Class<? extends AggregateRoot<?>> aggregateClass;
    private Tuple aggregateClasses;

    // TODO <pith> : change it to <? extends ...>
    private List<AggregateRoot<?>> aggregates = new ArrayList<AggregateRoot<?>>();
    private List<? extends Tuple> aggregateTuples;
    private Tuple aggregateTuple;

    private Object dto;
    private List<?> dtos = new ArrayList<Object>();

    private Repository<?, ?> repository;

    // ---

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }



    // --- aggregate class

    // --- -- single

    public Class<? extends AggregateRoot<?>> getAggregateClass() {
        return aggregateClass;
    }

    public void setAggregateClass(Class<? extends AggregateRoot<?>> aggregateClass) {
        this.aggregateClass = aggregateClass;
    }

    // --- -- tuple

    public Tuple getAggregateClasses() {
        return aggregateClasses;
    }

    public void setAggregateClasses(Tuple aggregateClasses) {
        this.aggregateClasses = aggregateClasses;
    }



    // --- aggregate instance

    // --- -- single

    public List<AggregateRoot<?>> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<AggregateRoot<?>> aggregates) {
        this.aggregates = aggregates;
    }

    public AggregateRoot<?> getAggregate() {
        if (aggregates != null && !aggregates.isEmpty()) {
            return aggregates.get(0);
        } else {
            return null;
        }
    }

    public void setAggregate(AggregateRoot<?> aggregate) {
        this.aggregates.add(aggregate);
    }

    // --- -- tuple

    public List<? extends Tuple> getAggregateTuples() {
        return aggregateTuples;
    }

    public void setAggregateTuples(List<? extends Tuple> aggregateTuples) {
        this.aggregateTuples = aggregateTuples;
    }

    public Tuple getAggregateTuple() {
        return aggregateTuple;
    }

    public void setAggregateTuple(Tuple aggregateTuple) {
        this.aggregateTuple= aggregateTuple;
    }



    // --- dtos

    public List<?> getDtos() {
        return dtos;
    }

    public void setDtos(List<?> dtos) {
        this.dtos = dtos;
    }

    public Object getDto() {
        return this.dto;
    }

    public void setDto(Object dto) {
        this.dto = dto;
    }

    // --- repository

    public Repository<?, ?> getRepository() {
        return repository;
    }

    public void setRepository(Repository<?, ?> repository) {
        this.repository = repository;
    }
}
