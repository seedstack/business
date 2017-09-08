/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.specification.IdentitySpecification;
import org.seedstack.business.specification.Specification;

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
 * @param <ID> the type of identifier of the aggregate root class.
 */
@DomainRepository
public interface Repository<A extends AggregateRoot<ID>, ID> {
    /**
     * Adds an aggregate to the repository.
     *
     * @param aggregate the aggregate to add.
     * @throws AggregateExistsException if the repository already contains the aggregate.
     */
    void add(A aggregate) throws AggregateExistsException;

    /**
     * Finds all aggregates in the repository satisfying the given specification. Options can be specified to
     * alter the returned stream (order, limits, ...).
     *
     * @param specification the specification aggregates must satisfy.
     * @param options       result options.
     * @return a stream of aggregates.
     */
    Stream<A> get(Specification<A> specification, Option... options);

    /**
     * Gets an aggregate identified by its identifier.
     *
     * @param id the aggregate identifier.
     * @return an optional of the corresponding aggregate.
     */
    default Optional<A> get(ID id) {
        return get(new IdentitySpecification<>(id)).findFirst();
    }

    /**
     * Check if at least one aggregate satisfying the specified specification is present in the repository.
     *
     * @param specification the specification.
     * @return true if at least one aggregate satisfying the specification is present, false otherwise.
     */
    default boolean contains(Specification<A> specification) {
        return count(specification) > 0;
    }

    /**
     * Checks that the aggregate identified by the specified identifier is present in the repository.
     *
     * @param id the aggregate identifier.
     * @return true if the aggregate is present, false otherwise.
     */
    default boolean contains(ID id) {
        return contains(new IdentitySpecification<>(id));
    }

    /**
     * Checks that the specified aggregate is present in the repository. The check is only done on the aggregate identity,
     * so this method is equivalent to calling <code>contains(aggregate.getId())</code>.
     *
     * @param aggregate the aggregate identifier.
     * @return true if the aggregate is present, false otherwise.
     */
    default boolean contains(A aggregate) {
        return contains(aggregate.getId());
    }

    /**
     * Count the number of aggregates in the repository satisfying the given specification.
     *
     * @param specification the specification aggregates must satisfy.
     * @return the number of aggregates in the repository satisfying the specification.
     */
    default long count(Specification<A> specification) {
        return get(specification).count();
    }

    /**
     * Returns the number of aggregates in the repository.
     *
     * @return the number of aggregates in the repository.
     */
    default long size() {
        return count(Specification.any());
    }

    /**
     * Return true if the repository is empty (i.e. contains no aggregate) and false otherwise
     * (i.e. contains at least one aggregate).
     *
     * @return true if repository is empty, false otherwise.
     */
    default boolean isEmpty() {
        return size() == 0L;
    }

    /**
     * Removes all aggregates in the repository satisfying the given specification.
     *
     * @param specification the specification aggregates must satisfy.
     * @return the number of aggregates removed from the repository.
     * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
     */
    long remove(Specification<A> specification) throws AggregateNotFoundException;

    /**
     * Removes the existing aggregate identified with the specified identifier.
     *
     * @param id the identifier of the aggregate to remove.
     * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
     */
    default void remove(ID id) throws AggregateNotFoundException {
        long removedCount = remove(new IdentitySpecification<>(id));
        if (removedCount == 0L) {
            throw new AggregateNotFoundException("Non-existent aggregate " + getAggregateRootClass().getSimpleName() + " identified with " + id + " cannot be removed");
        } else if (removedCount > 1L) {
            throw new IllegalStateException("More than one aggregate " + getAggregateRootClass().getSimpleName() + " identified with " + id + " have been removed");
        }
    }

    /**
     * Removes the specified aggregate from the repository.
     *
     * @param aggregate the aggregate to remove.
     * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
     */
    default void remove(A aggregate) throws AggregateNotFoundException {
        remove(aggregate.getId());
    }

    /**
     * Updates an existing aggregate with the specified instance.
     *
     * @param aggregate the aggregate to update.
     * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
     */
    default void update(A aggregate) throws AggregateNotFoundException {
        remove(aggregate);
        add(aggregate);
    }

    /**
     * Removes all aggregates from the repository.
     */
    default void clear() {
        remove(Specification.any());
    }

    /**
     * @return the aggregate root class.
     */
    Class<A> getAggregateRootClass();

    /**
     * @return the aggregate key.
     */
    Class<ID> getIdentifierClass();

    interface Option {

    }
}