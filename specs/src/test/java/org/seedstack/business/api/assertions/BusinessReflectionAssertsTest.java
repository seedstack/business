/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.assertions;

import org.seedstack.business.fixtures.application.DiscountApplicationService;
import org.seedstack.business.fixtures.application.DiscountApplicationServiceInternal;
import org.seedstack.business.fixtures.domain.discount.BadValueObject;
import org.seedstack.business.fixtures.domain.discount.Discount;
import org.seedstack.business.fixtures.domain.discount.DiscountPolicy;
import org.seedstack.business.fixtures.domain.discount.Price;
import org.seedstack.business.fixtures.domain.reporting.ReportTest;
import org.seedstack.business.fixtures.domain.export.*;
import org.junit.Test;
import org.seedstack.seed.core.api.SeedException;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 18/08/2014
 */
public class BusinessReflectionAssertsTest {

    @Test
    public void assertAggregateRootClassIsValid() {
         BusinessReflectionAsserts.assertAggregateRootClassIsValid(Discount.class);
    }

    @Test(expected = SeedException.class)
    public void assertAggregateRootClassIsValid_failed() {
        BusinessReflectionAsserts.assertAggregateRootClassIsValid(ReportTest.class);
    }

    @Test
    public void assertApplicationServiceInterfaceClassIsValid() {
        BusinessReflectionAsserts.assertApplicationServiceInterfaceClassIsValid(DiscountApplicationService.class);
    }

    @Test(expected = SeedException.class)
    public void assertApplicationServiceInterfaceClassIsValid_failed() {
        BusinessReflectionAsserts.assertApplicationServiceInterfaceClassIsValid(DiscountApplicationServiceInternal.class);
    }

    @Test
    public void assertDomainPolicyInterfaceClassIsValid() {
        BusinessReflectionAsserts.assertDomainPolicyInterfaceClassIsValid(DiscountPolicy.class);
    }

    @Test(expected = SeedException.class)
    public void assertDomainPolicyInterfaceClassIsValid_failed() {
        BusinessReflectionAsserts.assertDomainPolicyInterfaceClassIsValid(Discount.class);
    }

    @Test
    public void assertDomainServiceInterfaceClassIsValid() {
        BusinessReflectionAsserts.assertDomainServiceInterfaceClassIsValid(ExportDomainService.class);
    }

    @Test(expected = SeedException.class)
    public void assertDomainServiceInterfaceClassIsValid_failed() {
        BusinessReflectionAsserts.assertDomainServiceInterfaceClassIsValid(ExportDomainServiceInternal.class);
    }

    @Test
    public void assertEntityClassIsValid() {
        BusinessReflectionAsserts.assertEntityClassIsValid(ExportItem.class);
    }

    @Test(expected = SeedException.class)
    public void assertEntityClassIsValid_failed() {
        BusinessReflectionAsserts.assertEntityClassIsValid(ExportDomainServiceInternal.class);
    }

    @Test
    public void assertFactoryInterfaceClassIsValid() {
        BusinessReflectionAsserts.assertFactoryInterfaceClassIsValid(ExportFactory.class);
    }

    @Test(expected = SeedException.class)
    public void assertFactoryInterfaceClassIsValid_failed() {
        BusinessReflectionAsserts.assertFactoryInterfaceClassIsValid(ExportDomainServiceInternal.class);
    }

    @Test
    public void assertRepositoryInterfaceClassIsValid() {
        BusinessReflectionAsserts.assertRepositoryInterfaceClassIsValid(ExportRepository.class);
    }

    @Test(expected = SeedException.class)
    public void assertRepositoryInterfaceClassIsValid_failed() {
        BusinessReflectionAsserts.assertRepositoryInterfaceClassIsValid(ExportDomainServiceInternal.class);
    }

    @Test
    public void assertValueObjectClassIsValid() {
        BusinessReflectionAsserts.assertValueObjectClassIsValid(Price.class);
    }

    @Test(expected = SeedException.class)
    public void assertValueObjectClassIsValid_failed() {
        BusinessReflectionAsserts.assertValueObjectClassIsValid(BadValueObject.class);
    }

    @Test(expected = SeedException.class)
    public void assertValueObjectClassIsValid_failed2() {
        BusinessReflectionAsserts.assertValueObjectClassIsValid(ExportFactory.class);
    }
}
