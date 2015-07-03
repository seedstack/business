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

import org.seedstack.business.api.domain.BaseAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Entity
public class SampleSimpleJpaAggregateRoot extends BaseAggregateRoot<Integer>
{
	@Id
	private Integer entityId;
	@Size(min=2)
	private String field1;
	@Size(min=2)
	private String field2;
	@NotNull
	private Date  field3;
	
	private String  field4;
	
	SampleSimpleJpaAggregateRoot() {		
	}

	@Override
	public Integer getEntityId() {
		return entityId;
	}

	void setEntityId(Integer entityId)
	{
		this.entityId = entityId;
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


	public String getField4() {
		return field4;
	}


	public void setField4(String field4) {
		this.field4 = field4;
	}
}
