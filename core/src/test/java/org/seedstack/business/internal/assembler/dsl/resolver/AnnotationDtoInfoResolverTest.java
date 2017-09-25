/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl.resolver;

import static junit.framework.TestCase.fail;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.domain.BaseFactory;
import org.seedstack.business.domain.BaseValueObject;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.Case1Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.Case2Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.Case3Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.Case4Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.CaseFail1Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.CaseFail2Dto;
import org.seedstack.business.internal.assembler.dsl.resolver.annotated.CaseFail3Dto;
import org.seedstack.business.spi.DtoInfoResolver;

public class AnnotationDtoInfoResolverTest {

  public static final String firstName = "john";
  public static final Date birthDate = new Date();
  public static final String lastName = "doe";

  private DtoInfoResolver underTest;
  private DomainRegistry domainRegistry;
  private CaseFail1Dto caseFail1Dto;
  private CaseFail2Dto caseFail2Dto;
  private CaseFail3Dto caseFail3Dto;

  @Before
  public void setup() {
    domainRegistry = mock(DomainRegistry.class);
    underTest = new AnnotationDtoInfoResolver();
    Whitebox.setInternalState(underTest, "domainRegistry", domainRegistry);

    caseFail1Dto = new CaseFail1Dto(firstName, lastName);
    caseFail2Dto = new CaseFail2Dto(firstName, lastName);
    caseFail3Dto = new CaseFail3Dto(firstName, lastName);
  }

  @Test
  public void testSimpleId() {
    Integer id = underTest.resolveId(new Case1Dto(1, firstName), Integer.class);
    assertThat(id).isNotNull();
    assertThat(id).isEqualTo(1);
  }

  @Test
  public void testValueObjectId() {
    when(domainRegistry.getFactory(Case2DtoVO.class)).thenReturn(new Case2DtoVOFactory());

    Case2DtoVO id = underTest.resolveId(new Case2Dto(firstName, birthDate), Case2DtoVO.class);
    assertThat(id).isNotNull();
    assertThat(id).isEqualTo(new Case2DtoVO(firstName, birthDate));
  }

  @Test
  public void testSimpleIdForTuples() {
    Case3Dto case3Dto = new Case3Dto(firstName, lastName);
    String id1 = underTest.resolveId(case3Dto, String.class, 0);
    String id2 = underTest.resolveId(case3Dto, String.class, 1);
    assertThat(id1).isEqualTo(firstName);
    assertThat(id2).isEqualTo(lastName);
  }

  @Test
  public void testValueObjectIdForTuples() {
    when(domainRegistry.getFactory(Case4DtoVO.class)).thenReturn(new Case4DtoVOFactory());
    Case4Dto case4Dto = new Case4Dto(firstName, lastName, "oderItem", "description");
    assertThat(underTest.resolveId(case4Dto, Case4DtoVO.class, 0))
        .isEqualTo(new Case4DtoVO(firstName, lastName));
    assertThat(underTest.resolveId(case4Dto, Case4DtoVO.class, 1))
        .isEqualTo(new Case4DtoVO("oderItem", "description"));
  }

  @Test
  public void testResolveIdFailingCases() {
    // Case 1: two equals type without a specified index
    try {
      underTest.resolveId(caseFail1Dto, String.class);
      fail();
    } catch (BusinessException e) {
      assertThat(e).isNotNull();
    }

    // Case 2: two equals type with the same index
    try {
      underTest.resolveId(caseFail2Dto, String.class);
      fail();
    } catch (BusinessException e) {
      assertThat(e).isNotNull();
    }

    // Case 3: two equals type with the same index and the same typeIndex
    try {
      underTest.resolveId(caseFail3Dto, String.class, 0);
      fail();
    } catch (BusinessException e) {
      assertThat(e).isNotNull();
    }
  }

  private static class Case2DtoVO extends BaseValueObject {

    private final String name;
    private final Date birthDate;

    Case2DtoVO(String name, Date birthDate) {
      this.name = name;
      this.birthDate = birthDate;
    }
  }

  private static class Case4DtoVO extends BaseValueObject {

    private final String string1;
    private final String string2;

    Case4DtoVO(String string1, String string2) {
      this.string1 = string1;
      this.string2 = string2;
    }
  }

  private static class Case4DtoVOFactory extends BaseFactory<Case4DtoVO> {

  }

  private static class Case2DtoVOFactory extends BaseFactory<Case2DtoVO> {

  }
}
