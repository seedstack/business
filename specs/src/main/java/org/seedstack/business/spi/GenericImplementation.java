/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used on an implementation of a
 * {@link org.seedstack.business.domain.Repository}
 * or an {@link org.seedstack.business.assembler.Assembler} to declare it as a generic
 * implementation.
 *
 * <p> Generic implementations are able to work with all types satisfying the conditions of their
 * interface. For instance a generic implementation of a
 * {@link org.seedstack.business.domain.Repository}
 * must be able to work with any aggregate in the system. </p>
 *
 * <p> A generic implementation often exist along with user-defined explicit implementations of the
 * same interface, so it is recommended to annotate it with a {@code javax.inject.Qualifier},
 * leaving the unqualified implementation for user code.
 *
 * A generic implementation is instantiated through assisted injection, invoking a constructor whose
 * unique parameter must be an array of the classes it will work on. Consider the following example:
 * </p>
 *
 * <pre>
 * {@literal @}GenericImplementation
 * {@literal @}SomeQualifier
 *  public class SomeGenericRepository&lt;A extends AggregateRoot&lt;ID&gt;, ID&gt; implements
 * Repository&lt;A, ID&gt; {
 *      {@literal @}Inject
 *       public SomeGenericRepository({@literal @}Assisted Object[] genericClasses) {
 *           // genericClasses contains the aggregate root class and the identifier class
 *           // this instance must work with
 *       }
 *  }
 * </pre>
 *
 * <p> This generic implementation can be injected as follows: </p>
 * <pre>
 * public class SomeClass {
 *     {@literal @}Inject
 *     {@literal @}SomeQualifier
 *      private Repository&lt;SomeAggregate, SomeId&gt; someAggregateRepository;
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface GenericImplementation {

}
