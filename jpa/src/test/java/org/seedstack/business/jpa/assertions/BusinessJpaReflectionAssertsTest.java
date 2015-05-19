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

import org.junit.Test;
import org.seedstack.business.jpa.samples.domain.base.*;
import org.seedstack.business.jpa.samples.domain.simple.SampleSimpleFactory;
import org.seedstack.business.jpa.samples.domain.simple.SampleSimpleFactoryImpl;
import org.seedstack.business.jpa.samples.infrastructure.jpa.repository.SampleBaseJpaRepository;
import org.seedstack.business.jpa.samples.utils.Loader;
import org.seedstack.business.jpa.samples.utils.LoaderImpl;
import org.seedstack.seed.core.api.SeedException;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
public class BusinessJpaReflectionAssertsTest {

    @Test
    public void assertDefaultApplicationServiceClassIsValid() {
         BusinessJpaReflectionAsserts.assertDefaultApplicationServiceClassIsValid(LoaderImpl.class);
    }

    @Test(expected = SeedException.class)
    public void assertDefaultApplicationServiceClassIsValid_failed() {
        BusinessJpaReflectionAsserts.assertDefaultApplicationServiceClassIsValid(Loader.class);
    }

    @Test
    public void assertDefaultDomainServiceClassIsValid() {
        BusinessJpaReflectionAsserts.assertDefaultDomainServiceClassIsValid(MyDefaultDomainService.class);
    }

    @Test(expected = SeedException.class)
    public void assertDefaultDomainServiceClassIsValid_failed() {
        BusinessJpaReflectionAsserts.assertDefaultDomainServiceClassIsValid(MyDomainService.class);
    }

    @Test
    public void assertDefaultFactoryClassIsValid() {
        BusinessJpaReflectionAsserts.assertDefaultFactoryClassIsValid(SampleSimpleFactoryImpl.class);
    }

    @Test(expected = SeedException.class)
    public void assertDefaultFactoryClassIsValid_failed() {
        BusinessJpaReflectionAsserts.assertDefaultFactoryClassIsValid(SampleSimpleFactory.class);
    }

    @Test
    public void assertDomainPolicyInternalClassIsValid() {
        BusinessJpaReflectionAsserts.assertDomainPolicyInternalClassIsValid(MyDefaultDomainPolicy.class);
    }

    @Test(expected = SeedException.class)
    public void assertDomainPolicyInternalClassIsValid_failed() {
        BusinessJpaReflectionAsserts.assertDomainPolicyInternalClassIsValid(MyDomainPolicy.class);
    }

    @Test
    public void assertJpaRepositoryClassIsValid() {
        BusinessJpaReflectionAsserts.assertJpaRepositoryClassIsValid(SampleBaseJpaRepository.class);
    }

    @Test(expected = SeedException.class)
    public void assertJpaRepositoryClassIsValid_failed() {
        BusinessJpaReflectionAsserts.assertJpaRepositoryClassIsValid(SampleBaseRepository.class);
    }
}
