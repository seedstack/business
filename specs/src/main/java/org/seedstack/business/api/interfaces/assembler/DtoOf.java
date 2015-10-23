/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;

import org.seedstack.business.api.domain.AggregateRoot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to indicate to the default assembler
 * the aggregate classes into which this dto should assemble.
 * <p>
 * This is <b>only needed for default assemblers</b>.
 * </p>
 * Usage:
 * <pre>
 * {@literal @}DtoOf(Customer.class)
 * public class CustomerDto {
 *     ...
 * }
 * </pre>
 * Then
 * <pre>
 * {@literal @}Inject
 * DefaultAssembler{@literal <Customer, CustomerDto>} customerAssembler;
 * </pre>
 *
 * or for tuple of aggregates:
 * <pre>
 * {@literal @}DtoOf(Customer.class, Order.class)
 * public class CustomerDto {
 *     ...
 * }
 * </pre>
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
public @interface DtoOf {

    /**
     * @return the aggregate classes needed to assemble this dto
     */
    Class<? extends AggregateRoot<?>>[] value();
}
