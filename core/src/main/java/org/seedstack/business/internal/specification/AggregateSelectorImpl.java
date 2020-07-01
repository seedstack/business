/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.IdentityPicker;

class AggregateSelectorImpl<A extends AggregateRoot<I>, I, S extends AggregateSelector<A, I, S>> extends
        PropertySelectorImpl<A, S> implements AggregateSelector<A, I, S> {

    AggregateSelectorImpl(SpecificationBuilderContext<A, S> context) {
        super(context);
    }

    @Override
    public IdentityPicker<A, I, S> identity() {
        return new IdentityPickerImpl<>(context);
    }
}
