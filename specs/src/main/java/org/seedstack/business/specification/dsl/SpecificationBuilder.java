/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

import org.seedstack.business.domain.AggregateRoot;

/**
 * SpecificationBuilder is a DSL that facilitates the creation of complex composite specifications. It relies on expressing
 * boolean predicates in the disjunctive normal form, which is a disjunction (OR) of conjunctive clauses (AND). In other
 * words it requires that the predicates are expressed as an OR clause of AND clauses.
 *
 * <p>
 * Examples of formulae in DNF:
 * </p>
 * <ul>
 * <li>(A ∧ ¬B ∧ ¬C) ∨ (¬D ∧ E ∧ F)</li>
 * <li>(A ∧ B) ∨ C</li>
 * <li>A ∧ B</li>
 * <li>A</li>
 * </ul>
 */
public interface SpecificationBuilder {
    /**
     * Starts the building of a composite specification that applies on any type.
     *
     * @param anyClass   the class the specification applies to.
     * @param <T>        the type of the object the specification applies to.
     * @param <SELECTOR> the type of the selector.
     * @return the next operation of the builder DSL, allowing to select all or a part of the object the specification
     * will apply to.
     */
    <T, SELECTOR extends PropertySelector<T, SELECTOR>> SELECTOR of(Class<T> anyClass);

    /**
     * Starts the building of a composite specification that applies on any {@link AggregateRoot}.
     *
     * @param aggregateClass the aggregate class the specification applies to.
     * @param <A>            the type of the aggregate the specification applies to.
     * @param <ID>           the type of the identifier of the aggregate.
     * @param <SELECTOR>     the type of the selector.
     * @return the next operation of the builder DSL, allowing to select all or a part of the object the specification
     * will apply to.
     */
    <A extends AggregateRoot<ID>, ID, SELECTOR extends AggregateSelector<A, ID, SELECTOR>> SELECTOR ofAggregate(Class<A> aggregateClass);
}
