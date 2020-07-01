/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * A value object measures, quantifies or describes something in the domain. A value object has no
 * lifecycle from the domain perspective. As such we don’t need to provide him an identity. Value
 * objects can be created and destroyed at will without any impact.
 *
 * <p> A value object is immutable, meaning that its state cannot be changed after creation. If you
 * need to change a value object, create a new one derived from the initial one. Value object
 * immutability means that they can be easily shared across the whole system. </p>
 *
 * <p> A value object describes a conceptual whole. All of its attributes are related to each other
 * and are all participating to the description of the thing. </p>
 *
 * <p> As entities, value objects should contain behavior that is relevant to them. If the domain
 * concept described by the value object has a behavior, write methods encapsulating it. This
 * behavior must remain side-effect free (not depending upon any mutable state). </p>
 *
 * <p> The {@code BaseValueObject} class can be used as a base class for domain entities. It already
 * implements the {@link #equals(Object)} and {@link #hashCode()} methods properly. </p>
 *
 * <p> Example: </p>
 * <pre>
 * public class SomeValueObject implements ValueObject {
 *     private String attribute1;
 *     private String attribute2;
 *
 *     public SomeValueObject(String attribute1, String attribute2) {
 *         this.attribute1 = attribute1;
 *         this.attribute2 = attribute2;
 *     }
 *
 *    {@literal @}Override
 *     public int hashCode() {
 *         // implement based on all attributes
 *     }
 *
 *    {@literal @}Override
 *     public boolean equals() {
 *         // implement based on all attributes
 *     }
 *
 *     // Other methods
 * }
 * </pre>
 */
@DomainValueObject
public interface ValueObject extends Producible {

    /**
     * As per Domain-Driven Design semantics, value object equality must be computed on all its
     * attributes.
     *
     * @param other other object.
     * @return true if the other object is of the same class as this value object and if all
     *         attributes are equals, false otherwise.
     */
    boolean equals(Object other);

    /**
     * As per Domain-Driven Design semantics, the hash code of a value object must be computed on all
     * its attributes.
     *
     * @return a hash code value for this value object.
     */
    int hashCode();
}
