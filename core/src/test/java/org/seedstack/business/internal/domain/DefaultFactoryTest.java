/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.internal.BusinessException;

/**
 * FactoryInternalTest
 */
public class DefaultFactoryTest {

    private static final Integer ID = 2;
    private static final int ID_PRIMITIVE = 2;
    private static final String NOM = "test";
    private static final Long AGE = 1L;
    Factory<MyAggregateTest> myFactory;

    @Before
    public void before() {
        myFactory = new DefaultFactory<>(new Class<?>[]{MyAggregateTest.class});
    }

    @Test
    public void create_aggregate_using_unique_parameter_constructor() {
        MyAggregateTest myAggregateTest = myFactory.create(ID);
        Assertions.assertThat(myAggregateTest)
                .isNotNull();
        Assertions.assertThat(myAggregateTest.getId())
                .isEqualTo(ID);
    }

    @Test
    public void create_aggregate_using_unique_primitive_parameter_constructor() {
        MyAggregateTest myAggregateTest = myFactory.create(ID_PRIMITIVE);
        Assertions.assertThat(myAggregateTest)
                .isNotNull();
        Assertions.assertThat(myAggregateTest.getId())
                .isEqualTo(ID);
    }

    @Test
    public void create_aggregate_using_two_paramater_constructor() {
        MyAggregateTest myAggregateTest = myFactory.create(NOM, ID);
        Assertions.assertThat(myAggregateTest)
                .isNotNull();
        Assertions.assertThat(myAggregateTest.getId())
                .isEqualTo(ID);
        Assertions.assertThat(myAggregateTest.getName())
                .isEqualTo(NOM);
        myAggregateTest = myFactory.create(NOM, ID_PRIMITIVE);
        Assertions.assertThat(myAggregateTest)
                .isNotNull();
        Assertions.assertThat(myAggregateTest.getId())
                .isEqualTo(ID);
        Assertions.assertThat(myAggregateTest.getName())
                .isEqualTo(NOM);
    }

    @Test(expected = BusinessException.class)
    public void create_aggregate_using_two_parameter_conflicted_constructor() {
        myFactory.create(ID, NOM);
    }

    @Test(expected = BusinessException.class)
    public void create_aggregate_using_a_primitive_parameter_conflicted_constructor() {
        myFactory.create(ID_PRIMITIVE, NOM);
    }

    @Test(expected = BusinessException.class)
    public void create_aggregate_using_null_parameters_conflicted_constructor() {
        myFactory.create(AGE, null);
    }

    @Test(expected = BusinessException.class)
    public void create_aggregate_using_parameters_for_no_existing_constructor() {
        myFactory.create(AGE, "test1", "test2", "test3");
    }

    @Test
    public void create_aggregate_using_null_parameters_constructor() {
        MyAggregateTest myAggregateTest = myFactory.create(NOM, null);
        Assertions.assertThat(myAggregateTest)
                .isNotNull();
        Assertions.assertThat(myAggregateTest.getId())
                .isEqualTo(null);
        Assertions.assertThat(myAggregateTest.getName())
                .isEqualTo(NOM);
    }

    static class MyAggregateTest implements AggregateRoot<Integer> {

        Integer id;

        String name;

        Long age;

        private MyAggregateTest(String name) {
            super();
            this.name = name;
        }

        protected MyAggregateTest(Integer id) {
            super();
            this.id = id;
        }

        MyAggregateTest(Integer id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        MyAggregateTest(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        MyAggregateTest(Long age, Integer id) {
            super();
            this.age = age;
            this.id = id;
        }

        MyAggregateTest(Long age, Long age2) {
            super();
            this.age = age;
        }

        MyAggregateTest(String name, Integer id) {
            super();
            this.id = id;
            this.name = name;
        }

        MyAggregateTest() {
            super();
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        @Override
        public Integer getId() {
            return id;
        }
    }
}
