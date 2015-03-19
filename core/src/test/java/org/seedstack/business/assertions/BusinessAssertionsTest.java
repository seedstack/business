/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assertions;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 19/08/2014
 */
public class BusinessAssertionsTest {

    @Test
    public void test() {
        BusinessAssertions.assertThat(Boolean.FALSE).isFalse();
        BusinessAssertions.assertThat(Boolean.FALSE).isFalse();
        BusinessAssertions.assertThat(new BigDecimal(1)).isNotNull();
        BusinessAssertions.assertThat(new BigDecimal[]{new BigDecimal(1)}).isNotNull();
        BusinessAssertions.assertThat(1).isNotNull();
        BusinessAssertions.assertThat(new Integer(1)).isNotNull();
        BusinessAssertions.assertThat(new int[]{1, 3}).isNotNull();
        BusinessAssertions.assertThat(Long.MIN_VALUE).isNotNull();
        BusinessAssertions.assertThat(1L).isNotNull();
        BusinessAssertions.assertThat(new long[]{1L, 2L}).isNotNull();
        BusinessAssertions.assertThat(Float.MIN_VALUE).isNotNull();
        BusinessAssertions.assertThat(1F).isNotNull();
        BusinessAssertions.assertThat(new float[]{1f, 2f}).isNotNull();
        BusinessAssertions.assertThat(Double.MIN_VALUE).isNotNull();
        BusinessAssertions.assertThat(1d).isNotNull();
        BusinessAssertions.assertThat(new double[]{1d, 2d}).isNotNull();
        BusinessAssertions.assertThat(Short.MIN_VALUE).isNotNull();
        BusinessAssertions.assertThat((short)1).isNotNull();
        BusinessAssertions.assertThat(new short[]{(short)1, (short)2}).isNotNull();
        BusinessAssertions.assertThat(Character.MIN_VALUE).isNotNull();
        BusinessAssertions.assertThat('c').isNotNull();
        BusinessAssertions.assertThat(new Character[] {'c', 'b'}).isNotNull();
        BusinessAssertions.assertThat(Byte.MIN_VALUE).isNotNull();
        BusinessAssertions.assertThat((byte)1).isNotNull();
        BusinessAssertions.assertThat(new byte[] {1, 0}).isNotNull();
        BusinessAssertions.assertThat("").isNotNull();
        BusinessAssertions.assertThat(new String[] {"foo", "bar"}).isNotNull();
        BusinessAssertions.assertThat(new Date()).isNotNull();
        BusinessAssertions.assertThat(new RuntimeException()).isNotNull();
        BusinessAssertions.assertThat(new ArrayList<Object>()).isNotNull();
        BusinessAssertions.assertThat(new HashMap<String, Object>()).isNotNull();
        BusinessAssertions.assertThat(new Object()).isNotNull();
        BusinessAssertions.assertThat(Object.class).isNotNull();
        BusinessAssertions.assertThat(new File("temps")).isNotNull();
        BusinessAssertions.assertThat(new ByteArrayInputStream(new byte[]{})).isNotNull();
    }
}
