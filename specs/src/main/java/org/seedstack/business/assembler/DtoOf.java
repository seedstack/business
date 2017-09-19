/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.seedstack.business.domain.AggregateRoot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to declare a relation between a class acting as DTO and the aggregate (or tuple of aggregates)
 * it represents. It is only required by the business framework if you don't have an explicit {@link Assembler} implementation
 * for the types involved, as it allows it to generate a default generic implementation.
 * <p>
 * Usage:
 * </p>
 * <pre>
 * {@literal @}DtoOf(Customer.class)
 *  public class CustomerDto {
 *      ...
 *  }
 * </pre>
 *
 * <p>
 * Depending upon the assembler default implementation(s) present in the classpath, a generic assembler can be injected
 * with a qualified {@link Assembler} interface:
 * </p>
 * <pre>
 * public class SomeClass {
 *    {@literal @}Inject
 *    {@literal @}SomeQualifier
 *     Assembler{@literal <Customer, CustomerDto>} customerAssembler;
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DtoOf {

    /**
     * @return the aggregate classes needed to assemble this dto
     */
    Class<? extends AggregateRoot<?>>[] value();
}
