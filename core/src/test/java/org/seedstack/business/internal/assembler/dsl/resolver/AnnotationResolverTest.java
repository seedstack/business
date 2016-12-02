/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.fixtures.assembler.sample.Case1Dto;
import org.seedstack.business.fixtures.assembler.sample.Case2Dto;
import org.seedstack.business.fixtures.assembler.sample.Case3Dto;
import org.seedstack.business.fixtures.assembler.sample.Case4Dto;
import org.seedstack.business.fixtures.assembler.sample.CaseFail1Dto;
import org.seedstack.business.fixtures.assembler.sample.CaseFail2Dto;
import org.seedstack.business.fixtures.assembler.sample.CaseFail3Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.impl.AnnotationResolver;

import java.util.Date;

import static org.junit.Assert.fail;

/**
 * This class tests all the use cases for our old resolver algorithms.
 *
 * @see org.seedstack.business.internal.assembler.dsl.resolver.impl.AnnotationResolver
 */
public class AnnotationResolverTest {

    public static final String firstName = "john";
    public static final Date birthDate = new Date();
    public static final String lastName = "doe";

    private DtoInfoResolver underTest;
    private CaseFail1Dto caseFail1Dto;
    private CaseFail2Dto caseFail2Dto;
    private CaseFail3Dto caseFail3Dto;

    @Before
    public void setup() {
        underTest = new AnnotationResolver();

        caseFail1Dto = new CaseFail1Dto(firstName, lastName);
        caseFail2Dto = new CaseFail2Dto(firstName, lastName);
        caseFail3Dto = new CaseFail3Dto(firstName, lastName);
    }

    @Test
    public void testSimpleId() {
        ParameterHolder holder = underTest.resolveId(new Case1Dto(1, firstName));
        Assertions.assertThat(holder).isNotNull();
        Assertions.assertThat(holder.uniqueElement()).isEqualTo(1);
    }

    @Test
    public void testValueObjectId() {
        ParameterHolder holder = underTest.resolveId(new Case2Dto(firstName, birthDate));
        Assertions.assertThat(holder).isNotNull();
        Assertions.assertThat(holder.parameters()).hasSize(2);
        Assertions.assertThat(holder.parameters()).isEqualTo(new Object[]{firstName, birthDate});
    }

    @Test
    public void testSimpleIdForTuples() {
        ParameterHolder holder = underTest.resolveId(new Case3Dto(firstName, lastName));
        Assertions.assertThat(holder).isNotNull();
        Assertions.assertThat(holder.parameters()).isEmpty();
        Assertions.assertThat(holder.parametersOfAggregateRoot(0)).isEqualTo(new Object[]{firstName});
        Assertions.assertThat(holder.parametersOfAggregateRoot(1)).isEqualTo(new Object[]{lastName});
    }

    @Test
    public void testValueObjectIdForTuples() {
        ParameterHolder holder = underTest.resolveId(new Case4Dto(firstName, lastName, "oderItem", "description"));
        Assertions.assertThat(holder).isNotNull();

        Assertions.assertThat(holder.parametersOfAggregateRoot(0)).hasSize(2);
        Assertions.assertThat(holder.parametersOfAggregateRoot(0)).isEqualTo(new Object[]{firstName, lastName});

        Assertions.assertThat(holder.parametersOfAggregateRoot(1)).hasSize(2);
        Assertions.assertThat(holder.parametersOfAggregateRoot(1)).isEqualTo(new Object[]{"oderItem", "description"});
    }

    @Test
    public void testResolveIdFailingCases() {
        // Case 1: two equals type without a specified index
        try {
            underTest.resolveId(caseFail1Dto);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertThat(e).isNotNull();
        }

        // Case 2: two equals type with the same index
        try {
            underTest.resolveId(caseFail2Dto);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertThat(e).isNotNull();
        }

        // Case 3: two equals type with the same index and the same typeIndex
        try {
            underTest.resolveId(caseFail3Dto);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertThat(e).isNotNull();
        }
    }
}
