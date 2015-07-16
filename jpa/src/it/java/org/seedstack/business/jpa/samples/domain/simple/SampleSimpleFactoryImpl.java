/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.domain.simple;

import org.seedstack.business.api.domain.BaseFactory;

import javax.validation.Valid;
import java.util.Date;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class SampleSimpleFactoryImpl extends BaseFactory <SampleSimpleJpaAggregateRoot> implements SampleSimpleFactory
{

	@Override @Valid
	public SampleSimpleJpaAggregateRoot createSampleSimpleJpaAggregateRoot ( Integer id , String f1, String f2, Date d3, String f4) {
		
		SampleSimpleJpaAggregateRoot sampleSimpleJpaAggregateRoot = new SampleSimpleJpaAggregateRoot ();
		sampleSimpleJpaAggregateRoot.setEntityId(id);
		sampleSimpleJpaAggregateRoot.setField1(f1);
		sampleSimpleJpaAggregateRoot.setField2(f2);
		sampleSimpleJpaAggregateRoot.setField3(d3);
		sampleSimpleJpaAggregateRoot.setField4(f4);
		return sampleSimpleJpaAggregateRoot ;
	}
	
}
