/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.infrastructure;

import com.google.common.collect.ForwardingSet;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectCollection {
	
	
	static class Item 
	{
		@Inject 
		Service service ;
	}
	
	static class Service
	{
		void service ()
		{
			
		}
	}
	
	static class GuiceSet extends ForwardingSet<Item>
	{

		private Set<Item> items;

		public GuiceSet(Set<Item> items) {
			this.items = items;
		}
		
		@Override
		protected Set<Item> delegate() {
			return items;
		}
		
		
	}
	
	
//	@Test
	public void checkInject ()
	{
		
		Injector injector = Guice.createInjector(new AbstractModule()
		{
			@Override
			protected void configure() {
				binder().requireExplicitBindings();
				bind(Service.class);
			}
		});
		
		Set<Item> items = new HashSet<Item>();
		items.add(new Item());
		items.add(new Item());
		items.add(new Item());
		
		injector.injectMembers(items);
		
		assertThat(items.iterator().next().service).isNotNull();
		
		
		
		
	}
	

}
