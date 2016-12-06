/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
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
 * This annotation marks its annotated interface as a service. The implementation
 * of the annotated interface will be registered by the framework and bound to this interface.
 * <p>
 * For instance the following service:
 * </p>
 * <pre>
 * {@literal @}Service
 * public interface TransferService {
 *
 *    public void transfer(Account accountSource, Account accountTarget);
 * }
 * </pre>
 * with its implementation:
 * <pre>
 * public class TransferServiceBase implements TransferService {
 *
 *     public void transfer(Account accountSource, Account accountTarget) {
 *      ...
 *     }
 * }
 * </pre>
 * can be used as follows:
 * <pre>
 * {@literal @}Inject
 * TransferService transferService;
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Service {
}
