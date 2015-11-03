/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.base;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.seedstack.business.api.domain.BaseEntity;
import org.seedstack.seed.SeedException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {

	static class BaseChild extends BaseEntity<Long>
	{

		private String name;
		private Long entityId;
		
		public BaseChild() {
			entityId = null;
		}
		
		public BaseChild(Long entityId) {
			this.entityId = entityId;
		}
		
		@Override
		public Long getEntityId() {
			return entityId;
		}
		
	}
	
	@Test
	public void check_hashcode()
	{
		Long entityId = new Long(12);
		BaseChild child = new BaseChild(entityId);
		
		Set<BaseChild> children = Sets.newHashSet();
		children.add(child);
		
		assertThat(child.hashCode()).isEqualTo( entityId.hashCode());
		
	}

	@Test
	public void check_equals()
	{
		Long entityId = new Long(12);
		BaseChild child1 = new BaseChild(entityId);
		BaseChild child2 = new BaseChild(entityId);
		
		Set<BaseChild> children = Sets.newHashSet();
		children.add(child1);
		children.add(child2);
		
		assertThat(children).hasSize(1);// 
		
		assertThat(child1).isEqualTo( child2);
		
	}
	
	
	@Test(expected=SeedException.class)
	public void empty_entity_cannot_be_include_inside_a_collection() {
		BaseChild child = new BaseChild();
		
		Set<BaseChild> children = Sets.newHashSet();
		children.add(child);
	}

}
