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
 * Specifies the conversion logic of a association into a criteria.
 * 
 * @param <CRITERIA> the criteria type
 * @param <A> type of specification being converted
 * @param <BUILDER> whatever will help you build a criteria
 * 
 * @author Jeroen van Schagen
 * @since 28-12-2010
 */
public interface AssociationConverter<CRITERIA,A extends Association<?,?>, BUILDER> {

    /**
     * Convert some association into a criteria.
     * The returned predicate enforces a selection criteria on only entities that satisfy
     * the specification's business rules.
     * 
     * @param association the association
     * @param builder criteria builder, used to construct criteria
     * @return our constructed criteria, enforcing only matching entities to be selected
     */
    CRITERIA convertToCriteria(A association, BUILDER builder);

}
