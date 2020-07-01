/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
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
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.seed.Ignore;

public class BusinessSpecificationsTest {

    @Test
    public void testAggregateRootSpecification() {
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.test(MyAggregateRoot1.class)).isTrue();
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.test(MyAggregateRoot2.class)).isTrue();
        assertThat(BusinessSpecifications.AGGREGATE_ROOT.test(MySimplePojo.class)).isFalse();
        assertThat(BusinessSpecifications.AGGREGATE_ROOT).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.AGGREGATE_ROOT);
    }

    @Test
    public void testEntitySpecification() {
        assertThat(BusinessSpecifications.ENTITY.test(MyEntity1.class)).isTrue();
        assertThat(BusinessSpecifications.ENTITY.test(MyEntity2.class)).isTrue();
        assertThat(BusinessSpecifications.ENTITY.test(MySimplePojo.class)).isFalse();
        assertThat(BusinessSpecifications.ENTITY).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.ENTITY);
    }

    @Test
    public void testAssemblerSpecification() {
        assertThat(BusinessSpecifications.ASSEMBLER.test(MyAssembler1.class)).isTrue();
        assertThat(BusinessSpecifications.ASSEMBLER.test(MySimplePojo.class)).isFalse();
        assertThat(BusinessSpecifications.ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.ASSEMBLER);
    }

    @Test
    public void testClassicAssemblerSpecification() {
        assertThat(BusinessSpecifications.EXPLICIT_ASSEMBLER.test(MyAssembler1.class)).isTrue();
        assertThat(BusinessSpecifications.EXPLICIT_ASSEMBLER.test(DefaultMyAssembler1.class)).isFalse();
        assertThat(BusinessSpecifications.EXPLICIT_ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.EXPLICIT_ASSEMBLER);
    }

    @Test
    public void testDefaultAssemblerSpecification() {
        assertThat(BusinessSpecifications.DEFAULT_ASSEMBLER.test(DefaultMyAssembler1.class)).isTrue();
        assertThat(BusinessSpecifications.DEFAULT_ASSEMBLER.test(MyAssembler1.class)).isFalse();
        assertThat(BusinessSpecifications.DEFAULT_ASSEMBLER).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.DEFAULT_ASSEMBLER);
    }

    @Test
    public void testDomainFactorySpecification() {
        assertThat(BusinessSpecifications.FACTORY.test(MyFactory1.class)).isTrue();
        assertThat(BusinessSpecifications.FACTORY.test(MyFactory2.class)).isTrue();
        assertThat(BusinessSpecifications.FACTORY.test(MyFactory3.class)).isFalse();
        assertThat(BusinessSpecifications.FACTORY.test(MyFactory4.class)).isTrue();
        assertThat(BusinessSpecifications.FACTORY).describedAs("specification should be comparable")
                .isEqualTo(BusinessSpecifications.FACTORY);
    }

    @Test
    public void testDomainRepoSpecification() {
        assertThat(BusinessSpecifications.REPOSITORY.test(MyRepository1.class)).isTrue();
        assertThat(BusinessSpecifications.REPOSITORY.test(MyRepository2.class)).isTrue();
        assertThat(BusinessSpecifications.REPOSITORY.test(MyRepository3.class)).isFalse();
    }

    @Test
    public void testDomainRepoImplSpecification() {
        assertThat(BusinessSpecifications.DEFAULT_REPOSITORY.test(MyRepositoryImpl1.class)).isTrue();
        assertThat(BusinessSpecifications.DEFAULT_REPOSITORY.test(MyRepository1.class)).isFalse();
    }

    @Test
    public void testDomainServiceSpecification() {
        assertThat(BusinessSpecifications.SERVICE.test(MyDomainServiceSpecification1.class)).isTrue();
    }

    @Test
    public void testDtoWithDefaultAssemblerSpecification() {
        assertThat(BusinessSpecifications.DTO_OF.test(MyPojo1.class)).isTrue();
        assertThat(BusinessSpecifications.DTO_OF.test(MyPojo2.class)).isTrue();
        assertThat(BusinessSpecifications.DTO_OF.test(MySimplePojo.class)).isFalse();
    }

    @Test
    public void testPolicyServiceSpecification() {
        assertThat(BusinessSpecifications.POLICY.test(MyPolicy1.class)).isTrue();
        assertThat(BusinessSpecifications.POLICY.test(MyPolicy2.class)).isFalse();
    }

    @Test
    public void testValueObjectServiceSpecification() {
        assertThat(BusinessSpecifications.VALUE_OBJECT.test(MyValueObject1.class)).isTrue();
        assertThat(BusinessSpecifications.VALUE_OBJECT.test(MyValueObject2.class)).isTrue();
        assertThat(BusinessSpecifications.VALUE_OBJECT.test(MySimplePojo.class)).isFalse();
    }

    @DomainFactory
    interface MyFactory1 {

    }

    interface MyFactory2 extends Factory<MyAggregateRoot1> {

    }

    interface MyFactory4 extends Factory<MyAggregateRoot1> {

    }

    @DomainRepository
    @Ignore
    private interface MyRepository1 {

    }

    @Ignore
    private interface MyRepository2 extends Repository<MyAggregateRoot1, String> {

    }

    @Service
    @Ignore
    private interface MyDomainServiceSpecification1 {

    }

    @DomainPolicy
    @Ignore
    private interface MyPolicy1 {

    }

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

    @Ignore
    private static class MyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {

        @Override
        public MySimplePojo createDtoFromAggregate(MyAggregateRoot1 sourceAggregate) {
            return null;
        }

        @Override
        public void mergeAggregateIntoDto(MyAggregateRoot1 sourceAggregate, MySimplePojo sourceDto) {
        }

        @Override
        public void mergeDtoIntoAggregate(MySimplePojo sourceDto, MyAggregateRoot1 targetAggregate) {
        }

        @Override
        public Class<MySimplePojo> getDtoClass() {
            return null;
        }

        @Override
        public MySimplePojo createDto() {
            return null;
        }
    }

    @GenericImplementation
    @Ignore
    private static class DefaultMyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {

        @Override
        public MySimplePojo createDtoFromAggregate(MyAggregateRoot1 sourceAggregate) {
            return null;
        }

        @Override
        public void mergeAggregateIntoDto(MyAggregateRoot1 sourceAggregate, MySimplePojo sourceDto) {
        }

        @Override
        public void mergeDtoIntoAggregate(MySimplePojo sourceDto, MyAggregateRoot1 targetAggregate) {
        }

        @Override
        public Class<MySimplePojo> getDtoClass() {
            return null;
        }

        @Override
        public MySimplePojo createDto() {
            return null;
        }
    }

    @DomainFactory
    static class MyFactory3 {

    }

    @DomainRepository
    @Ignore
    private static class MyRepository3 {

    }

    @GenericImplementation
    @Ignore
    private static class MyRepositoryImpl1<A extends AggregateRoot<K>, K> implements Repository<A, K> {

        @Override
        public void add(A a) {
        }

        @Override
        public Stream<A> get(Specification<A> specification, Repository.Option... options) {
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

        @Override
        public SpecificationBuilder getSpecificationBuilder() {
            return null;
        }
    }

    @DtoOf(MyAggregateRoot1.class)
    @Ignore
    private static class MyPojo1 {

    }

    @DtoOf({MyAggregateRoot1.class, MyAggregateRoot1.class})
    @Ignore
    private static class MyPojo2 {

    }

    @DomainPolicy
    @Ignore
    private static class MyPolicy2 {

    }

    @DomainValueObject
    @Ignore
    private static class MyValueObject1 {

    }

    @Ignore
    private static class MyValueObject2 extends BaseValueObject {

    }
}
