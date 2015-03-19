/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.assertions.asserts;

import org.seedstack.business.assertions.asserts.BusinessReflectionAsserts;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Objects;
import org.seedstack.seed.core.assertions.CoreClassAssert;

import java.lang.annotation.Annotation;

/**
 * This class provides assertions on JPA.
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class BusinessJpaClassAssert extends CoreClassAssert<BusinessJpaClassAssert> {

    /**
     * Constructor.
     *
     * @param actual the actual class to check
     */
    public BusinessJpaClassAssert(Class<?> actual) {
        super(actual, BusinessJpaClassAssert.class);
    }


    /**
     * Chains asserts.
     *
     * @return itself
     */
    public BusinessJpaClassAssert and() {
        return myself;
    }


    /**
     * Asserts that the candidate is a valid JPA repository class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidJpaRepositoryClass() {
        assertNotNull(info, actual);

        BusinessJpaReflectionAsserts.assertJpaRepositoryClassIsValid(actual);

        return myself;
    }

    /**
     * Asserts that the candidate is valid default factory.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidFactoryDefaultClass() {
        assertNotNull(info, actual);

        BusinessJpaReflectionAsserts.assertDefaultFactoryClassIsValid(actual);

        return myself;

    }

    /**
     * Checks that the candidate is valid DomainServiceDefault class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidDomainServiceDefaultClass() {
        assertNotNull(info, actual);

        BusinessJpaReflectionAsserts.assertDefaultDomainServiceClassIsValid(actual);

        return myself;
    }

    /**
     * Checks that the candidate is valid DomainPolicyInternal class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidDomainPolicyInternalClass() {
        assertNotNull(info, actual);

        BusinessJpaReflectionAsserts.assertDomainPolicyInternalClassIsValid(actual);

        return myself;
    }

    /**
     * Checks that the candidate is valid ApplicationServiceInternal class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidApplicationServiceInternalClass() {
        assertNotNull(info, actual);

        BusinessJpaReflectionAsserts.assertDefaultApplicationServiceClassIsValid(actual);

        return myself;
    }

    /** Business Class Assert inheritance  ***/


    /**
     * Checks that the candidate is a valid aggregate root.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidAggregateRootClass() {
        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertAggregateRootClassIsValid(actual);

        return myself;
    }

    /**
     * Asserts that the candidate is a valid ValueObject class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidValueObjectClass() {
        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertValueObjectClassIsValid(actual);

        return myself;
    }

    /**
     * Asserts that the candidate is a valid Entity class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidEntityClass() {

        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertEntityClassIsValid(actual);

        return myself;
    }

    /**
     * Asserts that the candidate is a valid RepositoryInterface class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidRepositoryInterfaceClass() {
        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertRepositoryInterfaceClassIsValid(actual);

        return myself;
    }


    /**
     * Asserts that the candidate is a valid FactoryInterface class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidFactoryInterfaceClass() {
        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertFactoryInterfaceClassIsValid(actual);

        return myself;

    }

    /**
     * Asserts that the candidate is a valid DomainServiceInterface class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidDomainServiceInterfaceClass() {

        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertDomainServiceInterfaceClassIsValid(actual);

        return myself;

    }

    /**
     * Asserts that the candidate is a valid ApplicationServiceInterface class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidApplicationServiceInterfaceClass() {

        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertApplicationServiceInterfaceClassIsValid(actual);

        return myself;

    }

    /**
     * Asserts that the candidate is a valid DomainPolicyInterface class.
     *
     * @return itself
     */
    public BusinessJpaClassAssert isValidDomainPolicyInterfaceClass() {
        assertNotNull(info, actual);

        BusinessReflectionAsserts.assertDomainPolicyInterfaceClassIsValid(actual);

        return myself;
    }


    /**
     * Inheritance
     */

    private static void assertNotNull(AssertionInfo info, Class<?> actual) {
        Objects.instance().assertNotNull(info, actual);
    }

    @Override
    public BusinessJpaClassAssert isInjectable() {
        return super.isInjectable();
    }

    @Override
    public BusinessJpaClassAssert isInjectedWithInstanceOf(Class<?> candidate) {
        return super.isInjectedWithInstanceOf(candidate);
    }

    @Override
    public BusinessJpaClassAssert isAssignableFrom(Class<?>... others) {
        return super.isAssignableFrom(others);
    }

    @Override
    public BusinessJpaClassAssert isNotInterface() {
        return super.isNotInterface();
    }

    @Override
    public BusinessJpaClassAssert isInterface() {
        return super.isInterface();
    }

    @Override
    public BusinessJpaClassAssert isAnnotation() {
        return super.isAnnotation();
    }

    @Override
    public BusinessJpaClassAssert isNotAnnotation() {
        return super.isNotAnnotation();
    }

    @Override
    public BusinessJpaClassAssert hasAnnotations(
            Class<? extends Annotation>... annotations) {
        return super.hasAnnotations(annotations);
    }

    @Override
    public BusinessJpaClassAssert hasAnnotation(Class<? extends Annotation> annotation) {
        return super.hasAnnotation(annotation);
    }

    @Override
    public BusinessJpaClassAssert hasFields(String... fields) {
        return super.hasFields(fields);
    }

    @Override
    public BusinessJpaClassAssert hasDeclaredFields(String... fields) {
        return super.hasDeclaredFields(fields);
    }

}
