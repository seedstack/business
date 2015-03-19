/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.finders;

import java.util.Date;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class Dto1 {
    private Integer id;


	private String field1;
	private String field2;
	private Date  field3;
	private String field4;
	


	public Dto1(Integer id,  String field1, String field2, Date field3 , String field4) {
		this.id = id;
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
	}


	public Dto1() 
	{
	}
	
	public Integer getId() {
		return id;
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


	@Override
	public String toString() {
		return String.format("\nDto1 [id=%s, field1=%s, field2=%s, field3=%s, field4=%s]", id, field1,
				field2, field3, field4);
	}

    

}
