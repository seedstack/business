/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
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
 * This annotation allows the use of the {@code fromFactory()} method of the assembler DSL. If you don't use
 * this DSL feature, this annotated is unnecessary.
 * <p>
 * It binds the DTO's annotated method to one parameter of a factory method used to create the
 * assembled aggregate.
 * </p>
 * <p>
 * It also handle the case of a DTO assembled from a tuple of aggregate roots.
 * </p>
 * Case 1: Basic use case.
 * <pre>
 * public class CustomerDto {
 *
 *     {@literal @}MatchingFactoryParameter(index = 0)
 *     public String getName() {...}
 *
 *     {@literal @}MatchingFactoryParameter(index = 1)
 *     public Date getBirthDate() {...}
 *
 *     // No need for annotation here as the address is not part of the factory method
 *     public Address getAddress() {...}
 * }
 *
 * public class RecipeAssembler extends BaseAssembler&lt;Customer, CustomerDto&gt; { ... }
 *
 * public class CustomerFactory {
 *     public Customer createCustomer(String name, Date birthDate);
 * }
 * </pre>
 * Case 2: The DTO is an assembly of multiple aggregates.
 * <pre>
 * public class RecipeDto {
 *
 *     {@literal @}MatchingFactoryParameter(index = 0, typeIndex = 0)
 *     public String getCustomerName() {...}
 *
 *     {@literal @}MatchingFactoryParameter(index = 1, typeIndex = 0)
 *     public Date getCustomerBirthDate() {...}
 *
 *     {@literal @}MatchingFactoryParameter(index = 0, typeIndex = 1)
 *     public int getOrderId() {...}
 * }
 *
 * public class RecipeAssembler extends BaseTupleAssembler&lt;Pair&lt;Customer, Order&gt;, RecipeDto&gt; { ... }
 *
 * public class CustomerFactory {
 *     Customer createCustomer(String name, Date birthDate);
 * }
 *
 * public class OrderFactory {
 *     Customer createOrder(int orderId);
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Deprecated
public @interface MatchingFactoryParameter {

    /**
     * Indicates which factory parameter the annotated method match.
     *
     * @return the parameter index in the factory method.
     */
    int index() default -1;

    /**
     * When using a tuple assembler, i.e. when assembling a DTO to tuple of aggregate roots.
     * This index indicates for which aggregate root this factory parameter is used.
     *
     * @return the aggregate index
     */
    int typeIndex() default -1;

}