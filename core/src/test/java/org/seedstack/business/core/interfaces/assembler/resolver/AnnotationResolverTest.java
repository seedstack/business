/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.resolver;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.api.interfaces.assembler.resolver.DtoInfoResolver;
import org.seedstack.business.api.interfaces.assembler.resolver.ParameterHolder;
import org.seedstack.business.core.interfaces.assembler.resolver.sample.*;

import java.util.Date;

import static org.junit.Assert.fail;

/**
 * This class tests all the use cases for our old resolver algorithms.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 * @see AnnotationResolver
 */
public class AnnotationResolverTest {

    public static final String firstName = "john";
    public static final Date birthDate = new Date();
    public static final String lastName = "doe";

    private DtoInfoResolver underTest;
    private Case1Dto case1Dto;
    private Case2Dto case2Dto;
    private Case3Dto case3Dto;
    private Case4Dto case4Dto;
    private CaseFail1Dto caseFail1Dto;
    private CaseFail2Dto caseFail2Dto;
    private CaseFail3Dto caseFail3Dto;

    @Before
    public void setup() {
        underTest = new AnnotationResolver();

        case1Dto = new Case1Dto(1, firstName);
        case2Dto = new Case2Dto(firstName, birthDate);
        case3Dto = new Case3Dto(firstName, lastName);
        case4Dto = new Case4Dto(firstName, lastName);
        caseFail1Dto = new CaseFail1Dto(firstName, lastName);
        caseFail2Dto = new CaseFail2Dto(firstName, lastName);
        caseFail3Dto = new CaseFail3Dto(firstName, lastName);
    }

    @Test
    public void testResolveId() {
        SoftAssertions softAssertions = new SoftAssertions();

        ParameterHolder id1 = underTest.resolveId(case1Dto);
        softAssertions.assertThat(id1).as("ResolveId() - Cas 1: the id correspond to one field").isNotNull();
        softAssertions.assertThat(id1.first()).as("Cas 1:").isEqualTo(1);

        // TODO: case 2 is useless - delete it or make it work without specifying the index

        ParameterHolder id2 = underTest.resolveId(case2Dto);
        softAssertions.assertThat(id2).as("ResolveId() - Cas 2: the id is composite").isNotNull();
        softAssertions.assertThat(id2.parameters()).as("Cas 2").isEqualTo(new Object[]{firstName, birthDate});

        ParameterHolder id3 =  underTest.resolveId(case3Dto);
        softAssertions.assertThat(id3).as("ResolveId() - Cas 3: the id is composite and have elements of same type").isNotNull();
        softAssertions.assertThat(id3.parameters()).as("Cas 3").isEqualTo(new Object[]{firstName, lastName});

        ParameterHolder id4 = underTest.resolveId(case4Dto);
        softAssertions.assertThat(id4).as("ResolveId() - Cas 4: the id is composite and contains a value object").isNotNull();
        softAssertions.assertThat(id4.parameters()[0]).as("Cas 4").isNotNull();
        //noinspection unchecked
        Pair<String, String> name = (Pair<String, String>) id4.first();
        softAssertions.assertThat(name.getValue0()).as("Cas 4").isEqualTo(firstName);
        softAssertions.assertThat(name.getValue1()).as("Cas 4").isEqualTo(lastName);

        softAssertions.assertAll();
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
