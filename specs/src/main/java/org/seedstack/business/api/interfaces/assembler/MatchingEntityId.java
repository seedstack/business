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
 * This annotation is a marker allowing {@linkplain Assemblers} to retrieve the right AggregateRoot
 * when retrieving from the persistence. This describes the entityId.
 * <p/>
 * When defining DTO, developers can annotate methods that match with entityId with {@link MatchingEntityId}.
 * <p/>
 *  Doing this will allow {@link Assemblers} to automatically retrieve the Entity by the representation of entityId.
 * <p/>
 * This annotation serves as gateway between Aggregate Root and DTO. 
 * 
 * for doing the match between representation/dto methods 
 * to id ValueObject constructor parameters.
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface MatchingEntityId {

    /**
     * The index of the current matching field. Inside a single type.
     */
	int index() default -1;

    /**
     * In case of Tuple of Aggregate Roots, this field indicate the index number of the associated Root inside the Tuple.
     * <p/>
     * For instance, in case of
     * <pre>
     * public class UseCase1Assembler extends BaseTupleAssembler<Quartet<Activation, Customer, Order, Product>, UseCase1Representation> {
     *    ...
     * }
     * </pre>
     * A field inside a DTO that matches Customer will look like:
     * <pre>
     * {@literal @}MatchingEntityId(typeIndex=1)
     * public String getCustomer() {
     *     return customer;
     * }
     * </pre>
     *
     * @return the index of the type inside Tuple starting by 0.
     */
    int typeIndex() default -1;
}
