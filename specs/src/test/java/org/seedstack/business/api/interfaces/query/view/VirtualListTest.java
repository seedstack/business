/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.query.view;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;


//@RunWith(JUnitParamsRunner.class)
public class VirtualListTest {

	
//	@Test
//	@Parameters("listValues2")
	public void check_get(  Integer index, Integer expectedValue  , VirtualList<Integer> virtualList ) 
	{
		Assertions.assertThat(virtualList.get(index)).isEqualTo(expectedValue);
	}
	@Test
	public void getTests ()
	{
		Object[] listValues2 = listValues2();
		for (Object object : listValues2) {
			Object[] array = (Object[]) object;
			check_get((Integer)array[0], (Integer) array[1], (VirtualList<Integer>) array[2]);
		}
		
	}
	
	private Object[] listValues2()
	{
		return $( 
				 virtualist( 100 , 15 , 10  ,  20 ,  10  ) ,
				 virtualist( 100 , 15 , 85  ,  99 ,  14  ) 
			);
	}

	private Object[] virtualist(int realSize , int subListSize , int subListOffset , Integer index, Integer expectedValue) {

		return new Object [] { 
				index , expectedValue , new VirtualList<Integer>(subList(subListSize), subListOffset, realSize) }				
				;
	}

	private List<Integer> subList(int subListSize) {
		List<Integer> lit = new LinkedList<Integer>();
		
		for(int i = 0 ; i < subListSize ; i ++)
		{
			lit.add(i);
		}
		
		return lit;
	}
	
	@Test
	public void check_sublist ()
	{
		// list from 0 to 29
		List<Integer> subList = subList(30);
		
		// virtual list from 0 to 100 with sublist from 50 to 80 
		VirtualList<Integer> virtualList = new VirtualList<Integer>(subList, 50, 100);
		
		List<Integer> subList2 = virtualList.subList(50, 65);
		Assertions.assertThat(subList2).contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		
	}
	

}
