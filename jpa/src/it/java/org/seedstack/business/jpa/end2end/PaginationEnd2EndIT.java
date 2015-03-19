/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.end2end;

import com.google.common.collect.Maps;
import org.seedstack.business.api.interfaces.query.finder.GenericFinder;
import org.seedstack.business.api.interfaces.query.range.Range;
import org.seedstack.business.api.interfaces.query.result.Result;
import org.seedstack.business.api.interfaces.query.view.page.Page;
import org.seedstack.business.api.interfaces.query.view.page.PaginatedView;
import org.seedstack.business.api.interfaces.query.view.page.PaginationService;
import org.seedstack.business.jpa.samples.domain.simple.SampleSimpleFactory;
import org.seedstack.business.jpa.samples.domain.simple.SampleSimpleJpaAggregateRoot;
import org.seedstack.business.jpa.samples.finders.Dto1;
import org.seedstack.business.jpa.samples.utils.Loader;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.seed.core.api.Logging;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.transaction.api.Transactional;
import org.seedstack.seed.validation.api.ValidationException;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author epo.jemba@ext.mpsa.com
 */
public class PaginationEnd2EndIT extends AbstractSeedIT {

    @Logging
    Logger logger;

    @Inject
    Loader loader;

    @Inject
    GenericFinder<Dto1, Map<String, Object>> dto1Finder;

    @Inject
    SampleSimpleFactory sampleSimpleFactory;

    @Inject
    PaginationService paginationService;

    @Before
    public void init() {
        assertThat(loader).isNotNull();
    }

    @Test
    @Transactional
    public void pagination_scenario_1() {
        loader.init(Loader.Scenario.ONE);
        assertThat(dto1Finder).isNotNull();
        assertThat(paginationService).isNotNull();

		/*
		 * Page of 15 items
		 * 
		 */

        final int page_capacity = 15;
		
		/*
		 * we choose a range larger than the actual pagesize
		 */
        int rangeSize = page_capacity * 4;
        Range range = new Range(0, rangeSize);
		/*
		 * we setup the criteria 
		 */
        Map<String, Object> criteria = Maps.newHashMap();
        criteria.put("param1", "odd");
		
		/*
		 * we launch the query
		 */
        Result<Dto1> result = dto1Finder.find(range, criteria);
		/*
		 * we assert the results
		 */
        assertThat(result).isNotNull();
        assertThat(result.getResult()).isNotNull();
        assertThat(result.getResult()).hasSize(rangeSize);
        assertThat(result.getSize()).isEqualTo(rangeSize);
        assertThat(result.getFullSize()).isEqualTo(4996);

        // We ask for page 1 (i.e. index = 0 )
        Page page1 = new Page(0, page_capacity, page_capacity, result.getFullSize());

        {
			/*
			 * We prepare the view for the first page
			 */
            PaginatedView<Dto1> paginatedView = new PaginatedView<Dto1>(result, page1);
			
			/*
			 * We assert the view 
			 */
            List<Dto1> view = paginatedView.getView();
            //System.err.println(view);
            assertThat(view).hasSize(page1.getCapacity());
            assertThat(view).extracting("field1").containsExactly(
                    "f1-0",
                    "f1-2",
                    "f1-4",
                    "f1-6",
                    "f1-8",
                    "f1-10",
                    "f1-12",
                    "f1-14",
                    "f1-16",
                    "f1-18",
                    "f1-20",
                    "f1-22",
                    "f1-24",
                    "f1-26",
                    "f1-28"
            );
        }

        Page page2 = page1.next();
        {
			/*
			 * We prepare the view for the second page
			 */
            PaginatedView<Dto1> paginatedView = new PaginatedView<Dto1>(result, page2);
			/*
			 * We assert the view 
			 */
            List<Dto1> view = paginatedView.getView();
            assertThat(view).hasSize(page2.getCapacity());
            assertThat(view).extracting("field1").containsExactly(
                    "f1-30",
                    "f1-32",
                    "f1-34",
                    "f1-36",
                    "f1-38",
                    "f1-40",
                    "f1-42",
                    "f1-44",
                    "f1-46",
                    "f1-48",
                    "f1-50",
                    "f1-52",
                    "f1-54",
                    "f1-56",
                    "f1-58"
            );
        }
		/*
		 * we get the lastpage from the service of pagination.
		 */
        Page lastPage = paginationService.getLastPage(page2);
		/*
		 * we compute another a range always larger than the actual pagesize
		 */
        range = new Range(4996 - rangeSize, rangeSize);
        logger.info("Range used " + range);
		/*
		 * we relaunch the query
		 */
        result = dto1Finder.find(range, criteria);
        logger.info("Result Size : " + result.getSize());
        logger.info("Result : " + result.getResult());

        {
			/*
			 * We prepare the view for the last page
			 */
            PaginatedView<Dto1> paginatedView = new PaginatedView<Dto1>(result, lastPage);
			/*
			 * We assert the view 
			 */
            List<Dto1> view = paginatedView.getView();
//			System.err.println("View = " + view);

            assertThat(view).extracting("field1").containsExactly(
                    "f1-9990"
            );
        }
    }


    @Test(expected = ValidationException.class)
    public void validation_on_factory_works() {
        // this will throw a validation exception
        // look at the interface SampleSimpleFactory to know why
        @SuppressWarnings("unused")
        SampleSimpleJpaAggregateRoot aRoot = sampleSimpleFactory.createSampleSimpleJpaAggregateRoot(5, "f", "e", null, null);

    }
}
