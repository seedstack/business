/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.junit.Test;
import org.seedstack.business.api.DomainSpecifications;
import org.seedstack.business.api.Service;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.api.interfaces.finder.Finder;
import org.seedstack.business.api.interfaces.finder.RangeFinder;
import org.seedstack.business.spi.GenericImplementation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Validates all the domain specifications.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class DomainSpecificationsTest {

    static class MyAggregateRoot1 extends BaseAggregateRoot<String> {

        @Override
        public String getEntityId() {
            return "id";
        }
    }

    @DomainAggregateRoot
    static class MyAggregateRoot2 { }

    static class MySimplePojo { }

    @Test
    public void testAggregateRootSpecification() {
        assertThat(DomainSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MyAggregateRoot1.class)).isTrue();
        assertThat(DomainSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MyAggregateRoot2.class)).isTrue();

        assertThat(DomainSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(DomainSpecifications.AGGREGATE_ROOT).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.AGGREGATE_ROOT);
    }

    static class MyEntity1 extends BaseEntity<String> {

        @Override
        public String getEntityId() {
            return "id";
        }
    }

    @DomainEntity
    static class MyEntity2 { }

    @Test
    public void testEntitySpecification() {
        assertThat(DomainSpecifications.ENTITY.isSatisfiedBy(MyEntity1.class)).isTrue();
        assertThat(DomainSpecifications.ENTITY.isSatisfiedBy(MyEntity2.class)).isTrue();

        assertThat(DomainSpecifications.ENTITY.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(DomainSpecifications.ENTITY).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.ENTITY);
    }

    static class MyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {
        @Override
        public MySimplePojo assembleDtoFromAggregate(MyAggregateRoot1 sourceAggregate) { return null; }

        @Override
        public void assembleDtoFromAggregate(MySimplePojo sourceDto, MyAggregateRoot1 sourceAggregate) { }

        @Override
        public void mergeAggregateWithDto(MyAggregateRoot1 targetAggregate, MySimplePojo sourceDto) { }

        @Override
        public Class<MySimplePojo> getDtoClass() { return null; }
    }

    @GenericImplementation
    static class DefaultMyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {
        @Override
        public MySimplePojo assembleDtoFromAggregate(MyAggregateRoot1 sourceAggregate) { return null; }

        @Override
        public void assembleDtoFromAggregate(MySimplePojo sourceDto, MyAggregateRoot1 sourceAggregate) { }

        @Override
        public void mergeAggregateWithDto(MyAggregateRoot1 targetAggregate, MySimplePojo sourceDto) { }

        @Override
        public Class<MySimplePojo> getDtoClass() { return null; }
    }

    @Test
    public void testAssemblerSpecification() {
        assertThat(DomainSpecifications.ASSEMBLER.isSatisfiedBy(MyAssembler1.class)).isTrue();

        assertThat(DomainSpecifications.ASSEMBLER.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(DomainSpecifications.ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.ASSEMBLER);
    }

    @Test
    public void testClassicAssemblerSpecification() {
        assertThat(DomainSpecifications.CLASSIC_ASSEMBLER.isSatisfiedBy(MyAssembler1.class)).isTrue();

        assertThat(DomainSpecifications.CLASSIC_ASSEMBLER.isSatisfiedBy(DefaultMyAssembler1.class)).isFalse();

        assertThat(DomainSpecifications.CLASSIC_ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.CLASSIC_ASSEMBLER);
    }

    @Test
    public void testDefaultAssemblerSpecification() {
        assertThat(DomainSpecifications.DEFAULT_ASSEMBLER.isSatisfiedBy(DefaultMyAssembler1.class)).isTrue();

        assertThat(DomainSpecifications.DEFAULT_ASSEMBLER.isSatisfiedBy(MyAssembler1.class)).isFalse();

        assertThat(DomainSpecifications.DEFAULT_ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.DEFAULT_ASSEMBLER);
    }

    @DomainFactory
    static interface MyFactory1 {}

    static interface MyFactory2 extends GenericFactory<MyAggregateRoot1> {}

    @DomainFactory
    static class MyFactory3 {}

    @Test
    public void testDomainFactorySpecification() {
        assertThat(DomainSpecifications.FACTORY.isSatisfiedBy(MyFactory1.class)).isTrue();
        assertThat(DomainSpecifications.FACTORY.isSatisfiedBy(MyFactory2.class)).isTrue();

        assertThat(DomainSpecifications.FACTORY.isSatisfiedBy(MyFactory3.class)).isFalse();

        assertThat(DomainSpecifications.FACTORY).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.FACTORY);
    }

    @DomainRepository
    static interface MyRepository1 {}

    static interface MyRepository2 extends GenericRepository<MyAggregateRoot1, String> {}

    @DomainRepository
    static class MyRepository3 {}

    @Test
    public void testDomainRepoSpecification() {
        assertThat(DomainSpecifications.REPOSITORY.isSatisfiedBy(MyRepository1.class)).isTrue();
        assertThat(DomainSpecifications.REPOSITORY.isSatisfiedBy(MyRepository2.class)).isTrue();

        assertThat(DomainSpecifications.REPOSITORY.isSatisfiedBy(MyRepository3.class)).isFalse();
    }

    @GenericImplementation
    static class MyRepositoryImpl1<A extends AggregateRoot<K>, K> implements GenericRepository<A,K> {
        @Override
        public A load(K id) { return null; }

        @Override
        public void delete(K k) { }

        @Override
        public void delete(A a) { }

        @Override
        public void persist(A a) { }

        @Override
        public A save(A a) { return null; }

        @Override
        public Class<A> getAggregateRootClass() { return null; }

        @Override
        public Class<K> getKeyClass() { return null; }
    }

    @Test
    public void testDomainRepoImplSpecification() {
        assertThat(DomainSpecifications.DEFAULT_REPOSITORY.isSatisfiedBy(MyRepositoryImpl1.class)).isTrue();

        assertThat(DomainSpecifications.DEFAULT_REPOSITORY.isSatisfiedBy(MyRepository1.class)).isFalse();
    }

    @Service
    static interface MyDomainServiceSpecification1 {}

    @Test
    public void testDomainServiceSpecification() {
        assertThat(DomainSpecifications.SERVICE.isSatisfiedBy(MyDomainServiceSpecification1.class)).isTrue();
    }

    @DtoOf(MyAggregateRoot1.class)
    static class MyPojo1 {}

    @DtoOf({MyAggregateRoot1.class, MyAggregateRoot1.class})
    static class MyPojo2 {}

    @Test
    public void testDtoWithDefaultAssemblerSpecification() {
        assertThat(DomainSpecifications.DTO_OF.isSatisfiedBy(MyPojo1.class)).isTrue();
        assertThat(DomainSpecifications.DTO_OF.isSatisfiedBy(MyPojo2.class)).isTrue();

        assertThat(DomainSpecifications.DTO_OF.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }

    @Finder
    static interface MyFinder1 {}

    static interface MyFinder2 extends RangeFinder {}

    @Finder
    static class MyFinder3 {}

    @Test
    public void testFinderServiceSpecification() {
        assertThat(DomainSpecifications.FINDER.isSatisfiedBy(MyFinder1.class)).isTrue();
        assertThat(DomainSpecifications.FINDER.isSatisfiedBy(MyFinder2.class)).isTrue();

        assertThat(DomainSpecifications.FINDER.isSatisfiedBy(MyFinder3.class)).isFalse();
    }

    @DomainPolicy
    static interface MyPolicy1 {}

    @DomainPolicy
    static class MyPolicy3 {}

    @Test
    public void testPolicyServiceSpecification() {
        assertThat(DomainSpecifications.POLICY.isSatisfiedBy(MyPolicy1.class)).isTrue();

        assertThat(DomainSpecifications.POLICY.isSatisfiedBy(MyPolicy3.class)).isFalse();
    }

    @DomainValueObject
    static class MyValueObject1 {}

    static class MyValueObject2 extends BaseValueObject {}


    @Test
    public void testValueObjectServiceSpecification() {
        assertThat(DomainSpecifications.VALUE_OBJECT.isSatisfiedBy(MyValueObject1.class)).isTrue();
        assertThat(DomainSpecifications.VALUE_OBJECT.isSatisfiedBy(MyValueObject2.class)).isTrue();

        assertThat(DomainSpecifications.VALUE_OBJECT.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }
}
