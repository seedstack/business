/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.annotations;

import java.lang.annotation.*;

/**
 * 
 * This annotation is a marker for a domain policy interface. 
 * <p>
 * Use this annotation to annotate your Domain Policy Interface. 
 * <p>
 * Example of Domain Policy Interface is the following.
 * <pre>
 * {@literal  @}DomainPolicy
 * public interface RebatePolicy {
 * 
 *    public Money calculateRebate(Product product, int quantity, Money regularCost);
 * 
 * }
 * </pre>
 * 
 * Then developers just implements this interface 
 * 
 * 
 * 
 * and can ask injection via the inter
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Documented
@DomainElement
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
public @interface DomainPolicy {
}
