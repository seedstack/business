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
import org.seedstack.business.audit.api.Host;
import org.seedstack.business.audit.api.Initiator;
import org.seedstack.business.audit.api.Trail;

public class TrailTest {

    @Test
    public void testTrail() {
        final long id = 36;
        Initiator initiator = mock(Initiator.class);
        Host host = mock(Host.class);
        Trail trail = new Trail(id, initiator, host);
        assertThat(trail.getId()).isEqualTo(id);
        assertThat(trail.getInitiator()).isEqualTo(initiator);
        assertThat(trail.getHost()).isEqualTo(host);

        assertThat(trail.equals(trail)).isTrue();
        assertThat(trail.equals(null)).isFalse();

        Trail trail2 = new Trail(id, null, null);
        assertThat(trail).isEqualTo(trail2);
        assertThat(trail.hashCode()).isEqualTo(trail2.hashCode());

        Trail trail3 = new Trail(12, null, null);
        assertThat(trail).isNotEqualTo(trail3);

        assertThat(trail.equals(new Object())).isFalse();
    }
}
