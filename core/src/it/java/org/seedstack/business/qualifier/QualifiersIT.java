/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.qualifier;

import org.seedstack.business.qualifier.fixtures.application.MyApplicationService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.qualifier.fixtures.domain.*;
import org.seedstack.business.qualifier.fixtures.interfaces.*;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import javax.inject.Named;


@RunWith(SeedITRunner.class)
public class QualifiersIT {

    @Inject @Named("1")
    MyApplicationService myApplicationService1;
    @Inject @Named("1")
    MyApplicationService myApplicationService2;
    @Inject
    MyDomainPolicy<String> myDomainPolicy1; // check with user's generic interface
    @Inject @Named("2")
    MyDomainPolicy<Integer> myDomainPolicy2;
    @Inject
    MyDomainService myDomainService1;  // case with user's abstract class
    @Inject @Named("2")
    MyDomainService myDomainService2;
    @Inject @Q1
    MyInterfaceService myInterfaceService1;
    @Inject
    MyInterfaceService myInterfaceService2;
    @Inject
    MyFactory myFactory1;
    @Inject @Named("2")
    MyFactory myFactory2;
    @Inject @Q1
    MyFinder myFinder1;
    @Inject
    MyFinder myFinder2;

    @Test
    public void qualified_implementation_should_be_injected() {
        Assertions.assertThat(myApplicationService1).isNotNull();
        Assertions.assertThat(myApplicationService2).isNotNull();
        Assertions.assertThat(myDomainPolicy1).isNotNull();
        Assertions.assertThat(myDomainPolicy2).isNotNull();
        Assertions.assertThat(myDomainService1).isNotNull();
        Assertions.assertThat(myDomainService2).isNotNull();
        Assertions.assertThat(myInterfaceService1).isNotNull();
        Assertions.assertThat(myInterfaceService2).isNotNull();
        Assertions.assertThat(myFactory1).isNotNull();
        Assertions.assertThat(myFactory1).isInstanceOf(FactImpl1.class);
        Assertions.assertThat(myFactory2).isNotNull();
        Assertions.assertThat(myFactory2).isInstanceOf(FactImpl2.class);
        Assertions.assertThat(myFinder1).isNotNull();
        Assertions.assertThat(myFinder1).isInstanceOf(FinderImpl1.class);
        Assertions.assertThat(myFinder2).isNotNull();
        Assertions.assertThat(myFinder2).isInstanceOf(FinderImpl2.class);
    }

}
