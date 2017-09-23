/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * A domain event is used to represent something that happened in the domain. It happened in the
 * past and is of interest to the business.
 *
 * <p> A domain event always represent something that happened in the past. Its name must be in the
 * past tense and be based upon the ubiquitous language. </p> <p> A domain event can be as little as
 * just a name. More often, it will contain values and identifiers that represent the relevant state
 * at the time the event happened. That state should be minimized and the receivers should query the
 * model to access additional information if necessary. </p>
 *
 * <p> As they represent something in the past, domain events must be immutable. As such they can
 * only contain immutable objects like value objects, primitive types, strings, etc… </p>
 *
 * <p> Domain events are first and foremost about communication, within the system but also with
 * other systems. A domain event should therefore be kept as simple as possible as be easy to
 * serialize. </p>
 *
 * <p>Example:</p>
 * <pre>
 * public class SomeDomainEvent implements DomainEvent {
 *     private String attribute1;
 *     private String attribute2;
 *
 *     public SomeDomainEvent(String attribute1, String attribute2) {
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
 *
 * @see DomainEventHandler
 * @see DomainEventPublisher
 * @see ValueObject
 */
public interface DomainEvent extends ValueObject {

}
