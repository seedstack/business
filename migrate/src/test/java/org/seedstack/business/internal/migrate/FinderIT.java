/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.fixtures.FinderImpl1;
import org.seedstack.business.fixtures.FinderImpl2;
import org.seedstack.business.fixtures.MyFinder;
import org.seedstack.business.fixtures.Q1;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class FinderIT {
    @Inject
    private MyFinder myFinder;
    @Inject
    @Q1
    private MyFinder myQ1Finder;

    @Test
    public void findersAreInjected() {
        assertThat(myQ1Finder).isInstanceOf(FinderImpl1.class);
        assertThat(myFinder).isInstanceOf(FinderImpl2.class);
    }
}
