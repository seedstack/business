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

/**
 * A Trail regroups several events for one user action.
 * 
 * @author U236838
 */
public class Trail {

    private final long id;

    private final Initiator initiator;

    private final Host host;

    /**
     * Constructor
     * 
     * @param id id of the trail
     * @param initiator initiator of the trail
     * @param host Host on which the trail is created
     */
    public Trail(long id, Initiator initiator, Host host) {
        this.id = id;
        this.initiator = initiator;
        this.host = host;
    }

    public long getId() {
        return id;
    }

    public Initiator getInitiator() {
        return initiator;
    }

    public Host getHost() {
        return host;
    }

    @Override
    public int hashCode() {
        final int prime = 23;
        int result = 3;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Trail other = (Trail) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
