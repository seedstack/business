/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.utils;

import org.seedstack.business.jpa.samples.domain.simple.SampleSimpleFactory;
import org.seedstack.business.jpa.samples.domain.simple.SampleSimpleJpaAggregateRoot;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class LoaderImpl implements Loader {

	@Inject
	EntityManager entityManager;
	
	@Inject 
	SampleSimpleFactory sampleSimpleFactory;

	@Override
	@Transactional
	public void init(Scenario scenario) 
	{
		switch (scenario) {
		case ONE:
			for (int i = 0; i < (10000 - 9); i++) {
				String f2 =  i % 2 == 0 ? "odd" : "even";
				String f4= (f2.equals("odd")  ? "" + (i/2) : "");
				SampleSimpleJpaAggregateRoot aRoot =  sampleSimpleFactory
						.createSampleSimpleJpaAggregateRoot( i  , "f1-"+(i % 2 == 0 ? i : i - 1), f2, new Date(), f4);
				entityManager.persist(aRoot);
			}
			break;
			
		case TWO:
			
			break;

		default:
			break;
		}
		
		
	}

}
