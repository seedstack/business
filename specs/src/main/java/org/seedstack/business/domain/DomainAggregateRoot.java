/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marks a type as an aggregate root. This is the minimal element to apply on a class to make the business framework
 * recognize it as an aggregate root.
 *
 * <p>
 * For better semantics it is recommended to use the {@link AggregateRoot} interface which is already annotated
 * with {@link DomainAggregateRoot}.
 * </p>
 *
 * @see AggregateRoot
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface DomainAggregateRoot {
}
