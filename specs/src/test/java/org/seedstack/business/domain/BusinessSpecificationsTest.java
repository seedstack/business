/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.seedstack.business.BusinessSpecifications;
import org.seedstack.business.Service;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.finder.Finder;
import org.seedstack.business.finder.RangeFinder;
import org.seedstack.business.spi.GenericImplementation;

/**
 * Validates all the domain specifications.
 */
public class BusinessSpecificationsTest {

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
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MyAggregateRoot1.class)).isTrue();
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MyAggregateRoot2.class)).isTrue();

        assertThat(BusinessSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(BusinessSpecifications.AGGREGATE_ROOT).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.AGGREGATE_ROOT);
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
        assertThat(BusinessSpecifications.ENTITY.isSatisfiedBy(MyEntity1.class)).isTrue();
        assertThat(BusinessSpecifications.ENTITY.isSatisfiedBy(MyEntity2.class)).isTrue();

        assertThat(BusinessSpecifications.ENTITY.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(BusinessSpecifications.ENTITY).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.ENTITY);
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
        assertThat(BusinessSpecifications.ASSEMBLER.isSatisfiedBy(MyAssembler1.class)).isTrue();

        assertThat(BusinessSpecifications.ASSEMBLER.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(BusinessSpecifications.ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.ASSEMBLER);
    }

    @Test
    public void testClassicAssemblerSpecification() {
        assertThat(BusinessSpecifications.CLASSIC_ASSEMBLER.isSatisfiedBy(MyAssembler1.class)).isTrue();

        assertThat(BusinessSpecifications.CLASSIC_ASSEMBLER.isSatisfiedBy(DefaultMyAssembler1.class)).isFalse();

        assertThat(BusinessSpecifications.CLASSIC_ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.CLASSIC_ASSEMBLER);
    }

    @Test
    public void testDefaultAssemblerSpecification() {
        assertThat(BusinessSpecifications.DEFAULT_ASSEMBLER.isSatisfiedBy(DefaultMyAssembler1.class)).isTrue();

        assertThat(BusinessSpecifications.DEFAULT_ASSEMBLER.isSatisfiedBy(MyAssembler1.class)).isFalse();

        assertThat(BusinessSpecifications.DEFAULT_ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.DEFAULT_ASSEMBLER);
    }

    @DomainFactory
    interface MyFactory1 {}

    interface MyFactory2 extends GenericFactory<MyAggregateRoot1> {}

    @DomainFactory
    static class MyFactory3 {}

    interface MyFactory4 extends Factory<MyAggregateRoot1> {}

    @Test
    public void testDomainFactorySpecification() {
        assertThat(BusinessSpecifications.FACTORY.isSatisfiedBy(MyFactory1.class)).isTrue();
        assertThat(BusinessSpecifications.FACTORY.isSatisfiedBy(MyFactory2.class)).isTrue();

        assertThat(BusinessSpecifications.FACTORY.isSatisfiedBy(MyFactory3.class)).isFalse();
        assertThat(BusinessSpecifications.FACTORY.isSatisfiedBy(MyFactory4.class)).isTrue();

        assertThat(BusinessSpecifications.FACTORY).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.FACTORY);
    }

    @DomainRepository
    interface MyRepository1 {}

    interface MyRepository2 extends GenericRepository<MyAggregateRoot1, String> {}

    @DomainRepository
    static class MyRepository3 {}

    @Test
    public void testDomainRepoSpecification() {
        assertThat(BusinessSpecifications.REPOSITORY.isSatisfiedBy(MyRepository1.class)).isTrue();
        assertThat(BusinessSpecifications.REPOSITORY.isSatisfiedBy(MyRepository2.class)).isTrue();

        assertThat(BusinessSpecifications.REPOSITORY.isSatisfiedBy(MyRepository3.class)).isFalse();
    }

    @GenericImplementation
    static class MyRepositoryImpl1<A extends AggregateRoot<K>, K> implements GenericRepository<A,K> {
        @Override
        public A load(K id) { return null; }

        @Override
        public boolean exists(K id) {
            return false;
        }

        @Override
        public long count() {
            return 0L;
        }

        @Override
        public void clear() { }

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
        assertThat(BusinessSpecifications.DEFAULT_REPOSITORY.isSatisfiedBy(MyRepositoryImpl1.class)).isTrue();

        assertThat(BusinessSpecifications.DEFAULT_REPOSITORY.isSatisfiedBy(MyRepository1.class)).isFalse();
    }

    @Service
    interface MyDomainServiceSpecification1 {}

    @Test
    public void testDomainServiceSpecification() {
        assertThat(BusinessSpecifications.SERVICE.isSatisfiedBy(MyDomainServiceSpecification1.class)).isTrue();
    }

    @DtoOf(MyAggregateRoot1.class)
    static class MyPojo1 {}

    @DtoOf({MyAggregateRoot1.class, MyAggregateRoot1.class})
    static class MyPojo2 {}

    @Test
    public void testDtoWithDefaultAssemblerSpecification() {
        assertThat(BusinessSpecifications.DTO_OF.isSatisfiedBy(MyPojo1.class)).isTrue();
        assertThat(BusinessSpecifications.DTO_OF.isSatisfiedBy(MyPojo2.class)).isTrue();

        assertThat(BusinessSpecifications.DTO_OF.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }

    @Finder
    interface MyFinder1 {}

    interface MyFinder2 extends RangeFinder {}

    @Finder
    static class MyFinder3 {}

    @Test
    public void testFinderServiceSpecification() {
        assertThat(BusinessSpecifications.FINDER.isSatisfiedBy(MyFinder1.class)).isTrue();
        assertThat(BusinessSpecifications.FINDER.isSatisfiedBy(MyFinder2.class)).isTrue();

        assertThat(BusinessSpecifications.FINDER.isSatisfiedBy(MyFinder3.class)).isFalse();
    }

    @DomainPolicy
    interface MyPolicy1 {}

    @DomainPolicy
    static class MyPolicy3 {}

    @Test
    public void testPolicyServiceSpecification() {
        assertThat(BusinessSpecifications.POLICY.isSatisfiedBy(MyPolicy1.class)).isTrue();

        assertThat(BusinessSpecifications.POLICY.isSatisfiedBy(MyPolicy3.class)).isFalse();
    }

    @DomainValueObject
    static class MyValueObject1 {}

    static class MyValueObject2 extends BaseValueObject {}


    @Test
    public void testValueObjectServiceSpecification() {
        assertThat(BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(MyValueObject1.class)).isTrue();
        assertThat(BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(MyValueObject2.class)).isTrue();

        assertThat(BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }
}
