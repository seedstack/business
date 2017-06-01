/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.specification.AndSpecification;
import org.seedstack.business.domain.specification.EqualSpecification;
import org.seedstack.business.spi.domain.specification.SpecificationTranslator;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import javax.inject.Named;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SeedITRunner.class)
public class SpecTranslatorIT {
    @Inject
    @Named("dummy")
    private SpecificationTranslator<SomeAggregateRoot, StringBuilder, String> dummySpecificationTranslator;

    @Test
    public void translatorIsInjectable() throws Exception {
        assertThat(dummySpecificationTranslator).isNotNull();
    }

    @Test
    public void translatorIsWorking() throws Exception {
        String result = dummySpecificationTranslator.translate(
                new AndSpecification<>(
                        new EqualSpecification<>("path1", "value1"),
                        new EqualSpecification<>("path2", "value2")
                ),
                new StringBuilder()
        );
        assertThat(result).isEqualTo("path1 == value1 && path2 == value2");
    }

    static class SomeAggregateRoot extends BaseAggregateRoot<String> {
        @Override
        public String getId() {
            return "one";
        }
    }
}
