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
import org.seedstack.business.audit.api.Host;

public class HostTest {

    @Test
    public void hostTest_unknownHost() {
        final String id = "id";
        final String name = "name";
        Host host = new Host(id, name, "dummy");
        assertThat(host.getId()).isEqualTo(id);
        assertThat(host.getName()).isEqualTo(name);
        assertThat(host.getAddress()).isNull();
    }

    @Test
    public void hostTest_knownHost() {
        final String id = "id";
        final String name = "name";
        Host host = new Host(id, name, "localhost");
        assertThat(host.getId()).isEqualTo(id);
        assertThat(host.getName()).isEqualTo(name);
        assertThat(host.getAddress()).isNotNull();
    }

    @Test
    public void hostTest_localhost() {
        final String id = "id";
        final String name = "name";
        Host host = new Host(id, name);
        assertThat(host.getId()).isEqualTo(id);
        assertThat(host.getName()).isEqualTo(name);
        assertThat(host.getAddress()).isNotNull();
    }
}
