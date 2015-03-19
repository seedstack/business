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

import org.junit.Test;
import org.seedstack.business.audit.api.Initiator;

public class InitiatorTest {

    @Test
    public void testInitiator_localhost() {
        final String id = "id";
        final String name = "name";
        String ipAddress = "127.0.0.1";
        Initiator initiator = new Initiator(id, name, ipAddress);
        assertThat(initiator.getId()).isEqualTo(id);
        assertThat(initiator.getName()).isEqualTo(name);
        assertThat(initiator.getAddress()).isNotNull();
    }

    @Test
    public void testInitiator_unknownHost() {
        final String id = "id";
        final String name = "name";
        String ipAddress = "dummy";
        Initiator initiator = new Initiator(id, name, ipAddress);
        assertThat(initiator.getId()).isEqualTo(id);
        assertThat(initiator.getName()).isEqualTo(name);
        assertThat(initiator.getAddress()).isNull();
    }
}
