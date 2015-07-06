/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.reporting;

import org.seedstack.business.api.domain.AggregateRoot;
import org.junit.Test;
import org.mockito.Mockito;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.powermock.reflect.Whitebox;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author epo.jemba@ext.mpsa.com
 */
public class ReportTest {

    @Test(expected = StackOverflowError.class)
    public void mock_aggregate_root() {
        Report report = Mockito.mock(Report.class);
        Mockito.when(report.getEntityId()).thenReturn("report1");
        assertThat(report.getEntityId()).isEqualTo("report1");
    }

    @Test
    public void mock_aggregate_root_good_way() {

        Report report = aggregateInstanceForTest("report1", Report.class);

        assertThat(report.getEntityId()).isEqualTo("report1");
    }

    /**
     * Give back an aggregate root for test.
     *
     * @param entityId      the entity id
     * @param aggregateType the aggregate type
     * @return the Aggregate root updated.
     */
    protected <AGGREGATE_ROOT extends AggregateRoot<ID>, ID> AGGREGATE_ROOT aggregateInstanceForTest(ID entityId, Class<AGGREGATE_ROOT> aggregateType) {
        final String ENTITY_ID = "entityId";
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator aggregateInstantiator = objenesis.getInstantiatorOf(aggregateType);
        Object aggregateRoot = aggregateInstantiator.newInstance();
        Whitebox.setInternalState(aggregateRoot, ENTITY_ID, entityId);
        return aggregateType.cast(aggregateRoot);
    }


}
