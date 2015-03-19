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
 * Creation : 22 juil. 2014
 */
package org.seedstack.business.audit.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.UnknownHostException;

import org.junit.Test;
import org.seedstack.business.audit.api.Address;

public class AddressTest {

    @Test(expected = UnknownHostException.class)
    public void testAddress_unknownHost() throws UnknownHostException {
        new Address("dummy");
    }

    @Test
    public void testAddress_localhost() throws UnknownHostException {
        Address address = new Address("localhost");
        assertThat(address.getIpAddress()).isEqualTo("127.0.0.1");
        assertThat(address.getDnsName()).isEqualTo("localhost");
    }
}
