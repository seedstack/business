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
package org.seedstack.business.audit;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.audit.api.annotations.Audited;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.it.api.ITBind;
import org.seedstack.seed.security.api.WithUser;

@RunWith(SeedITRunner.class)
public class IntegrationTest {

    @Inject
    private AuditedMethods auditedMethods;

    @Test
    @WithUser(id = "Obiwan", password = "yodarulez")
    public void testStuff() throws Exception {
        auditedMethods.audited("pouet");
    }

    @ITBind
    public static class AuditedMethods {

        @Audited(messageBefore = "AUDITING with argument ${args[0]}........", messageAfter = "AUDITED !!!!! !${result}")
        public String audited(String someString) {
            return "this is returned";
        }
    }
}
