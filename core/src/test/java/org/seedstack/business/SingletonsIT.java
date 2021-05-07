/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.fixtures.application.IndexService;
import org.seedstack.business.fixtures.application.SingletonService;
import org.seedstack.seed.testing.junit4.SeedITRunner;

/**
 * This class checks the injection of new instances or singletons.
 */
@RunWith(SeedITRunner.class)
public class SingletonsIT {
    @Inject
    private SingletonService singletonService;

    @Inject
    private SingletonService singletonService2;

    @Inject
    private IndexService indexService;

    @Inject
    private IndexService indexService2;

    @Test
    public void singleton_injection_should_work() {
        Assertions.assertThat(singletonService)
                .isEqualTo(singletonService2);
    }

    @Test
    public void default_scope_injection_should_work() {
        Assertions.assertThat(indexService)
                .isNotEqualTo(indexService2);
    }
}
