/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.junit.Test;
import org.seedstack.business.Service;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.BaseEntity;
import org.seedstack.business.domain.BaseValueObject;
import org.seedstack.business.domain.DomainAggregateRoot;
import org.seedstack.business.domain.DomainEntity;
import org.seedstack.business.domain.DomainFactory;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRepository;
import org.seedstack.business.domain.DomainValueObject;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.domain.RepositoryOptions;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.finder.Finder;
import org.seedstack.business.finder.RangeFinder;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.seed.Ignore;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Validates all the domain specifications.
 */
public class BusinessSpecificationsTest {
    @Ignore
    private static class MyAggregateRoot1 extends BaseAggregateRoot<String> {
        @Override
        public String getId() {
            return "id";
        }
    }

    @DomainAggregateRoot
    @Ignore
    private static class MyAggregateRoot2 {
    }

    @Ignore
    private static class MySimplePojo {
    }

    @Test
    public void testAggregateRootSpecification() {
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MyAggregateRoot1.class)).isTrue();
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MyAggregateRoot2.class)).isTrue();

        assertThat(BusinessSpecifications.AGGREGATE_ROOT.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(BusinessSpecifications.AGGREGATE_ROOT).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.AGGREGATE_ROOT);
    }

    @Ignore
    private static class MyEntity1 extends BaseEntity<String> {

        @Override
        public String getId() {
            return "id";
        }
    }

    @DomainEntity
    @Ignore
    private static class MyEntity2 {
    }

    @Test
    public void testEntitySpecification() {
        assertThat(BusinessSpecifications.ENTITY.isSatisfiedBy(MyEntity1.class)).isTrue();
        assertThat(BusinessSpecifications.ENTITY.isSatisfiedBy(MyEntity2.class)).isTrue();

        assertThat(BusinessSpecifications.ENTITY.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(BusinessSpecifications.ENTITY).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.ENTITY);
    }

    @Ignore
    private static class MyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {
        @Override
        public MySimplePojo assembleDtoFromAggregate(MyAggregateRoot1 sourceAggregate) {
            return null;
        }

        @Override
        public void assembleDtoFromAggregate(MySimplePojo sourceDto, MyAggregateRoot1 sourceAggregate) {
        }

        @Override
        public void mergeAggregateWithDto(MyAggregateRoot1 targetAggregate, MySimplePojo sourceDto) {
        }

        @Override
        public Class<MySimplePojo> getDtoClass() {
            return null;
        }
    }

    @GenericImplementation
    @Ignore
    private static class DefaultMyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {
        @Override
        public MySimplePojo assembleDtoFromAggregate(MyAggregateRoot1 sourceAggregate) {
            return null;
        }

        @Override
        public void assembleDtoFromAggregate(MySimplePojo sourceDto, MyAggregateRoot1 sourceAggregate) {
        }

        @Override
        public void mergeAggregateWithDto(MyAggregateRoot1 targetAggregate, MySimplePojo sourceDto) {
        }

        @Override
        public Class<MySimplePojo> getDtoClass() {
            return null;
        }
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
    interface MyFactory1 {
    }

    interface MyFactory2 extends Factory<MyAggregateRoot1> {
    }

    @DomainFactory
    static class MyFactory3 {
    }

    interface MyFactory4 extends Factory<MyAggregateRoot1> {
    }

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
    @Ignore
    private interface MyRepository1 {
    }

    @Ignore
    private interface MyRepository2 extends Repository<MyAggregateRoot1, String> {
    }

    @DomainRepository
    @Ignore
    private static class MyRepository3 {
    }

    @Test
    public void testDomainRepoSpecification() {
        assertThat(BusinessSpecifications.REPOSITORY.isSatisfiedBy(MyRepository1.class)).isTrue();
        assertThat(BusinessSpecifications.REPOSITORY.isSatisfiedBy(MyRepository2.class)).isTrue();

        assertThat(BusinessSpecifications.REPOSITORY.isSatisfiedBy(MyRepository3.class)).isFalse();
    }

    @GenericImplementation
    @Ignore
    private static class MyRepositoryImpl1<A extends AggregateRoot<K>, K> implements Repository<A, K> {
        @Override
        public void add(A a) {
        }

        @Override
        public Stream<A> get(Specification<A> specification, RepositoryOptions... options) {
            return null;
        }

        @Override
        public long remove(Specification<A> specification) {
            return 0;
        }

        @Override
        public Class<A> getAggregateRootClass() {
            return null;
        }

        @Override
        public Class<K> getIdentifierClass() {
            return null;
        }
    }

    @Test
    public void testDomainRepoImplSpecification() {
        assertThat(BusinessSpecifications.DEFAULT_REPOSITORY.isSatisfiedBy(MyRepositoryImpl1.class)).isTrue();

        assertThat(BusinessSpecifications.DEFAULT_REPOSITORY.isSatisfiedBy(MyRepository1.class)).isFalse();
    }

    @Service
    @Ignore
    private interface MyDomainServiceSpecification1 {
    }

    @Test
    public void testDomainServiceSpecification() {
        assertThat(BusinessSpecifications.SERVICE.isSatisfiedBy(MyDomainServiceSpecification1.class)).isTrue();
    }

    @DtoOf(MyAggregateRoot1.class)
    @Ignore
    private static class MyPojo1 {
    }

    @DtoOf({MyAggregateRoot1.class, MyAggregateRoot1.class})
    @Ignore
    private static class MyPojo2 {
    }

    @Test
    public void testDtoWithDefaultAssemblerSpecification() {
        assertThat(BusinessSpecifications.DTO_OF.isSatisfiedBy(MyPojo1.class)).isTrue();
        assertThat(BusinessSpecifications.DTO_OF.isSatisfiedBy(MyPojo2.class)).isTrue();

        assertThat(BusinessSpecifications.DTO_OF.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }

    @Finder
    @Ignore
    private interface MyFinder1 {
    }

    @Ignore
    private interface MyFinder2 extends RangeFinder {
    }

    @Finder
    @Ignore
    private static class MyFinder3 {
    }

    @Test
    public void testFinderServiceSpecification() {
        assertThat(BusinessSpecifications.FINDER.isSatisfiedBy(MyFinder1.class)).isTrue();
        assertThat(BusinessSpecifications.FINDER.isSatisfiedBy(MyFinder2.class)).isTrue();

        assertThat(BusinessSpecifications.FINDER.isSatisfiedBy(MyFinder3.class)).isFalse();
    }

    @DomainPolicy
    @Ignore
    private interface MyPolicy1 {
    }

    @DomainPolicy
    @Ignore
    private static class MyPolicy3 {
    }

    @Test
    public void testPolicyServiceSpecification() {
        assertThat(BusinessSpecifications.POLICY.isSatisfiedBy(MyPolicy1.class)).isTrue();

        assertThat(BusinessSpecifications.POLICY.isSatisfiedBy(MyPolicy3.class)).isFalse();
    }

    @DomainValueObject
    @Ignore
    private static class MyValueObject1 {
    }

    @Ignore
    private static class MyValueObject2 extends BaseValueObject {
    }


    @Test
    public void testValueObjectServiceSpecification() {
        assertThat(BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(MyValueObject1.class)).isTrue();
        assertThat(BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(MyValueObject2.class)).isTrue();

        assertThat(BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }
}
