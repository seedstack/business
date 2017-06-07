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
 * @param <ID> the type of the aggregate root class.
 */
@DomainRepository
public interface Repository<A extends AggregateRoot<ID>, ID> {
    /**
     * Adds an aggregate to the repository.
     *
     * @param aggregate the aggregate to add.
     */
    @Persist
    void add(A aggregate);

    /**
     * Finds all aggregates in the repository satisfying the given specification. Options can be specified to
     * alter the returned stream (order, limits, ...).
     *
     * @param specification the specification aggregates must satisfy.
     * @param options       result options.
     * @return a stream of aggregates.
     */
    @Read
    Stream<A> get(Specification<A> specification, Options... options);

    /**
     * Gets an aggregate identified by its identifier.
     *
     * @param id the aggregate identifier.
     * @return an optional of the corresponding aggregate.
     */
    @Read
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
        return get(specification).findFirst().isPresent();
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
     */
    @Delete
    long remove(Specification<A> specification);

    /**
     * Removes the existing aggregate identified with the specified identifier.
     *
     * @param id the identifier of the aggregate to remove.
     */
    @Delete
    default boolean remove(ID id) {
        long removedCount = remove(new IdentitySpecification<>(id));
        if (removedCount > 1L) {
            throw new IllegalStateException("More than one aggregate has been removed");
        }
        return removedCount == 1L;
    }

    /**
     * Removes the specified aggregate from the repository.
     *
     * @param aggregate the aggregate to remove.
     */
    @Delete
    default boolean remove(A aggregate) {
        return remove(aggregate.getId());
    }

    /**
     * Replaces an existing aggregate with the specified aggregate.
     *
     * @param aggregate the updated aggregate.
     */
    @Persist
    default void update(A aggregate) {
        Optional<A> found = get(aggregate.getId());
        if (found.isPresent()) {
            if (remove(found.get())) {
                add(aggregate);
            }
        }
    }

    /**
     * Removes all aggregates from the repository.
     */
    @Delete
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

    interface Options {

    }
}