/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.automode;

import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.Assemblers;
import org.seedstack.business.jpa.samples.domain.tinyaggregate.TinyAggRoot;
import org.seedstack.business.jpa.samples.domain.tinyaggregate.TinyDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 26/09/2014
 */
@Transactional
@RunWith(SeedITRunner.class)
public class AutoRepositoriesIT {

    @Inject
    Repository<TinyAggRoot, String> repository;

    @Inject
    Factory<TinyAggRoot> factory;

    @Inject
    Assemblers assemblers;

    @Test
    public void retrieve_aggregate_from_repository () {
        repository.save(factory.create("hello"));
        TinyAggRoot tinyAggRoot = repository.load("hello");
        Assertions.assertThat(tinyAggRoot).isNotNull();
    }

    @Test
    public void retrieve_aggregate_with_assemblers() {
        TinyDto tinyDto = new TinyDto("id", "name");
        TinyAggRoot tinyAggRoot = assemblers.createThenMergeAggregateWithDto(tinyDto, TinyAggRoot.class);
        repository.save(tinyAggRoot);
        TinyAggRoot result = repository.load("id");
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(tinyAggRoot);

        TinyDto tinyDto2 = new TinyDto("id", "name2");
        tinyAggRoot = assemblers.retrieveOrCreateThenMergeAggregateWithDto(tinyDto2, TinyAggRoot.class);
        result = repository.load("id");
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(tinyAggRoot);
        Assertions.assertThat(result.getName()).isEqualTo(tinyDto2.getName());

    }
}
