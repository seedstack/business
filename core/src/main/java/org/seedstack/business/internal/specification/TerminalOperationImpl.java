/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.TerminalOperation;

class TerminalOperationImpl<T, S extends BaseSelector<T, S>> implements TerminalOperation<T> {

    protected final SpecificationBuilderContext<T, S> context;

    TerminalOperationImpl(SpecificationBuilderContext<T, S> context) {
        this.context = context;
    }

    @Override
    public Specification<T> build() {
        return context.build();
    }
}
