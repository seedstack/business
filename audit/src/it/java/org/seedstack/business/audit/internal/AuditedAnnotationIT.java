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
 * Creation : 30 juin 2014
 */
package org.seedstack.business.audit.internal;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.audit.api.annotations.Audited;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.it.api.ITBind;
import org.seedstack.seed.security.api.WithUser;
import org.seedstack.seed.security.api.exceptions.AuthorizationException;

@RunWith(SeedITRunner.class)
public class AuditedAnnotationIT {

    @Inject
    private AuditedMethods auditedMethods;

    @Test(expected = AuthorizationException.class)
    @WithUser(id = "Obiwan", password = "yodarulez")
    public void testStuff() throws Exception {
        auditedMethods.audited("pouet");
        auditedMethods.auditedWithException();
    }

    @ITBind
    public static class AuditedMethods {

        @Audited(messageBefore = "AUDITING with argument ${args[0]}........", messageAfter = "AUDITED !!!!! !${result}")
        public String audited(String someString) {
            reaudited();
            return "this is returned";
        }

        @Audited(messageBefore = "REAUDITING...", messageAfter = "REAUDITED !!!!!! ")
        public void reaudited() {

        }

        @Audited(messageBefore = "AUDITING EXCEPTION...", messageAfter = "AUDITED !!!!!!", messageOnException = "OMG I Crashed !! ${exception.getMessage()}")
        public void auditedWithException() throws Exception {
            throw new AuthorizationException("what a crash");
        }
    }

}
