/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.domain.embed;

import org.seedstack.business.api.domain.BaseAggregateRoot;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

/**
 * 
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Entity
public class SampleEmbedJpaAggregateRoot extends BaseAggregateRoot<SampleEmbeddedKey>
{
	@EmbeddedId
	private SampleEmbeddedKey entityId;
	private String field1;
	private String field2;
	private Date  field3;
	
	SampleEmbedJpaAggregateRoot() {		
	}

	@Override
	public SampleEmbeddedKey getEntityId() {
		return entityId;
	}

	public String getField1() {
		return field1;
	}


	public void setField1(String field1) {
		this.field1 = field1;
	}


	public String getField2() {
		return field2;
	}


	public void setField2(String field2) {
		this.field2 = field2;
	}


	public Date getField3() {
		return field3;
	}


	public void setField3(Date field3) {
		this.field3 = field3;
	}
}
