/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.specification;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.BaseSpecificationTranslator;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.spi.GenericImplementation;

import javax.inject.Inject;
import javax.inject.Named;

@Named("dummy")
@GenericImplementation
public class DefaultDummySpecificationTranslator<A extends AggregateRoot<?>> extends BaseSpecificationTranslator<A, StringBuilder, String> {
    @Inject
    @SuppressWarnings("unchecked")
    public DefaultDummySpecificationTranslator(@Assisted Object[] genericClasses) {
        super(
                (Class<A>) genericClasses[0],
                (Class<StringBuilder>) genericClasses[1],
                (Class<String>) genericClasses[2]
        );
    }

    @Override
    public String translate(Specification<A> specification, StringBuilder stringBuilder) {
        return convert(specification, new StringBuilder());
    }
}
