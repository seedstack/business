/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.utils;

import java.lang.reflect.Method;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.internal.BusinessException;


public class MethodMatcherTest {

  @Test
  public void test_method_matcher() {
    Method testMethod = MethodMatcher
        .findMatchingMethod(TestedClass.class, Integer.class, "aa", ProjectStatus.FIRST);
    Assertions.assertThat(testMethod).isNotNull();
    Assertions.assertThat(testMethod.getName()).isEqualTo("test");

    Method testMethod2 = MethodMatcher
        .findMatchingMethod(TestedClass.class, Integer.class, "aa", ProjectStatus.FIRST, null);
    Assertions.assertThat(testMethod2).isNotNull();
    Assertions.assertThat(testMethod2.getName()).isEqualTo("test2");

    Method testMethod3 = MethodMatcher.findMatchingMethod(TestedClass.class, null, "aa", 1);
    Assertions.assertThat(testMethod3).isNotNull();
    Assertions.assertThat(testMethod3.getName()).isEqualTo("test3");
  }

  @Test(expected = BusinessException.class)
  public void test_method_matcher_fail() {
    MethodMatcher.findMatchingMethod(TestedClass2.class, Integer.class, "aa", ProjectStatus.FIRST);
  }

  static enum ProjectStatus {
    FIRST(0);

    int status;

    ProjectStatus(int status) {
      this.status = status;
    }

    public int getCode() {
      return status;
    }
  }

  static class TestedClass {

    public Integer test(String str, ProjectStatus i) {
      return 1;
    }

    public Integer test2(String str, ProjectStatus i, String str2) {
      return 0;
    }


    public void test3(String str, int i) {
    }
  }

  static class TestedClass2 {

    public Integer test(String str, ProjectStatus i) {
      return 1;
    }

    public Integer test2(String str, ProjectStatus i) {
      return 1;
    }
  }
}
