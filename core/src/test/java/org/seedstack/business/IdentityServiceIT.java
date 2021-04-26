/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business;

import java.util.UUID;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.IdentityExistsException;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.fixtures.identity.MyAggregate;
import org.seedstack.business.fixtures.identity.MyAggregateWithBadIdentityManagement;
import org.seedstack.business.fixtures.identity.MyAggregateWithConfiguration;
import org.seedstack.business.fixtures.identity.MyAggregateWithNoIdentityManagement;
import org.seedstack.business.fixtures.identity.embedded.MyAggregateWithCompositeId;
import org.seedstack.business.fixtures.identity.embedded.MyCompositeId;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class IdentityServiceIT {

    @Inject
    private IdentityService identityService;

    @Test
    public void identify_entity() {
        MyAggregate myAggregate = new MyAggregate();
        identityService.identify(myAggregate);
        Assertions.assertThat(myAggregate.getId())
                .isNotNull();
    }

    @Test(expected = IdentityExistsException.class)
    public void allready_identify_entity() {
        MyAggregate myAggregate = new MyAggregate(UUID.randomUUID());
        identityService.identify(myAggregate);
        Assertions.fail("no exception occured");
    }

    @Test
    public void identify_entity_with_configuration() {
        MyAggregateWithConfiguration myAggregate = new MyAggregateWithConfiguration();
        identityService.identify(myAggregate);
        Assertions.assertThat(myAggregate.getId())
                .isNotNull();
    }

    @Test
    public void aggregate_with_bad_identity_Management() {
        MyAggregateWithBadIdentityManagement myAggregate = new MyAggregateWithBadIdentityManagement();
        try {
            identityService.identify(myAggregate);
            Assertions.fail("no exception occurred");
        } catch (BusinessException e) {
            Assertions.assertThat(e.getErrorCode())
                    .isEqualTo(BusinessErrorCode.INCOMPATIBLE_IDENTITY_TYPES);
        }
    }

    @Test
    public void aggregate_with_no_identity_Management() {
        MyAggregateWithNoIdentityManagement myAggregate = new MyAggregateWithNoIdentityManagement();
        identityService.identify(myAggregate);
    }

    @Test
    public void test_embedded_identity(){
        final String ID_TEST_STRING="ID_TEST_STRING";
        final String ID_TEST_DESCRIPTION="ID_TEST_DESCRIPTION";
        MyCompositeId identifier = new MyCompositeId(ID_TEST_STRING);
        MyCompositeId identifier2 = new MyCompositeId(ID_TEST_STRING);
        MyAggregateWithCompositeId aggregate = new MyAggregateWithCompositeId(identifier,ID_TEST_DESCRIPTION);
        MyAggregateWithCompositeId aggregate2 = new MyAggregateWithCompositeId(identifier2,ID_TEST_DESCRIPTION);
        identityService.identify(aggregate);
        identityService.identify(aggregate2);
        Assertions.assertThat(aggregate.getId()).isNotNull();
        Assertions.assertThat(aggregate.getId().getSeq()).isEqualTo(Long.valueOf(0));
        Assertions.assertThat(aggregate2.getId()).isNotNull();
        Assertions.assertThat(aggregate2.getId().getSeq()).isEqualTo(Long.valueOf(1));

        boolean exceptionThrown=false;
        try{
            identityService.identify(aggregate);
        }
        catch(IdentityExistsException identityExistsException){
            Assertions.assertThat(identityExistsException.getMessage()).isEqualTo("Composite identifier MyCompositeId[aTextInTheId=ID_TEST_STRING,seq=0] already has an identity");
            exceptionThrown=true;
        }
        Assertions.assertThat(exceptionThrown).isTrue();
    }
}
