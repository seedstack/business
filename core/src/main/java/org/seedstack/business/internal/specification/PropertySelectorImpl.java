/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.seedstack.business.specification.FalseSpecification;
import org.seedstack.business.specification.TrueSpecification;
import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.PropertySelector;
import org.seedstack.business.specification.dsl.SpecificationPicker;
import org.seedstack.business.specification.dsl.TerminalOperation;

class PropertySelectorImpl<T, S extends BaseSelector<T, S>> implements PropertySelector<T, S> {

    protected final SpecificationBuilderContext<T, S> context;

    @SuppressWarnings("unchecked")
    @SuppressFBWarnings("MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR")
    PropertySelectorImpl(SpecificationBuilderContext<T, S> context) {
        this.context = context;
        this.context.setSelector((S) this);
    }

    @Override
    public TerminalOperation<T> all() {
        context.addSpecification(new TrueSpecification<>());
        return new TerminalOperationImpl<>(context);
    }

    @Override
    public TerminalOperation<T> none() {
        context.addSpecification(new FalseSpecification<>());
        return new TerminalOperationImpl<>(context);
    }

    @Override
    public SpecificationPicker<T, S> whole() {
        return new SpecificationPickerImpl<>(context);
    }

    @Override
    public SpecificationPicker<T, S> property(String path) {
        context.setProperty(path);
        return new SpecificationPickerImpl<>(context);
    }
}
