/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.seedstack.business.assembler.dsl.FluentAssembler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When {@link FluentAssembler} DSL needs to create an aggregate (i.e. when the {@code fromFactory()} method is used),
 * this annotation allows to specify a matching between a DTO getter and an argument of an aggregate factory method
 * by position.
 *
 * <h4>Case 1: single aggregate</h4>
 * <pre>
 * public class CustomerDto {
 *     {@literal @}FactoryArgument(index = 0)
 *     public String getName() {...}
 *
 *     {@literal @}FactoryArgument(index = 1)
 *     public Date getBirthDate() {...}
 *
 *     // No need for annotation here as the address is not part of the factory method
 *     public Address getAddress() {...}
 * }
 *
 * {@literal @}Factory
 * public class CustomerFactory {
 *     public Customer createCustomer(String name, Date birthDate);
 * }
 * </pre>
 *
 * <h4>Case 2: tuple of aggregates</h4>
 * <pre>
 * public class RecipeDto {
 *     {@literal @}FactoryArgument(aggregateIndex = 0, index = 0)
 *     public String getCustomerName() {...}
 *
 *     {@literal @}FactoryArgument(aggregateIndex = 0, index = 1)
 *     public Date getCustomerBirthDate() {...}
 *
 *     {@literal @}FactoryArgument(aggregateIndex = 1, index = 0)
 *     public int getOrderId() {...}
 * }
 *
 * {@literal @}Factory
 * public class CustomerFactory {
 *     Customer createCustomer(String name, Date birthDate);
 * }
 *
 * {@literal @}Factory
 * public class OrderFactory {
 *     Customer createOrder(int orderId);
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FactoryArgument {

    /**
     * Only used when assembling a tuple of aggregates. This specifies which aggregate in the tuple (by position) is
     * concerned by the {@link #index()} value.
     *
     * @return the concerned aggregate index in the tuple.
     */
    int aggregateIndex() default -1;

    /**
     * Specifies the position of the aggregate factory method argument the getter will match. The return value of the
     * getter will be used as the value of the corresponding factory method argument.
     *
     * @return the index of the argument to match in the factory method.
     */
    int index() default 0;

}
