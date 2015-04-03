/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.assertions;

import org.seedstack.business.assertions.BusinessAssertionsErrorCodes;
import org.seedstack.business.assertions.BusinessReflectionAsserts;
import org.seedstack.business.core.domain.base.BaseFactory;
import org.seedstack.business.jpa.infrastructure.repository.BaseJpaRepository;
import org.apache.commons.collections.iterators.ArrayIterator;
import org.seedstack.business.api.domain.meta.specifications.DomainSpecifications;
import org.kametic.specifications.AbstractSpecification;
import org.kametic.specifications.Specification;
import org.seedstack.seed.core.api.ErrorCode;
import org.seedstack.seed.core.api.SeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public final class BusinessJpaReflectionAsserts {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessJpaReflectionAsserts.class);
    private static final String PARENT_CLASS_NAME = "parentClassName";
    private static final String MORE = "more";

    private BusinessJpaReflectionAsserts() {
    }

    /**
     * Asserts that the class is a valid JpaRepository.
     * @param actual the class to check
     */
    public static void assertJpaRepositoryClassIsValid(Class<?> actual) {
        $(
                actual, parentInterfacesHasOneRepositoryInterface(),
                BusinessJpaAssertionsErrorCodes.CLASS_MUST_IMPLEMENT_A_REPOSITORY_INTERFACE

        );

        // we check that the class extends GenericJpaRepository
        $(
                actual, DomainSpecifications.classInherits(BaseJpaRepository.class),
                BusinessAssertionsErrorCodes.CLASS_MUST_EXTENDS,
                PARENT_CLASS_NAME, BaseJpaRepository.class.getName(),
                MORE, "Please do not forget to remove all methods already defined."
        );
    }

    /**
     * Asserts that the class is a valid Factory.
     * @param actual the class to check
     */
    public static void assertDefaultFactoryClassIsValid(Class<?> actual) {
        $(
                actual, parentInterfacesHasOneFactoryInterface(),
                BusinessJpaAssertionsErrorCodes.CLASS_MUST_IMPLEMENT_A_FACTORY_INTERFACE
        );

        // we check that the class extends GenericJpaRepository
        $(
                actual, DomainSpecifications.classInherits(BaseFactory.class),
                BusinessAssertionsErrorCodes.CLASS_MUST_EXTENDS,
                PARENT_CLASS_NAME, BaseFactory.class.getName(),
                MORE, "Please do not forget to remove all methods already defined."
        );
    }

    /**
     * Asserts that the class is a valid DomainService.
     * @param actual the class to check
     */
    public static void assertDefaultDomainServiceClassIsValid(Class<?> actual) {
        $(
                actual, parentInterfacesHasOneDomainServiceInterface(),
                BusinessJpaAssertionsErrorCodes.CLASS_MUST_IMPLEMENT_A_DOMAIN_SERVICE_INTERFACE
        );
    }

    /**
     * Asserts that the class is a valid ApplicationService.
     * @param actual the class to check
     */
    public static void assertDefaultApplicationServiceClassIsValid(Class<?> actual) {
        $(
                actual, parentInterfacesHasOneApplicationServiceInterface(),
                BusinessJpaAssertionsErrorCodes.CLASS_MUST_IMPLEMENT_A_APPLICATION_SERVICE_INTERFACE
        );

    }

    /**
     * Asserts that the class is a valid DomainPolicyInternal.
     * @param actual the class to check
     */
    public static void assertDomainPolicyInternalClassIsValid(Class<?> actual) {
        $(
                actual, parentInterfacesHasOneDomainPolicyInterface(),
                BusinessJpaAssertionsErrorCodes.CLASS_MUST_IMPLEMENT_A_DOMAIN_POLICY_INTERFACE
        );

    }

    private static Specification<Class<?>> parentInterfacesHasOneRepositoryInterface() {
        return new AbstractSpecification<Class<?>>() {

            @Override
            public boolean isSatisfiedBy(Class<?> candidate) {
                boolean oneInterfaceIsGenericRepo = false;
                for (Class<?> interfaceClass : candidate.getInterfaces()) {
                    try {
                        BusinessReflectionAsserts.assertRepositoryInterfaceClassIsValid(interfaceClass);
                        oneInterfaceIsGenericRepo = true;
                        break;
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
                return oneInterfaceIsGenericRepo;
            }
        };
    }

    private static Specification<Class<?>> parentInterfacesHasOneFactoryInterface() {
        return new AbstractSpecification<Class<?>>() {

            @Override
            public boolean isSatisfiedBy(Class<?> candidate) {
                boolean oneInterfaceIsGenericRepo = false;
                for (Class<?> interfaceClass : candidate.getInterfaces()) {
                    try {
                        BusinessReflectionAsserts.assertFactoryInterfaceClassIsValid(interfaceClass);
                        oneInterfaceIsGenericRepo = true;
                        break;
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
                return oneInterfaceIsGenericRepo;
            }
        };
    }

    private static Specification<Class<?>> parentInterfacesHasOneDomainServiceInterface() {
        return new AbstractSpecification<Class<?>>() {

            @Override
            public boolean isSatisfiedBy(Class<?> candidate) {
                boolean oneInterfaceIsGenericRepo = false;
                for (Class<?> interfaceClass : candidate.getInterfaces()) {
                    try {
                        BusinessReflectionAsserts.assertDomainServiceInterfaceClassIsValid(interfaceClass);
                        oneInterfaceIsGenericRepo = true;
                        break;
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
                return oneInterfaceIsGenericRepo;
            }
        };
    }

    private static Specification<Class<?>> parentInterfacesHasOneApplicationServiceInterface() {
        return new AbstractSpecification<Class<?>>() {

            @Override
            public boolean isSatisfiedBy(Class<?> candidate) {
                boolean oneInterfaceIsGenericRepo = false;
                for (Class<?> interfaceClass : candidate.getInterfaces()) {
                    try {
                        BusinessReflectionAsserts.assertApplicationServiceInterfaceClassIsValid(interfaceClass);
                        oneInterfaceIsGenericRepo = true;
                        break;
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
                return oneInterfaceIsGenericRepo;
            }
        };
    }

    private static Specification<Class<?>> parentInterfacesHasOneDomainPolicyInterface() {
        return new AbstractSpecification<Class<?>>() {

            @Override
            public boolean isSatisfiedBy(Class<?> candidate) {
                boolean oneInterfaceIsGenericRepo = false;
                for (Class<?> interfaceClass : candidate.getInterfaces()) {
                    try {
                        BusinessReflectionAsserts.assertDomainPolicyInterfaceClassIsValid(interfaceClass);
                        oneInterfaceIsGenericRepo = true;
                        break;
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
                return oneInterfaceIsGenericRepo;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <T> void $(T actual, Specification<T> specification, ErrorCode errorCode, String... messages) {
        if (!specification.isSatisfiedBy(actual)) {
            SeedException seedException = SeedException.createNew(errorCode);

            Iterator<String> it = new ArrayIterator(messages);
            while (it.hasNext()) {
                String key = it.next();
                String value = "";
                if (it.hasNext()) {
                    value = it.next();
                }
                seedException.put(key, value);
            }
            if (actual instanceof Class) {
                seedException.put("className", ((Class) actual).getName());
            }

            throw seedException;
        }
    }
}
