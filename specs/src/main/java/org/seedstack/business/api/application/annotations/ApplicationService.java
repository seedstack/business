/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.application.annotations;

import java.lang.annotation.*;

/**
 * This annotation marks its annotated interface as an application service for
 * the framework.
 * <p>
 * The implementation of the annotated interface will be bound to this interface
 * in SEED.
 * <p>
 * Usage :
 * 
 * <pre>
 * {@literal @}ApplicationService
 * public interface StockService {
 *    
 *    public Availability checkAvailability(Product p);
 * 
 * }
 * </pre>
 * 
 * The implementation should look like :
 * 
 * <pre>
 * public class StockServiceBase implements StockService {
 * 
 *     public Availability checkAvailability(Product p) {
 *      ...
 *     }
 * }
 * </pre>
 * 
 * And now the StockService implementation (StockServiceBase) will be available via :
 * 
 * <pre>
 *   {@literal @}Inject
 *   StockService stockService;
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface ApplicationService {

}
