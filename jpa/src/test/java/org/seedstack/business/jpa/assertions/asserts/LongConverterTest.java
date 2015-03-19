/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.assertions.asserts;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.LongConverter;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;

public class LongConverterTest {

	private static final Long LONG_DIX = 10L;
	private static final int DIX = 10;
	@Test
	public void longconverter_test(){
		LongConverter converter = new LongConverter();
		Assertions.assertThat(LONG_DIX).isEqualTo(converter.convert(Long.class, new BigDecimal(DIX)));
		Assertions.assertThat(LONG_DIX).isEqualTo(converter.convert(Long.class, Long.valueOf(DIX)));
		Assertions.assertThat(LONG_DIX).isEqualTo(converter.convert(Long.class, Integer.valueOf(DIX)));
		Assertions.assertThat(LONG_DIX).isEqualTo(converter.convert(Long.class, LONG_DIX.toString()));
		Assertions.assertThat(LONG_DIX).isEqualTo(converter.convert(Long.class, 10));

	}
	
	@Test(expected = ConversionException.class)
	public void longconverter_exception_test(){
		LongConverter converter = new LongConverter();
		Assertions.assertThat(LONG_DIX).isEqualTo(converter.convert(Long.class, "fffff"));
	
	}

}
