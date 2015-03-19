/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.specification.association;



/**
 * Capable of translating domain specific association instances, into a query Criteria.
 * Translating specifications into query criteria enables us to construct, and execute, high performance
 * queries, that still capture the logic of our domain related specification.
 */
public interface AssociationTranslator<CRITERIA,BUILDER> {

    /**
     * Translate some association into a new Criteria, enforcing the same selection criteria
     * as our provided specification. Returned predicates can only be used on the provided criteria query.
     * @param association the association to translate
     * @param criteriaBuilder instances used to construct new criteria
     * @return new Criteria that enforces our specification logic
     */
    CRITERIA translateToCriteria(Association<?,?> association, BUILDER criteriaBuilder);

}
