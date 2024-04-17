/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import javax.inject.Inject;
import javax.inject.Named;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.fixtures.qualifier.application.MyApplicationService;
import org.seedstack.business.fixtures.qualifier.domain.FactImpl1;
import org.seedstack.business.fixtures.qualifier.domain.FactImpl2;
import org.seedstack.business.fixtures.qualifier.domain.MyDomainPolicy;
import org.seedstack.business.fixtures.qualifier.domain.MyDomainService;
import org.seedstack.business.fixtures.qualifier.domain.MyFactory;
import org.seedstack.business.fixtures.qualifier.interfaces.MyInterfaceService;
import org.seedstack.business.fixtures.qualifier.interfaces.Q1;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class QualifiersIT {
    @Inject
    @Named("1")
    MyApplicationService myApplicationService1;
    @Inject
    @Named("1")
    MyApplicationService myApplicationService2;
    @Inject
    MyDomainPolicy<String> myDomainPolicy1; // check with user's generic interface
    @Inject
    @Named("2")
    MyDomainPolicy<Integer> myDomainPolicy2;
    @Inject
    MyDomainService myDomainService1;  // case with user's abstract class
    @Inject
    @Named("2")
    MyDomainService myDomainService2;
    @Inject
    @Q1
    MyInterfaceService myInterfaceService1;
    @Inject
    MyInterfaceService myInterfaceService2;
    @Inject
    MyFactory myFactory1;
    @Inject
    @Named("2")
    MyFactory myFactory2;

    @Test
    public void qualified_implementation_should_be_injected() {
        Assertions.assertThat(myApplicationService1)
                .isNotNull();
        Assertions.assertThat(myApplicationService2)
                .isNotNull();
        Assertions.assertThat(myDomainPolicy1)
                .isNotNull();
        Assertions.assertThat(myDomainPolicy2)
                .isNotNull();
        Assertions.assertThat(myDomainService1)
                .isNotNull();
        Assertions.assertThat(myDomainService2)
                .isNotNull();
        Assertions.assertThat(myInterfaceService1)
                .isNotNull();
        Assertions.assertThat(myInterfaceService2)
                .isNotNull();
        Assertions.assertThat(myFactory1)
                .isNotNull();
        Assertions.assertThat(myFactory1)
                .isInstanceOf(FactImpl1.class);
        Assertions.assertThat(myFactory2)
                .isNotNull();
        Assertions.assertThat(myFactory2)
                .isInstanceOf(FactImpl2.class);
    }

}
