/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 23 juil. 2014
 */
package org.seedstack.business.audit.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.business.audit.api.Trail;

public class AuditEventTest {

    @Test
    public void testAuditEvent() {
        final String message = "message";
        Trail trail = mock(Trail.class);
        AuditEvent underTest = new AuditEvent(message, trail);
        assertThat(underTest.getMessage()).isEqualTo(message);
        assertThat(underTest.getTrail()).isEqualTo(trail);

        assertThat(underTest.getDate()).isNotNull();
        assertThat(underTest.getFormattedDate("y")).isNotNull();
    }
}
