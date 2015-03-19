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
 * This annotation is a marker for {@link GenericFactory}.
 * <p>
 * {@link GenericFactory} is the interface to extend to create a Domain Factory interface. 
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Documented
@DomainElement
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE , ElementType.ANNOTATION_TYPE})
public @interface DomainFactory {
    
}