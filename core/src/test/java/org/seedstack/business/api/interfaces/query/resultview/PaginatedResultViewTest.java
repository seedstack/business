/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.query.resultview;

import org.seedstack.business.api.interfaces.query.view.page.PaginatedView;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.data.Index;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static junitparams.JUnitParamsRunner.$;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PaginatedResultViewTest {


//	@Test
//	@Parameters(method="listValues")
//	public void assertpaginatedResult(
//			PaginatedView<String> list ,
//			int expectedPageCount ,
//			int expectedPageSize ,
//			int totalItemsCount ,
//			int lastPageSize
//			)
//	{
//		assertThat(list.getPageIndex()).isEqualTo(expectedPageCount - 1);
//		assertThat(list.getPagesCount()).isEqualTo(expectedPageCount );
//		assertThat(list.getPageSize()).isEqualTo(expectedPageSize);
//		assertThat(list.getTotalItemsCount()).isEqualTo(totalItemsCount);
//
//		assertThat(list.getPageItems().size()).isPositive();
//		assertThat(list.getPageItems().size()).isEqualTo(lastPageSize);
//		assertThat(list.getPageItems().size()).isLessThanOrEqualTo(expectedPageSize);
//		for (int index = 0 ; index < lastPageSize ; index ++)
//		{
//			assertThat ( list.getPageItems() ).contains("" + (expectedPageCount - 1 ), atIndex(index));
//		}
//	}

	@Test
	@Parameters(method="listValues2")
	public void assertpaginatedResultWithSublist(
			PaginatedView<String> list ,
			int expectedPageCount ,
			int expectedPageSize ,
			int totalItemsCount
			)
	{

		assertThat(list.getView()).hasSize(expectedPageSize);
		for (int i = 0 ; i < expectedPageSize ; i ++)
		{
			assertThat(list.getView()).contains("0", Index.atIndex(i));
		}
//		assertThat(list.getPagesCount()).isEqualTo(expectedPageCount );
//		assertThat(list.getPageSize()).isEqualTo(expectedPageSize);
//		assertThat(list.getTotalItemsCount()).isEqualTo(totalItemsCount);
//
//		assertThat(list.getPageItems().size()).isPositive();
//		assertThat(list.getPageItems().size()).isEqualTo(lastPageSize);
//		assertThat(list.getPageItems().size()).isLessThanOrEqualTo(expectedPageSize);
//		for (int index = 0 ; index < lastPageSize ; index ++)
//		{
//			assertThat ( list.getPageItems() ).contains("" + (expectedPageCount - 1 ), atIndex(index));
//		}
	}

	@SuppressWarnings("unused")
	private Object[] listValues()
	{

		return $(// nuber of pages , items per page
				 randomPaginatedResult( 30 , 5 )
				,randomPaginatedResult( 1 , 10 )
				,randomPaginatedResult( 1 , 1 )
				,randomPaginatedResult( 15 , 5 )
				,randomPaginatedResult( 200 , 1 )
				,randomPaginatedResult( 100 , 100 )
				,randomPaginatedResult( 12 , 5 )
		)
				;
	}



	private Object[] randomPaginatedResult (int pagesCount , int pageSize )
	{
		// will be given by
		int totalItemsCount[] = new int[1];
		int randomLastPageSize[] = new int[1];
		return $(
				// paginated result
				new PaginatedView<String>(
						// generated list
						paginatedList (pagesCount, pageSize, totalItemsCount,randomLastPageSize) ,
						// Page Size
						pageSize ,
						// Page Index we want last page from the PR
						pagesCount - 1
						) ,
				pagesCount,
				pageSize ,
				totalItemsCount[0] ,
				randomLastPageSize[0]
				);
	}


	@SuppressWarnings("unused")
	private Object[] listValues2()
	{

		return $(// number of pages , items per page ,
				 randomPaginatedResultWithSublist( 30 , 5  )
				,randomPaginatedResultWithSublist( 1 , 10 )
				,randomPaginatedResultWithSublist( 1 , 1  )
				,randomPaginatedResultWithSublist( 15 , 5 )
				,randomPaginatedResultWithSublist( 200 , 1   )
				,randomPaginatedResultWithSublist( 100 , 100 )
				,randomPaginatedResultWithSublist( 12 , 5    )
				)
				;
	}

	private Object[] randomPaginatedResultWithSublist (int pagesCount , int pageSize )
	{
		// will be given by
		int totalItemsCount[] = new int[1];
		List<String> paginatedList = paginatedList (pagesCount, pageSize, totalItemsCount);
		return $(
				// paginated result
				new PaginatedView<String>(
							// generated sub list
							paginatedList ,
							// sub list start at the second "section"
							pagesCount * pageSize * 1 ,
							// realsize : a list 10 times larger
							pagesCount * pageSize * 10 ,
							// Page Size
							pageSize ,
							// Page Index we want last page from the PR
							pagesCount
						) ,
						pagesCount,
						pageSize ,
						totalItemsCount[0]
				);
	}

	/**
	 *
	 *
	 * @return list with index as item on each
	 */
	private List<String> paginatedList(int pagesCount , int pageSize ,int [] totalItemsCount, int [] randomLastPageSize)
	{
		int itemCount = 0;
		List<String> out = new LinkedList<String>();
		for (int i = 0 ; i < pagesCount ; i++)
		{
			int actualPageSize = pageSize;
			if (i == (pagesCount - 1 ))
			{
				actualPageSize = new Random().nextInt(pageSize);
				if (actualPageSize == 0) actualPageSize++;
				randomLastPageSize[0] = actualPageSize;
			}
			itemCount += actualPageSize;
			for (int j = 0; j < actualPageSize; j++)
			{
				out.add(""+i);
			}
		}
		totalItemsCount[0] = itemCount;

		return out;
	}
	/**
	 *
	 *
	 * @return list with index as item on each
	 */
	private List<String> paginatedList(int pagesCount , int pageSize ,int [] totalItemsCount)
	{
		int itemCount = 0;
		List<String> out = new LinkedList<String>();
		for (int i = 0 ; i < pagesCount ; i++)
		{
			int actualPageSize = pageSize;

			itemCount += actualPageSize;
			for (int j = 0; j < actualPageSize; j++)
			{
				out.add(""+i);
			}
		}
		totalItemsCount[0] = itemCount;

		return out;
	}





}
