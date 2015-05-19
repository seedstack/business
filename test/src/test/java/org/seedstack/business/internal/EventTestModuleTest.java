/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.inject.Binder;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.mock;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
public class EventTestModuleTest {

    @Test
    public void test_configure_method() {
        EventTestModule underTest = new EventTestModule();
        Binder b = mock(Binder.class, Mockito.RETURNS_MOCKS);
        Whitebox.setInternalState(underTest, "binder", b);
        underTest.configure();
    }
}
