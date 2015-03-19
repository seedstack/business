/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the machine and application
 * 
 * @author U236838
 */
public class Host {

    private static final Logger LOG = LoggerFactory.getLogger(Host.class);

    private final String name;

    private final String id;

    private Address address;

    /**
     * Constructor where hostname is given. If hostname is unknown, address will be null.
     * 
     * @param id functional id of the host
     * @param name functional name of the host
     * @param hostName hostname
     */
    public Host(String id, String name, String hostName) {
        this.id = id;
        this.name = name;
        try {
            this.address = new Address(hostName);
        } catch (UnknownHostException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(e.getMessage(), e);
            }
            this.address = null;
            LOG.info("Could not find application server hostname : {}", e.getMessage());
        }
    }

    /**
     * Constructor without hostname. Hostname will be resolved with localhost
     * 
     * @param id functional id of the host
     * @param name functional name of the host
     */
    public Host(String id, String name) {
        this.id = id;
        this.name = name;
        try {
            this.address = new Address(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(e.getMessage(), e);
            }
            this.address = null;
            LOG.info("Could not find application server hostname : {}", e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the address.
     * 
     * @return the Address. Null if unknown.
     */
    public Address getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }
}
