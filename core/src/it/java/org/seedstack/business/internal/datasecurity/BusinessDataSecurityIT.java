/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.datasecurity;

import org.seedstack.business.internal.datasecurity.sample.Dummy;
import org.seedstack.business.internal.datasecurity.sample.DummyFactory;
import org.seedstack.business.internal.datasecurity.sample.DummyService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.api.WithUser;

import javax.inject.Inject;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
@RunWith(SeedITRunner.class)
public class BusinessDataSecurityIT {
	
	@Inject
	DummyService dummyService ;
	
	
	
	@Test
	@WithUser(id = "Anakin", password = "imsodark" )
	public void checking_data_security_interceptions_patterns() {
		
		Dummy dummy11 = DummyFactory.create(11);
		Dummy dummy12 = DummyFactory.create(12);
		Dummy dummy13 = DummyFactory.create(13);
		Dummy dummy21 = DummyFactory.create(21);
		
		Dummy dummyReturn1 = dummyService.service1(dummy11, dummy12, dummy13);
		
		Dummy dummyReturn2 = dummyService.service2(dummy21);
		
		assertDummySecured(1, dummyReturn1);
		assertDummy(2, dummyReturn2);
		
		assertDummySecured(12, dummy12);
		assertDummy(13, dummy13);
		assertDummySecured(11, dummy11);
	}
	
	void assertDummy (Integer i, Dummy d) {
		Assertions.assertThat(d.getDummy1()).isEqualTo("dummy1-" + i);
		Assertions.assertThat(d.getDummy2()).isEqualTo(Long.valueOf(i));
		Assertions.assertThat(d.getDummy3()).isTrue();
		Assertions.assertThat(d.getDummy4()).isEqualTo("dummy4-" + i);
	}
	
	void assertDummySecured (Integer i, Dummy d) {
		Assertions.assertThat(d.getDummy1()).isEqualTo("obfuscated!!");
		Assertions.assertThat(d.getDummy2()).isEqualTo(Long.valueOf(0));
		Assertions.assertThat(d.getDummy3()).isFalse();
		Assertions.assertThat(d.getDummy4()).isEqualTo("");
	}
	
	
	
	
	

}
