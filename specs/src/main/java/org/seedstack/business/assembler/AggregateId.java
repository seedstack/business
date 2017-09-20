/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.seedstack.business.assembler.dsl.FluentAssembler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When {@link FluentAssembler} DSL needs to get an aggregate from repository (i.e. when the {@code fromRepository()}
 * method is used), this annotation allows to specify a matching between a DTO getter and the identifier of the aggregate.
 *
 * <h3>Example 1: single aggregate, simple identifier</h3>
 * <pre>
 * public class OrderDto {
 *    {@literal @}AggregateId
 *     public String getId() {...}
 * }
 *
 * public class Order extends BaseAggregateRoot&lt;String&gt; {
 * }
 * </pre>
 *
 * <h3>Example 2: single aggregate, composite identifier</h3>
 * <pre>
 * public class CustomerDto {
 *    {@literal @}AggregateId(index = 0)
 *     public String getFirstName() {...}
 *
 *    {@literal @}AggregateId(index = 1)
 *     public String getLastName() {...}
 *
 *     // No annotation here as the birth date is not part of the customer id
 *     public Date getBirthDate() {...}
 * }
 *
 * public class CustomerId extends BaseValueObject {
 *     public CustomerId(String firstName, String lastName) {...}
 * }
 *
 * public class Customer extends BaseAggregateRoot&lt;CustomerId&gt; {
 * }
 * </pre>
 *
 * <h3>Example 3: tuple of aggregates, one with composite identifier, the other with simple identifier</h3>
 * <pre>
 * public class RecipeDto {
 *    {@literal @}MatchingEntityId(aggregateIndex = 0, index = 0)
 *     public String getCustomerFirstName() {...}
 *
 *    {@literal @}MatchingEntityId(aggregateIndex = 0, index = 1)
 *     public String getCustomerLastName() {...}
 *
 *    {@literal @}MatchingEntityId(aggregateIndex = 1)
 *     public int getOrderId() {...}
 * }
 *
 * public class CustomerId extends BaseValueObject {
 *     public CustomerId(String firstName, String lastName) {...}
 * }
 *
 * public class Customer extends BaseAggregateRoot&lt;CustomerId&gt; {
 * }
 *
 * public class Order extends BaseAggregateRoot&lt;Integer&gt; {
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface AggregateId {

    /**
     * When using a tuple assembler, i.e. when assembling a DTO to tuple of aggregate roots.
     * This index indicates to which aggregate root this id belongs.
     *
     * @return the aggregate index
     */
    int aggregateIndex() default -1;

    /**
     * If the aggregate root id is composite, i.e it is a value object, this method indicates
     * constructor parameter of the value object associated to the annotated method.
     *
     * @return the parameter index in the id constructor.
     */
    int index() default -1;

}
