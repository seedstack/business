/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import com.google.inject.Binder;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

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
