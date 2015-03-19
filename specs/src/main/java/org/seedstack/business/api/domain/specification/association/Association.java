/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.api.domain.specification.association;

import org.seedstack.business.api.domain.specification.NamedSpecification;


/**
 * Association.
 *
 * @author redouane.loulou@ext.mpsa.com
 */
public interface Association<LT, RT> {

    /**
     * Sees if an object satisfy all the requirements expressed in this specification association.
     *
     * @param typeToAssociate     the type to associate
     * @param typeToAssociateWith the type to associate with
     * @return {@code true} if the requirements are satisfied, otherwise {@code false}
     */
    boolean isSatisfiedBy(LT typeToAssociate, RT typeToAssociateWith);

    /**
     * Gets the name of the field to associate. For instance if we associate two object A and B with
     * a.key -> b.referenceKey. The field to associate with will be "referenceKey".
     *
     * @return the field to associate with
     */
    String getFieldToAssociateWith();

    /**
     * Gets the first specification.
     *
     * @return a named specification
     */
    NamedSpecification<LT> getNamedSpecToAssociate();

    /**
     * Gets the specification to associate with the first one.
     *
     * @return a named specification
     */
    NamedSpecification<RT> getNamedSpecToAssociateWith();

}
