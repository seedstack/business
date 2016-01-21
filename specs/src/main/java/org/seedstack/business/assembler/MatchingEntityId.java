/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation allows the use of the {@code fromRepository()} method of the assembler DSL. If you don't use
 * this DSL feature, this annotated is unnecessary.
 * <p>
 * It binds the DTO annotated method to the aggregate root id. If the id is a value object, it binds the method
 * to one of the constructor parameters. This allows the assembler DSL to find (or create) the aggregate root id
 * in order to load the aggregate root from the repository.
 * </p>
 * <p>
 * It also handle the case of a DTO assembled from a tuple of aggregate roots.
 * </p>
 * Case 1: Basic use case.
 * <pre>
 * public class OrderDto {
 *
 *     {@literal @}MatchingEntityId
 *     public int getId() {...}
 * }
 *
 * public class OrderAssembler extends BaseTupleAssembler&lt;Order, OrderDto&gt; { ... }
 *
 * public class Order {
 *
 *     private int orderId;
 *
 *     public Integer getEntityId() {
 *         return orderId;
 *     }
 * }
 * </pre>
 * Case 1: Basic use case.
 * <pre>
 * public class CustomerDto {
 *
 *     {@literal @}MatchingEntityId(index = 0)
 *     public String getFirstName() {...}
 *
 *     {@literal @}MatchingEntityId(index = 1)
 *     public String getLastName() {...}
 *
 *     // No need for annotation here as the birth date is not part of the customer id
 *     public Date getBirthDate() {...}
 * }
 *
 * public class RecipeAssembler extends BaseAssembler&lt;Customer, CustomerDto&gt; { ... }
 *
 * public class CustomerId {
 *     public CustomerId(String firstName, String lastName) {...}
 * }
 * </pre>
 * Case 2: The DTO is an assembly of multiple aggregates.
 * <pre>
 * public class RecipeDto {
 *
 *     {@literal @}MatchingEntityId(index = 0, typeIndex = 0)
 *     public String getCustomerFirstName() {...}
 *
 *     {@literal @}MatchingEntityId(index = 1, typeIndex = 0)
 *     public String getCustomerLastName() {...}
 *
 *     {@literal @}MatchingEntityId(typeIndex = 1) // no need for index as OrderId is not a value object
 *     public int getOrderId() {...}
 * }
 *
 * public class RecipeAssembler extends BaseTupleAssembler&lt;Pair&lt;Customer, Order&gt;, RecipeDto&gt; { ... }
 *
 * public class CustomerId {
 *     public CustomerId(String firstName, String lastName) {...}
 * }
 *
 * public class Order {
 *
 *     private int orderId;
 *
 *     public Integer getEntityId() {
 *         return orderId;
 *     }
 * }
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface MatchingEntityId {

    /**
     * If the aggregate root id is composite, i.e it is a value object, this method indicates
     * constructor parameter of the value object associated to the annotated method.
     *
     * @return the parameter index in the id constructor.
     */
	int index() default -1;

    /**
     * When using a tuple assembler, i.e. when assembling a DTO to tuple of aggregate roots.
     * This index indicates to which aggregate root this id belongs.
     *
     * @return the aggregate index
     *
     * @see BaseTupleAssembler
     */
    int typeIndex() default -1;

}
