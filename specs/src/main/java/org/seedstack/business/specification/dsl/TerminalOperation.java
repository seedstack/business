/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

import org.seedstack.business.specification.Specification;

/**
 * An element of the {@link SpecificationBuilder} DSL to build the final composite specification.
 *
 * @param <T> the type of the object the specification applies to.
 */
public interface TerminalOperation<T> {

    /**
     * Build the final composite specification as defined by previous DSL operations.
     *
     * @return the final composite specification.
     */
    Specification<T> build();
}
