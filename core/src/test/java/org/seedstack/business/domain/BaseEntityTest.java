/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class BaseEntityTest {
    private Entity1 one;
    private Entity1 two;
    private Entity1 three;

    static class Entity1 extends BaseEntity<Long> {
        private Long id;
        private String name;

        public Entity1() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(long l) {
            this.id = l;
        }
    }


    @Before
    public void init() {
        one = new Entity1();
        one.setId(1l);
        one.setName("one");

        two = new Entity1();
        two.setId(1l);
        two.setName("two");

        three = new Entity1();
        three.setId(2l);
        three.setName("one");
    }

    @Test
    public void testHashCode() {
        Assertions.assertThat(one.hashCode()).isEqualTo(two.hashCode());
        Assertions.assertThat(one.hashCode()).isNotEqualTo(three.hashCode());
    }

    @Test
    public void testEqualsObject() {
        Assertions.assertThat(one).isEqualTo(two);
        Assertions.assertThat(one).isNotEqualTo(three);
    }
}
