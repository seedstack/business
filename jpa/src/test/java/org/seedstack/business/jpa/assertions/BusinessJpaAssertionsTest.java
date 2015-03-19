/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.assertions;

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
public class BusinessJpaAssertionsTest {

    @Test
    public void testAssetions() {
        BusinessJpaAssertions.assertThat(Boolean.FALSE).isFalse();
        BusinessJpaAssertions.assertThat(Boolean.FALSE).isFalse();
        BusinessJpaAssertions.assertThat(new BigDecimal(1)).isNotNull();
        BusinessJpaAssertions.assertThat(new BigDecimal[]{new BigDecimal(1)}).isNotNull();
        BusinessJpaAssertions.assertThat(1).isNotNull();
        BusinessJpaAssertions.assertThat(new Integer(1)).isNotNull();
        BusinessJpaAssertions.assertThat(new int[]{1, 3}).isNotNull();
        BusinessJpaAssertions.assertThat(Long.MIN_VALUE).isNotNull();
        BusinessJpaAssertions.assertThat(1L).isNotNull();
        BusinessJpaAssertions.assertThat(new long[]{1L, 2L}).isNotNull();
        BusinessJpaAssertions.assertThat(Float.MIN_VALUE).isNotNull();
        BusinessJpaAssertions.assertThat(1F).isNotNull();
        BusinessJpaAssertions.assertThat(new float[]{1f, 2f}).isNotNull();
        BusinessJpaAssertions.assertThat(Double.MIN_VALUE).isNotNull();
        BusinessJpaAssertions.assertThat(1d).isNotNull();
        BusinessJpaAssertions.assertThat(new double[]{1d, 2d}).isNotNull();
        BusinessJpaAssertions.assertThat(Short.MIN_VALUE).isNotNull();
        BusinessJpaAssertions.assertThat((short)1).isNotNull();
        BusinessJpaAssertions.assertThat(new short[]{(short)1, (short)2}).isNotNull();
        BusinessJpaAssertions.assertThat(Character.MIN_VALUE).isNotNull();
        BusinessJpaAssertions.assertThat('c').isNotNull();
        BusinessJpaAssertions.assertThat(new Character[] {'c', 'b'}).isNotNull();
        BusinessJpaAssertions.assertThat(Byte.MIN_VALUE).isNotNull();
        BusinessJpaAssertions.assertThat((byte)1).isNotNull();
        BusinessJpaAssertions.assertThat(new byte[] {1, 0}).isNotNull();
        BusinessJpaAssertions.assertThat("").isNotNull();
        BusinessJpaAssertions.assertThat(new String[] {"foo", "bar"}).isNotNull();
        BusinessJpaAssertions.assertThat(new Date()).isNotNull();
        BusinessJpaAssertions.assertThat(new RuntimeException()).isNotNull();
        BusinessJpaAssertions.assertThat(new ArrayList<Object>()).isNotNull();
        BusinessJpaAssertions.assertThat(new HashMap<String, Object>()).isNotNull();
        BusinessJpaAssertions.assertThat(new Object()).isNotNull();
        BusinessJpaAssertions.assertThat(Object.class).isNotNull();
        BusinessJpaAssertions.assertThat(new File("temps")).isNotNull();
        BusinessJpaAssertions.assertThat(new ByteArrayInputStream(new byte[]{})).isNotNull();
    }
}
