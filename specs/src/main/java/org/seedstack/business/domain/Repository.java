/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import java.util.Optional;
import java.util.stream.Stream;
import org.seedstack.business.specification.IdentitySpecification;
import org.seedstack.business.specification.Specification;

/**
 * A repository is responsible for consistently storing and retrieving a whole aggregate. It has a
 * simple collection-like global interface and optionally domain-specific methods.
 *
 * <p> A repository is responsible for storing and retrieve a whole aggregate. It manipulates the
 * aggregate through its root entity. It cannot directly store or retrieve parts of the aggregate.
 * </p>
 *
 * <p> A repository provides the illusion of an in-memory collection of all objects that are of the
 * corresponding aggregate root type. </p>
 *
 * <p> A repository implements a well-known interface that provides methods for adding, removing and
 * querying objects. </p>
 *
 * <p> A repository optionally implements methods that select objects based on criteria meaningful
 * to domain experts. Those methods return fully instantiated objects or collections of objects
 * whose attribute values meet the criteria. </p>
 *
 * <p> Example: </p>
 * <pre>
 * public interface SomeRepository extends Repository&lt;SomeAggregate, SomeId&gt; {
 *     // Optional business-meaningful methods
 *     List&lt;SomeAggregate&gt; objectsByCategory(String category);
 * }
 *
 * public class SomeJpaRepository implements SomeRepository {
 *    {@literal @}Override
 *     public List&lt;SomeAggregate&gt; objectsByCategory(String category) {
 *         // implement specific query
 *     }
 * }
 * </pre>
 *
 * @param <AggregateRootT> the type of the aggregate root class.
 * @param <IdT>            the type of identifier of the aggregate root class.
 */
@DomainRepository
public interface Repository<AggregateRootT extends AggregateRoot<IdT>, IdT> {

  /**
   * Adds an aggregate to the repository.
   *
   * @param aggregate the aggregate to add.
   * @throws AggregateExistsException if the repository already contains the aggregate.
   */
  void add(AggregateRootT aggregate) throws AggregateExistsException;

  /**
   * Finds all aggregates in the repository satisfying the given specification. Options can be
   * specified to alter the returned stream (order, limits, ...).
   *
   * @param specification the specification aggregates must satisfy.
   * @param options       result options.
   * @return a stream of aggregates.
   */
  Stream<AggregateRootT> get(Specification<AggregateRootT> specification, Option... options);

  /**
   * Gets an aggregate identified by its identifier.
   *
   * @param id the aggregate identifier.
   * @return an optional of the corresponding aggregate.
   */
  default Optional<AggregateRootT> get(IdT id) {
    return get(new IdentitySpecification<>(id)).findFirst();
  }

  /**
   * Check if at least one aggregate satisfying the specified specification is present in the
   * repository.
   *
   * @param specification the specification.
   * @return true if at least one aggregate satisfying the specification is present, false
   *   otherwise.
   */
  default boolean contains(Specification<AggregateRootT> specification) {
    return count(specification) > 0;
  }

  /**
   * Checks that the aggregate identified by the specified identifier is present in the repository.
   *
   * @param id the aggregate identifier.
   * @return true if the aggregate is present, false otherwise.
   */
  default boolean contains(IdT id) {
    return contains(new IdentitySpecification<>(id));
  }

  /**
   * Checks that the specified aggregate is present in the repository. The check is only done on the
   * aggregate identity, so this method is equivalent to calling <code>contains(aggregate.getId()
   * )</code>.
   *
   * @param aggregate the aggregate identifier.
   * @return true if the aggregate is present, false otherwise.
   */
  default boolean contains(AggregateRootT aggregate) {
    return contains(aggregate.getId());
  }

  /**
   * Count the number of aggregates in the repository satisfying the given specification.
   *
   * @param specification the specification aggregates must satisfy.
   * @return the number of aggregates in the repository satisfying the specification.
   */
  default long count(Specification<AggregateRootT> specification) {
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
   * Return true if the repository is empty (i.e. contains no aggregate) and false otherwise (i.e.
   * contains at least one aggregate).
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
  long remove(Specification<AggregateRootT> specification) throws AggregateNotFoundException;

  /**
   * Removes the existing aggregate identified with the specified identifier.
   *
   * @param id the identifier of the aggregate to remove.
   * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
   */
  default void remove(IdT id) throws AggregateNotFoundException {
    long removedCount = remove(new IdentitySpecification<>(id));
    if (removedCount == 0L) {
      throw new AggregateNotFoundException(
        "Non-existent aggregate " + getAggregateRootClass().getSimpleName() + " identified with "
          + id + " cannot be removed");
    } else if (removedCount > 1L) {
      throw new IllegalStateException(
        "More than one aggregate " + getAggregateRootClass().getSimpleName() + " identified with "
          + id + " have been removed");
    }
  }

  /**
   * Removes the specified aggregate from the repository.
   *
   * @param aggregate the aggregate to remove.
   * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
   */
  default void remove(AggregateRootT aggregate) throws AggregateNotFoundException {
    remove(aggregate.getId());
  }

  /**
   * Updates an existing aggregate with the specified instance.
   *
   * @param aggregate the aggregate to update.
   * @throws AggregateNotFoundException if the repository doesn't contain the aggregate.
   */
  default void update(AggregateRootT aggregate) throws AggregateNotFoundException {
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
   * Returns the aggregate root class managed by the repository.
   *
   * @return the aggregate root class.
   */
  Class<AggregateRootT> getAggregateRootClass();

  /**
   * Returns the aggregate root identifier class managed by the repository.
   *
   * @return the aggregate root identifier.
   */
  Class<IdT> getIdentifierClass();

  interface Option {

  }
}