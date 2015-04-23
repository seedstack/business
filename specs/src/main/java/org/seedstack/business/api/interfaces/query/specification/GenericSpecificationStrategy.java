/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.query.specification;

import org.kametic.specifications.Specification;

import java.util.List;

/**
 * GenericSpecificationFinder
 *
 * @param <E> the type of candidates being checked
 * @param <R> the type of result
 * @author redouane.loulou@ext.mpsa.com
 */
public interface GenericSpecificationStrategy<R, E> {

    /**
     * Retrieve all entities that match a specification.
     *
     * @param specification the specification
     * @param option        the specification option
     * @return List of representation
     */
    List<R> matching(Specification<E> specification, SpecificationOption option);

    /**
     * Counts how many entities are matching the given specification.
     *
     * @param specification the specification
     * @param option        the specification option
     * @return the result size
     */
    long howMany(Specification<E> specification, SpecificationOption option);

    /**
     * Determine if any of our entities match a specification.
     *
     * @param specification the specification
     * @param option        the specification option
     * @return true if the result are not empty, false otherwise
     */
    boolean containsAny(Specification<E> specification, SpecificationOption option);

}
