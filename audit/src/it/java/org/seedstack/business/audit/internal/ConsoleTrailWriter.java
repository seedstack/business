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
 * Creation : 28 juil. 2014
 */
package org.seedstack.business.audit.internal;

import javax.el.ELContext;
import javax.inject.Inject;

import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.business.audit.spi.TrailWriter;
import org.seedstack.seed.el.api.ELContextBuilder;
import org.seedstack.seed.el.api.ELService;

public class ConsoleTrailWriter implements TrailWriter {

    @Inject
    private ELService elService;

    @Inject
    private ELContextBuilder elContextBuilder;

    private final static String FORMAT = "At ${event.getFormattedDate(\"yyyy/MM/dd HH:mm:ss.SSS\")} user ${initiator.getName()} - ${initiator.getId()} requested application ${host.getName()} : ${event.getMessage()}";

    @Override
    public void writeEvent(AuditEvent auditEvent) {
        ELContext elContext = elContextBuilder.defaultContext().withProperty("event", auditEvent).withProperty("trail", auditEvent.getTrail())
                .withProperty("initiator", auditEvent.getTrail().getInitiator()).withProperty("host", auditEvent.getTrail().getHost()).build();
        System.out.println(elService.withExpression(FORMAT, String.class).withContext(elContext).asValueExpression().eval());
    }

}
