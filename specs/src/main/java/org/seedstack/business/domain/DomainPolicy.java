/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
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
 * A policy is used to encapsulate a varying business rule or process in a separate object. Multiple
 * versions of the policy object represent different ways the process can be done.
 *
 * <p> This annotation can be applied to an interface to declare a domain policy. Any class
 * implementing the annotated interface will be registered by the framework as an implementation of
 * this policy. </p>
 *
 * <p> When a policy interface has multiple implementations, it is necessary to differentiate them
 * by using a different {@code javax.inject.Qualifier} annotation on each. This qualifier can then
 * be used at the injection point to specify which implementation is required. </p>
 *
 * <p> Considering the following policy: </p>
 * <pre>
 * {@literal @}DomainPolicy
 *  public interface SomePolicy {
 *     double computeSomething(String someParameter);
 *  }
 * </pre>
 * Two different implementations can be declared as follows:
 * <pre>
 * {@literal @}Named("variantA")
 *  public class SomePolicyVariantA {
 *     {@literal @}Override
 *      public double computeSomething(String someParameter) {
 *          // implement variant A logic
 *      }
 *  }
 *
 * {@literal @}Named("variantB")
 *  public class SomePolicyVariantB {
 *     {@literal @}Override
 *      public double computeSomething(String someParameter) {
 *          // implement variant B logic
 *      }
 *  }
 * </pre>
 * The "B variant" implementation can then be injected as follows:
 * <pre>
 * public class SomeClass {
 *     {@literal @}Inject
 *     {@literal @}Named("variantB")
 *      SomePolicy somePolicy;
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface DomainPolicy {

}
