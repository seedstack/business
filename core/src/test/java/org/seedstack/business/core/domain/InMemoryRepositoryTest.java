/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.domain;

import org.seedstack.business.internal.BusinessCorePlugin;
import org.junit.Ignore;
import org.junit.Test;
import org.seedstack.business.fixtures.InMemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class InMemoryRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(BusinessCorePlugin.class);

	@Test
    @Ignore
	public void test() {
		Field[] declaredFields = InMemoryRepository.class.getDeclaredFields();
		for (Field field : declaredFields)
		{
			System.out.println(field);
		}
		
		assertThat(declaredFields).hasSize(2);
	}

}
