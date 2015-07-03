/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.core.domain;

import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Factory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.internal.defaults.FactoryInternal;
import org.seedstack.seed.core.api.SeedException;

/**
 * FactoryInternalTest
 * 
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public class FactoryInternalTest {

	Factory<MyAggregateTest> myFactory;

	private static final Integer ID = Integer.valueOf(2);
	private static final int ID_PRIMITIVE = 2;
	private static final String NOM = "test";
	private static final Long AGE = Long.valueOf(1);

	@Before
	public void before() {
		myFactory = new FactoryInternal<MyAggregateTest>(new Class<?>[]{MyAggregateTest.class});
	}

	@Test
	public void create_aggregate_using_unique_parameter_constructor() {
		MyAggregateTest myAggregateTest = myFactory.create(ID);
		Assertions.assertThat(myAggregateTest).isNotNull();
		Assertions.assertThat(myAggregateTest.getEntityId()).isEqualTo(ID);
	}

	@Test
	public void create_aggregate_using_unique_primitive_parameter_constructor() {
		MyAggregateTest myAggregateTest = myFactory.create(ID_PRIMITIVE);
		Assertions.assertThat(myAggregateTest).isNotNull();
		Assertions.assertThat(myAggregateTest.getEntityId()).isEqualTo(ID);
	}
	
	@Test
	public void create_aggregate_using_two_paramater_constructor() {
		MyAggregateTest myAggregateTest = myFactory.create(NOM, ID);
		Assertions.assertThat(myAggregateTest).isNotNull();
		Assertions.assertThat(myAggregateTest.getEntityId()).isEqualTo(ID);
		Assertions.assertThat(myAggregateTest.getNom()).isEqualTo(NOM);	
		myAggregateTest = myFactory.create(NOM, ID_PRIMITIVE);
		Assertions.assertThat(myAggregateTest).isNotNull();
		Assertions.assertThat(myAggregateTest.getEntityId()).isEqualTo(ID);
		Assertions.assertThat(myAggregateTest.getNom()).isEqualTo(NOM);
	}

	@Test(expected = SeedException.class)
	public void create_aggregate_using_two_parameter_conflicted_constructor() {
		myFactory.create(ID, NOM);
	}
	
	@Test(expected = SeedException.class)
	public void create_aggregate_using_a_primitiva_paramater_conflicted_constructor() {
		myFactory.create(ID_PRIMITIVE, NOM);
	}
	
	@Test(expected = SeedException.class)
	public void create_aggregate_using_null_parameters_conflicted_constructor() {
		myFactory.create(AGE, null);
	}
	@Test(expected = SeedException.class)
	public void create_aggregate_using_parameters_for_no_existing_constructor() {
		myFactory.create(AGE, "test1","test2", "test3");
	}
	
	@Test
	public void create_aggregate_using_null_parameters_constructor() {
		MyAggregateTest myAggregateTest = myFactory.create(NOM, null);
		Assertions.assertThat(myAggregateTest).isNotNull();
		Assertions.assertThat(myAggregateTest.getEntityId()).isEqualTo(null);
		Assertions.assertThat(myAggregateTest.getNom()).isEqualTo(NOM);
	}
	
	static class MyAggregateTest implements AggregateRoot<Integer> {
		Integer id;

		String nom;
		
		Long age;

		private MyAggregateTest(String nom) {
			super();
			this.nom = nom;
		}

		protected MyAggregateTest(Integer id) {
			super();
			this.id = id;
		}

		MyAggregateTest(Integer id, String nom) {
			super();
			this.id = id;
			this.nom = nom;
		}

		MyAggregateTest(int id, String nom) {
			super();
			this.id = id;
			this.nom = nom;
		}
		
		MyAggregateTest(Long age, Integer id) {
			super();
			this.age = age;
			this.id = id;
		}

		
		MyAggregateTest(Long age, Long age2) {
			super();
			this.age = age;
		}


		MyAggregateTest( String nom, Integer id) {
			super();
			this.id = id;
			this.nom = nom;
		}

		MyAggregateTest() {
			super();
		}

		/**
		 * Getter nom
		 * 
		 * @return the nom
		 */
		public String getNom() {
			return nom;
		}

		@Override
		public Integer getEntityId() {
			return id;
		}
	}
}
