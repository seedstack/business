/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.specification;

/**
 * In computer programming, the specification pattern is a particular software design
 * pattern, whereby business logic can be recombined by chaining the business logic
 * together using boolean logic.
 * 
 * @author Jeroen van Schagen
 * @since 23-12-2010
 *
 * @param <T> type of candidates being checked
 */
public interface Specification<T> {

    /**
     * See if an object satisfies all the requirements expressed in this specification.
     * @param candidate the object being verified
     * @return {@code true} if the requirements are satisfied, otherwise {@code false}
     */
    boolean isSatisfiedBy(T candidate);

}
