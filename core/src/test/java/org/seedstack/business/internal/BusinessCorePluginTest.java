/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import io.nuun.kernel.api.plugin.RoundEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.DomainSpecifications;
import org.seedstack.business.fixtures.domain.discount.Discount;

import static org.mockito.Mockito.mock;

/**
 * Test the the BusinessCorePlugin.
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class BusinessCorePluginTest {

    BusinessCorePlugin underTestBusinessCorePlugin;

    @Before
    public void setUp() {
        underTestBusinessCorePlugin = new BusinessCorePlugin();
        mockFirstRound();
    }

    private void mockFirstRound() {
        RoundEnvironment environment = mock(RoundEnvironment.class);
        Mockito.when(environment.firstRound()).thenReturn(true);
        underTestBusinessCorePlugin.provideRoundEnvironment(environment);
    }

    @Test
    public void test_classpathScanRequests() {
        underTestBusinessCorePlugin.classpathScanRequests();
        // TODO mock all the list to do the second round
    }

    @Test
    public void aggregateSpecification_works_fine() {
        Assertions.assertThat(DomainSpecifications.AGGREGATE_ROOT.isSatisfiedBy(Discount.class)).isTrue();
    }

}
