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
import org.seedstack.business.api.application.GenericApplicationService;
import org.seedstack.business.api.application.annotations.ApplicationService;
import org.seedstack.business.api.domain.annotations.*;
import org.seedstack.business.api.domain.base.BaseAggregateRoot;
import org.seedstack.business.api.domain.base.BaseEntity;
import org.seedstack.business.api.domain.base.BaseValueObject;
import org.seedstack.business.api.domain.meta.specifications.DomainSpecifications;
import org.seedstack.business.api.interfaces.GenericInterfacesService;
import org.seedstack.business.api.interfaces.annotations.InterfacesService;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.api.interfaces.query.finder.Finder;
import org.seedstack.business.api.interfaces.query.finder.GenericFinder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Validates all the domain specifications.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
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
        assertThat(DomainSpecifications.aggregateRootSpecification.isSatisfiedBy(MyAggregateRoot1.class)).isTrue();
        assertThat(DomainSpecifications.aggregateRootSpecification.isSatisfiedBy(MyAggregateRoot2.class)).isTrue();

        assertThat(DomainSpecifications.aggregateRootSpecification.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(DomainSpecifications.aggregateRootSpecification).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.aggregateRootSpecification);
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
        assertThat(DomainSpecifications.entitySpecification.isSatisfiedBy(MyEntity1.class)).isTrue();
        assertThat(DomainSpecifications.entitySpecification.isSatisfiedBy(MyEntity2.class)).isTrue();

        assertThat(DomainSpecifications.entitySpecification.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(DomainSpecifications.entitySpecification).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.entitySpecification);
    }

    static interface MyApplicationService1 extends GenericApplicationService { }

    @ApplicationService
    static interface MyApplicationService2 { }

    @ApplicationService
    static class MyApplicationService3 { }

    @Test
    public void testApplicationServiceSpecification() {
        assertThat(DomainSpecifications.applicationServiceSpecification.isSatisfiedBy(MyApplicationService1.class)).isTrue();
        assertThat(DomainSpecifications.applicationServiceSpecification.isSatisfiedBy(MyApplicationService2.class)).isTrue();

        assertThat(DomainSpecifications.applicationServiceSpecification.isSatisfiedBy(MyApplicationService3.class)).isFalse();

        assertThat(DomainSpecifications.applicationServiceSpecification).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.applicationServiceSpecification);
    }

    static class MyAssembler1 implements Assembler<MyAggregateRoot1, MySimplePojo> {
        @Override
        public MySimplePojo assembleDtoFromAggregate(MyAggregateRoot1 sourceAggregate) { return null; }

        @Override
        public void updateDtoFromAggregate(MySimplePojo sourceDto, MyAggregateRoot1 sourceAggregate) { }

        @Override
        public void mergeAggregateWithDto(MyAggregateRoot1 targetAggregate, MySimplePojo sourceDto) { }

        @Override
        public Class<MySimplePojo> getDtoClass() { return null; }
    }

    @Test
    public void testAssemblerSpecification() {
        assertThat(DomainSpecifications.assemblerSpecification.isSatisfiedBy(MyAssembler1.class)).isTrue();

        assertThat(DomainSpecifications.assemblerSpecification.isSatisfiedBy(MySimplePojo.class)).isFalse();

        assertThat(DomainSpecifications.assemblerSpecification).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.assemblerSpecification);
    }

    @DomainFactory
    static interface MyFactory1 {}

    static interface MyFactory2 extends GenericFactory<MyAggregateRoot1> {}

    @DomainFactory
    static class MyFactory3 {}

    @Test
    public void testDomainFactorySpecification() {
        assertThat(DomainSpecifications.domainFactorySpecification.isSatisfiedBy(MyFactory1.class)).isTrue();
        assertThat(DomainSpecifications.domainFactorySpecification.isSatisfiedBy(MyFactory2.class)).isTrue();

        assertThat(DomainSpecifications.domainFactorySpecification.isSatisfiedBy(MyFactory3.class)).isFalse();

        assertThat(DomainSpecifications.domainFactorySpecification).describedAs("specification should be comparable")
                .isEqualTo(DomainSpecifications.domainFactorySpecification);
    }

    @DomainRepository
    static interface MyRepository1 {}

    static interface MyRepository2 extends GenericRepository<MyAggregateRoot1, String> {}

    @DomainRepository
    static class MyRepository3 {}

    @Test
    public void testDomainRepoSpecification() {
        assertThat(DomainSpecifications.domainRepoSpecification.isSatisfiedBy(MyRepository1.class)).isTrue();
        assertThat(DomainSpecifications.domainRepoSpecification.isSatisfiedBy(MyRepository2.class)).isTrue();

        assertThat(DomainSpecifications.domainRepoSpecification.isSatisfiedBy(MyRepository3.class)).isFalse();
    }

    @DomainRepositoryImpl
    static class MyRepositoryImpl1 {}

    @Test
    public void testDomainRepoImplSpecification() {
        assertThat(DomainSpecifications.domainRepoImplSpecification.isSatisfiedBy(MyRepositoryImpl1.class)).isTrue();

        assertThat(DomainSpecifications.domainRepoImplSpecification.isSatisfiedBy(MyRepository1.class)).isFalse();
    }

    @DomainService
    static interface MyDomainServiceSpecification1 {}

    static interface MyDomainServiceSpecification2 extends GenericDomainService {}

    static class MyDomainServiceSpecification3 implements GenericDomainService {}

    @Test
    public void testDomainServiceSpecification() {
        assertThat(DomainSpecifications.domainServiceSpecification.isSatisfiedBy(MyDomainServiceSpecification1.class)).isTrue();
        assertThat(DomainSpecifications.domainServiceSpecification.isSatisfiedBy(MyDomainServiceSpecification2.class)).isTrue();

        assertThat(DomainSpecifications.domainServiceSpecification.isSatisfiedBy(MyDomainServiceSpecification3.class)).isFalse();
    }

    @InterfacesService
    static interface MyInterfacesServiceSpecification1 {}

    static interface MyInterfacesServiceSpecification2 extends GenericInterfacesService {}

    static class MyInterfacesServiceSpecification3 implements GenericInterfacesService {}

    @Test
    public void testInterfacesServiceSpecification() {
        assertThat(DomainSpecifications.interfacesServiceSpecification.isSatisfiedBy(MyInterfacesServiceSpecification1.class)).isTrue();
        assertThat(DomainSpecifications.interfacesServiceSpecification.isSatisfiedBy(MyInterfacesServiceSpecification2.class)).isTrue();

        assertThat(DomainSpecifications.interfacesServiceSpecification.isSatisfiedBy(MyInterfacesServiceSpecification3.class)).isFalse();
    }

    @DtoOf(MyAggregateRoot1.class)
    static class MyPojo1 {}

    @DtoOf({MyAggregateRoot1.class, MyAggregateRoot1.class})
    static class MyPojo2 {}

    @Test
    public void testDtoWithDefaultAssemblerSpecification() {
        assertThat(DomainSpecifications.dtoWithDefaultAssemblerSpecification.isSatisfiedBy(MyPojo1.class)).isTrue();
        assertThat(DomainSpecifications.dtoWithDefaultAssemblerSpecification.isSatisfiedBy(MyPojo2.class)).isTrue();

        assertThat(DomainSpecifications.dtoWithDefaultAssemblerSpecification.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }

    @Finder
    static interface MyFinder1 {}

    static interface MyFinder2 extends GenericFinder {}

    @Finder
    static class MyFinder3 {}

    @Test
    public void testFinderServiceSpecification() {
        assertThat(DomainSpecifications.finderServiceSpecification.isSatisfiedBy(MyFinder1.class)).isTrue();
        assertThat(DomainSpecifications.finderServiceSpecification.isSatisfiedBy(MyFinder2.class)).isTrue();

        assertThat(DomainSpecifications.finderServiceSpecification.isSatisfiedBy(MyFinder3.class)).isFalse();
    }

    @DomainPolicy
    static interface MyPolicy1 {}

    static interface MyPolicy2 extends GenericDomainPolicy {}

    @DomainPolicy
    static class MyPolicy3 {}

    @Test
    public void testPolicyServiceSpecification() {
        assertThat(DomainSpecifications.policySpecification.isSatisfiedBy(MyPolicy1.class)).isTrue();
        assertThat(DomainSpecifications.policySpecification.isSatisfiedBy(MyPolicy2.class)).isTrue();

        assertThat(DomainSpecifications.policySpecification.isSatisfiedBy(MyPolicy3.class)).isFalse();
    }

    @DomainValueObject
    static class MyValueObject1 {}

    static class MyValueObject2 extends BaseValueObject {}


    @Test
    public void testValueObjectServiceSpecification() {
        assertThat(DomainSpecifications.valueObjectSpecification.isSatisfiedBy(MyValueObject1.class)).isTrue();
        assertThat(DomainSpecifications.valueObjectSpecification.isSatisfiedBy(MyValueObject2.class)).isTrue();

        assertThat(DomainSpecifications.valueObjectSpecification.isSatisfiedBy(MySimplePojo.class)).isFalse();
    }
}
