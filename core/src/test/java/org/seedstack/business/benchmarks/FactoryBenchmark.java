/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.benchmarks;

import javax.inject.Inject;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.fixtures.factory.MyFactoryAggregate;
import org.seedstack.business.fixtures.identity.MyAggregateFactory;
import org.seedstack.jmh.AbstractBenchmark;

@State(Scope.Benchmark)
public class FactoryBenchmark extends AbstractBenchmark {

    @Inject
    private Factory<MyFactoryAggregate> myAggregateAutoFactory;
    @Inject
    private MyAggregateFactory myAggregateFactory;

    @Benchmark
    public void createWithoutIdentityManagement() {
        myAggregateAutoFactory.create("id");
    }

    @Benchmark
    public void createWithIdentityManagement() {
        myAggregateFactory.createMyAggregate();
    }

    @Benchmark
    public void createWithComplexIdentityManagement() {
        myAggregateFactory.createMyAggregate("someName");
    }
}
