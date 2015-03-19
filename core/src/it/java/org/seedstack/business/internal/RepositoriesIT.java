/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.seedstack.business.api.domain.Repositories;
import org.seedstack.business.api.domain.auto.repository.DefaultRepoSample;
import org.seedstack.business.api.domain.auto.repository.TestAggregate;
import org.seedstack.business.sample.domain.multi.Multi;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.seedstack.seed.core.api.SeedException;
import org.seedstack.seed.it.AbstractSeedIT;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;


public class RepositoriesIT extends AbstractSeedIT {

	@Inject
	Repositories repo;

	@Test
	public void find_Repositories_should_work() {
		assertThat(repo).isNotNull();
		assertThat(repo.findRepositories()).isNotNull();
		assertThat(repo.findRepositories().size()).isGreaterThan(4);
		
		assertThat( Whitebox.getInternalState(repo, "injector") ).isNotNull();
	}

	@Test
	public void find_Repositories_should_work2() {
		assertThat(repo).isNotNull();
		assertThat(repo.findRepositories()).isNotNull();
		assertThat(repo.findRepositories().size()).isGreaterThan(4);
		assertThat( Whitebox.getInternalState(repo, "injector") ).isNotNull();
	}

    @Test
    @Ignore("Make this test with the new classpath test fixture of nuun.io")
    public void get_repository_should_work_with_default_repository() {
        // It should have only one default implementation to work.
        assertThat(repo).isNotNull();
        assertThat(repo.get(TestAggregate.class)).isNotNull();
        assertThat(repo.get(TestAggregate.class)).isInstanceOf(DefaultRepoSample.class);
        assertThat(repo.get(TestAggregate.class)).isNotExactlyInstanceOf(DefaultRepoSample.class);
    }

    @Test(expected = SeedException.class)
    public void get_repository_should_fail_with_multiple_repository() {
        repo.get(Multi.class);
    }

}
