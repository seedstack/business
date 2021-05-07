/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A service is a stateless object that implements domain, applicative, infrastructure or interface
 * logic.
 *
 * <p> This annotation can be applied to an interface to declare a service. Any class implementing
 * the annotated interface will be registered by the framework as an implementation of this service.
 * </p>
 *
 * <p> Considering the following service: </p>
 * <pre>
 * {@literal @}Service
 *  public interface MoneyTransferService {
 *     void transfer(Account source, Account target);
 *  }
 * </pre>
 * Its implementation is declared as:
 * <pre>
 *  public class ElectronicMoneyTransferService implements MoneyTransferService {
 *      public void transfer(Account source, Account target) {
 *          // implementation
 *      }
 *  }
 * </pre>
 * The implementation can then be injected as follows:
 * <pre>
 * public class SomeClass {
 *     {@literal @}Inject
 *      MoneyTransferService moneyTransferService;
 * }
 * </pre>
 *
 * <p> When a service interface has multiple implementations, it is necessary to differentiate them
 * by using a different {@code javax.inject.Qualifier} annotation on each. This qualifier can then
 * be used at the injection point to specify which implementation is required. Considering the
 * additional implementation of the MoneyTransferService below: </p>
 * <pre>
 * {@literal @}Named("solidGold")
 *  public class SolidGoldMoneyTransferService implements MoneyTransferService {
 *      public void transfer(Account source, Account target) {
 *          // implementation
 *      }
 *  }
 * </pre>
 * <p> This implementation can be injected as follows: </p>
 * <pre>
 *  public class SomeClass {
 *      {@literal @}Inject
 *      {@literal @}Named("solidGold")
 *       TransferService transferService;
 *  }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Service {

}
