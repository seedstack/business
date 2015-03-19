/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.base;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class BaseEntityTest {

	private Entity1 one;
	private Entity1 two;
	private Entity1 three;

	static class Entity1 extends BaseEntity<Long>
	{
		private String name;
		
		private Long entityId;
		
		public Entity1() {			
		}

		public String getName() {
			return name;
		}
		
		@Override
		public Long getEntityId() {
			return entityId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setEntityId(long l) {
			this.entityId = l;
		}
	}
	
	
	@Before
	public void init()
	{
		one = new Entity1();
		one.setEntityId(1l);
		one.setName("one");
		
		two = new Entity1();
		two.setEntityId(1l);
		two.setName("two");
		
		three = new Entity1();
		three.setEntityId(2l);
		three.setName("one");
		
	}
	
	@Test
	public void testHashCode() {
		Assertions.assertThat(one.hashCode()).isEqualTo(two.hashCode());
		Assertions.assertThat(one.hashCode()).isNotEqualTo(three.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Assertions.assertThat(one).isEqualTo(two);
		Assertions.assertThat(one).isNotEqualTo(three);
	}

}
