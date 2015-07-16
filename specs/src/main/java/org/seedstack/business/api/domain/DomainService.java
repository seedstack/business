/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import java.lang.annotation.*;

/**
 * This annotation marks its annotated interface as a domain service for
 * the framework.
 * <p>
 * The implementation of the annotated interface will be bound to this interface
 * in SEED.
 * <p>
 * Usage :
 *
 * <pre>
 * {@literal @}DomainService
 * public interface TransferService {
 *
 *    public void transfer(Account accountSource, Account accountTarget);
 *
 * }
 * </pre>
 *
 * The implementation should look like :
 *
 * <pre>
 * public class TransferServiceBase implements TransferService {
 *
 *     public void transfer(Account accountSource, Account accountTarget) {
 *      ...
 *     }
 * }
 * </pre>
 *
 * And now the TransferService implementation (TransferServiceBase) will be available via :
 *
 * <pre>
 *   {@literal @}Inject
 *   TransferService transferService;
 * </pre>
 *
 * Verification will be done by the framework on either or not this service is
 * in the right place.
 *
 *
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Documented
@DomainElement
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface DomainService {

}
