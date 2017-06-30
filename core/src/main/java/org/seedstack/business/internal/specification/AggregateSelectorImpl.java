/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.IdentityPicker;

class AggregateSelectorImpl<A extends AggregateRoot<ID>, ID, SELECTOR extends AggregateSelector<A, ID, SELECTOR>> extends BaseSelectorImpl<A, SELECTOR> implements AggregateSelector<A, ID, SELECTOR> {
    AggregateSelectorImpl(SpecificationBuilderContext<A, SELECTOR> context) {
        super(context);
    }

    @Override
    public IdentityPicker<A, ID, SELECTOR> identity() {
        return new IdentityPickerImpl<>(context);
    }
}
