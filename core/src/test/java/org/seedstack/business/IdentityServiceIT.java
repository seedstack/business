/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.fixtures.identity.MyAggregate;
import org.seedstack.business.fixtures.identity.MyAggregateWithBadIdentityManagement;
import org.seedstack.business.fixtures.identity.MyAggregateWithNoIdentityManagement;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import java.util.UUID;

@RunWith(SeedITRunner.class)
public class IdentityServiceIT {
    @Inject
    private IdentityService identityService;

    @Test
    public void identify_entity() {
        MyAggregate myAggregate = new MyAggregate();
        identityService.identify(myAggregate);
        Assertions.assertThat(myAggregate.getId()).isNotNull();
    }

    @Test
    public void allready_identify_entity() {
        MyAggregate myAggregate = new MyAggregate(UUID.randomUUID());
        try {
            identityService.identify(myAggregate);
            Assertions.fail("no exception occured");
        } catch (BusinessException e) {
            Assertions.assertThat(BusinessErrorCode.ENTITY_ALREADY_HAS_AN_IDENTITY)
                    .isEqualTo(e.getErrorCode());
        }
    }

    @Test
    public void aggregate_with_bad_identity_Management() {
        MyAggregateWithBadIdentityManagement myAggregate = new MyAggregateWithBadIdentityManagement();
        try {
            identityService.identify(myAggregate);
            Assertions.fail("no exception occured");
        } catch (BusinessException e) {
            Assertions.assertThat(BusinessErrorCode.IDENTITY_TYPE_CANNOT_BE_GENERATED)
                    .isEqualTo(e.getErrorCode());
        }
    }

    @Test
    public void aggregate_with_no_identity_Management() {
        MyAggregateWithNoIdentityManagement myAggregate = new MyAggregateWithNoIdentityManagement();
        try {

            identityService.identify(myAggregate);
            Assertions.fail("no exception occured");
        } catch (BusinessException e) {
            Assertions.assertThat(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                    .isEqualTo(e.getErrorCode());
        }
    }
}
