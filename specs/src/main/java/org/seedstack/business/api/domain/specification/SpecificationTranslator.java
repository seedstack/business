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
 * Capable of translating domain specific {@link Specification} instances, into a query Criteria.
 * Translating specifications into query criteria enables us to construct, and execute, high performance
 * queries, that still capture the logic of our domain related specification.
 *
 * @param <CRITERIA> the criteria type
 * @param <BUILDER> whatever will help you build a criteria
 *
 * @author Jeroen van Schagen
 * @since 28-12-2010
 */
public interface SpecificationTranslator<CRITERIA,BUILDER> {

    /**
     * Translate some {@link Specification} into a new Criteria, enforcing the same selection criteria
     * as our provided specification. Returned predicates can only be used on the provided criteria query.
     * 
     * @param specification the specification
     * @param criteriaBuilder the criteria builder
     * @return new Criteria that enforces our specification logic
     */
    CRITERIA translateToCriteria(Specification<?> specification, BUILDER criteriaBuilder);

}
