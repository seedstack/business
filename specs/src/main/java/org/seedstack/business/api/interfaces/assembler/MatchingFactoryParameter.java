/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation serves for doing the match between representation/dto methods
 * to factory create method parameters. 
 *  
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface MatchingFactoryParameter {

    /**
     * @return the parameter index in the factory method.
     */
	int index() default -1;

    /**
     * When using a BaseTupleAssembler, this type index is used to indicate to in
     *
     * @return the entity index in the tuple.
     *
     * @see BaseTupleAssembler
     */
    int typeIndex () default -1;
}
