/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * An entity represent a thread of continuity and identity, going through a lifecycle, though its
 * attributes may change. It is not defined primarily by its attributes but by its identity that
 * stays the same through time and across distinct representations.
 *
 * <p> The identity of an entity must be unique and immutable. It must be chosen carefully and well
 * defined in the model. Identification can come from: </p> <ul> <li>The outside: a user of the
 * system can provide the identity, handling the uniqueness himself.</li> <li>The inside: the entity
 * can generate its own identity using an algorithm.</li> <li>An {@link IdentityGenerator}.</li>
 * </ul>
 *
 * <p> An entity should not be merely a holder of attributes, but should also contain the behavior
 * that is directly relevant to it. Do not create entities with only getters and setters but add
 * methods with meaningful business names, implementing domain behavior. </p>
 *
 * <p> The {@code BaseEntity} class can be used as a base class for domain entities. It provides an
 * implementation of the {@link #getId()}, {@link #equals(Object)} and {@link #hashCode()} methods.
 * </p>
 *
 * <p> Example: </p>
 * <pre>
 * public class SomeEntity implements Entity&lt;SomeEntityId&gt; {
 *     private SomeEntityId id;
 *
 *     public SomeEntity(SomeEntityId id) {
 *         this.id = id;
 *     }
 *
 *    {@literal @}Override
 *     public SomeEntityId getId() {
 *         return this.id;
 *     }
 *
 *    {@literal @}Override
 *     public int hashCode() {
 *         // implement using identity attribute only
 *     }
 *
 *    {@literal @}Override
 *     public boolean equals() {
 *         // implement using identity attribute only
 *     }
 *
 *     // Other methods
 * }
 * </pre>
 *
 * @param <IdT> the type of the entity identifier.
 */
@DomainEntity
public interface Entity<IdT> {

  /**
   * Returns the identifier of this entity instance.
   *
   * @return the entity identifier.
   */
  IdT getId();

  /**
   * As per Domain-Driven Design semantics, entity equality must be computed on its identity only,
   * as returned by the {@link #getId()} method. Two entities from the same class hierarchy (with an
   * inheritance relationship between them) can be checked for equality as they have a comparable
   * identity.
   *
   * <p> When implementing this method for entities, the semantics above must be respected in
   * addition to the semantics of {@link Object#equals(Object)}. </p>
   *
   * @param other other object
   * @return true if the other object is an entity of the same class hierarchy as this entity and
   *     has an identity equal to this entity identity.
   */
  boolean equals(Object other);

  /**
   * As per Domain-Driven Design semantics, the hash code of an entity must be computed on its
   * identity only, as return by as returned by the {@link #getId()} method.
   *
   * @return a hash code value for this entity.
   */
  int hashCode();
}
