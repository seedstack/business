/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Sets;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class BaseEntityTest {

    private TestEntity one;
    private TestEntity two;
    private TestEntity three;

    @Before
    public void setUp() {
        one = new TestEntity(1L);
        one.setName("one");

        two = new TestEntity(1L);
        two.setName("two");

        three = new TestEntity(2L);
        three.setName("one");
    }

    @Test
    public void testHashCode() {
        Assertions.assertThat(one.hashCode())
                .isEqualTo(two.hashCode());
        Assertions.assertThat(one.hashCode())
                .isNotEqualTo(three.hashCode());
    }

    @Test
    public void testEquality() {
        Assertions.assertThat(one)
                .isEqualTo(two);
        Assertions.assertThat(one)
                .isNotEqualTo(three);
    }

    @Test
    public void checkHashcode() {
        Long entityId = 12L;
        TestEntity entity = new TestEntity(entityId);
        assertThat(entity.hashCode()).isEqualTo(entityId.hashCode());
    }

    @Test
    public void checkEquals() {
        Long entityId = 12L;
        TestEntity entity1 = new TestEntity(entityId);
        TestEntity entity2 = new TestEntity(entityId);

        Set<TestEntity> entities = Sets.newHashSet();
        entities.add(entity1);
        entities.add(entity2);

        assertThat(entities).hasSize(1);
        assertThat(entity1).isEqualTo(entity2);
    }

    @Test
    public void emptyEntityCanBeAddedToCollection() {
        TestEntity entity = new TestEntity();
        Sets.newHashSet()
                .add(entity);
    }

    @Test
    public void inheritingEntityCanBeCompared() {
        TestEntity entity = new TestEntity(1L);
        InheritingTestEntity inheritingEntity = new InheritingTestEntity(1L);
        assertThat(entity.equals(inheritingEntity)).isTrue();
        assertThat(inheritingEntity.equals(entity)).isTrue();
    }

    @Test
    public void cannotBeComparedWithNonEntity() {
        TestEntity entity = new TestEntity(1L);
        Object nonEntity = new Object();
        assertThat(entity.equals(nonEntity)).isFalse();
        assertThat(nonEntity.equals(entity)).isFalse();
    }

    static class TestEntity extends BaseEntity<Long> {

        private Long id;
        private String name;

        TestEntity(Long id) {
            this.id = id;
        }

        TestEntity() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class InheritingTestEntity extends TestEntity {

        public InheritingTestEntity(Long id) {
            super(id);
        }
    }
}
