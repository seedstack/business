/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.internal;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.business.audit.api.AuditService;
import org.seedstack.business.audit.api.Host;
import org.seedstack.business.audit.api.Initiator;
import org.seedstack.business.audit.api.Trail;
import org.seedstack.business.audit.spi.TrailWriter;
import org.seedstack.seed.core.api.Application;
import org.seedstack.seed.security.api.SecuritySupport;
import org.seedstack.seed.security.api.principals.Principals;
import org.seedstack.seed.security.api.principals.SimplePrincipalProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of AuditService.
 * 
 * @author U236838
 */
public class DefaultAuditService implements AuditService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuditService.class);

    private static Host host;

    private static AtomicLong trailIds = new AtomicLong(0);

    @Inject
    private Application application;

    @Inject
    private SecuritySupport securitySupport;

    @Inject
    private Set<TrailWriter> trailWriters;

    @Override
    public Trail createTrail() {
        return new Trail(trailIds.getAndIncrement(), createInitiator(), getHost());
    }

    @Override
    public void trail(String message, Trail trail) {
        AuditEvent auditEvent = new AuditEvent(message, trail);
        for (TrailWriter writer : trailWriters) {
            writer.writeEvent(auditEvent);
        }
    }

    private Initiator createInitiator() {
        Initiator initiator;
        if (securitySupport.isAuthenticated()) {
            SimplePrincipalProvider fullNamePrincipal = securitySupport.getSimplePrincipalByName(Principals.FULL_NAME);
            String fullName;
            if (fullNamePrincipal == null) {
                fullName = "";
            } else {
                fullName = fullNamePrincipal.getValue();
            }
            initiator = new Initiator(securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getValue(), fullName, securitySupport.getHost());
        } else {
            LOG.warn("An audited code is being run by an unauthenticated user");
            initiator = new Initiator("unknown user id", "unknow user name", securitySupport.getHost());
        }
        return initiator;
    }

    void initHost() {
        host = new Host(application.getId(), application.getName());
    }

    Host getHost() {
        if (host == null) {
            initHost();
        }
        return host;
    }
}
