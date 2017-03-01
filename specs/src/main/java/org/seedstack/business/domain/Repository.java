/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.domain.specification.Specification;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This interface has to be extended in order to create a Domain Repository <em>interface</em>.
 * <p>
 * To be a valid repository interface, Type must respect the followings:
 * </p>
 * <ul>
 * <li>be an interface</li>
 * <li>extends {@link Repository}</li>
 * </ul>
 * The following is a valid Domain repository interface.
 * <pre>
 *  public interface ProductRepository extends Repository&lt;Product,String&gt; {
 *     // nothing needed, but you can add methods with specifics
 *     // then implements them
 *  }
 * </pre>
 * <p>
 * Then this interface has to be implemented by the actual repository implementation.
 * </p>
 *
 * @param <A>  the type of the aggregate root class.
 * @param <ID> the type of the aggregate root class.
 */
@DomainRepository
public interface Repository<A extends AggregateRoot<ID>, ID> {

    /**
     * Gets an aggregate identified by its identifier.
     *
     * @param id the aggregate identifier.
     * @return an optional of the corresponding aggregate.
     */
    @Read
    Optional<A> get(ID id);

    /**
     * Finds all aggregates in the repository satisfying the given specification. Options can be specified to
     * alter the returned stream (order, limits, ...).
     *
     * @param specification the specification aggregates must satisfy.
     * @param options       result options.
     * @return a stream of aggregates.
     */
    @Read
    Stream<A> get(Specification<A> specification, RepositoryOptions... options);

    /**
     * Checks that the aggregate identified by the specified identifier exists in the repository.
     *
     * @param id the aggregate identifier.
     * @return true if the aggregate exists, false otherwise.
     */
    boolean contains(ID id);


    /**
     * Count the number of aggregates in the repository satisfying the given specification.
     *
     * @param specification the specification aggregates must satisfy.
     */
    long count(Specification<A> specification);

    /**
     * Returns the number of aggregates in the repository.
     *
     * @return the number of aggregates in the repository.
     */
    long count();

    /**
     * Adds an aggregate to the repository.
     *
     * @param aggregate the aggregate to add.
     */
    @Persist
    void add(A aggregate);

    /**
     * Replaces an existing aggregate with the specified aggregate. Identifiers for both aggregates must match.
     *
     * @param aggregate the updated aggregate.
     * @return the updated aggregate.
     */
    @Persist
    A update(A aggregate);

    /**
     * Removes the specified aggregate from the repository.
     *
     * @param aggregate the aggregate to remove.
     */
    @Delete
    void remove(A aggregate);

    /**
     * Removes the existing aggregate identified with the specified identifier.
     *
     * @param id the identifier of the aggregate to remove.
     */
    @Delete
    void remove(ID id);

    /**
     * Removes all aggregates in the repository satisfying the given specification.
     *
     * @param specification the specification aggregates must satisfy.
     */
    @Delete
    long remove(Specification<A> specification);


    /**
     * Removes all aggregates from the repository.
     */
    @Delete
    void clear();

    /**
     * @return the aggregate root class.
     */
    Class<A> getAggregateRootClass();

    /**
     * @return the aggregate key.
     */
    Class<ID> getIdentifierClass();
}