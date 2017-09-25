/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.specification.dsl.SpecificationBuilder;
import org.seedstack.business.spi.SpecificationTranslator;
import org.seedstack.seed.it.SeedITRunner;

@RunWith(SeedITRunner.class)
public class SpecTranslatorIT {

  @Inject
  private SpecificationTranslator<StringBuilder, String> dummySpecificationTranslator;
  @Inject
  private SpecificationBuilder specificationBuilder;

  @Test
  public void translatorIsInjectable() throws Exception {
    assertThat(dummySpecificationTranslator).isNotNull();
  }

  @Test
  public void translatorIsWorking() throws Exception {
    String result = dummySpecificationTranslator.translate(
        specificationBuilder.of(SomeAggregateRoot.class)
            .property("path1").equalTo("value1").and()
            .property("path2").equalTo("value2")
            .build(),
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
