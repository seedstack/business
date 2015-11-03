/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.finder.Result;

import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class PaginatedViewTest {

    private PaginatedView<Integer> underTest;

    @Test
    public void test_paginated_view() {
        // List of 5 items -- full size equals 10
        List<Integer> integers = Lists.newArrayList(0,1,2,3,4);
        Result<Integer> result = new Result<Integer>(integers, 0, 10);

        // Get the first page
        underTest = new PaginatedView<Integer>(result, new Page(0, 5));

        Assertions.assertThat(underTest).isNotNull();
        Assertions.assertThat(underTest.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(underTest.getView()).hasSize(5);
        Assertions.assertThat(underTest.getResultSize()).isEqualTo(10);
    }

    @Test
    public void test_paginated_view_incomplete_page() {
        // List of 3 items -- full size equals 5
        List<Integer> integers = Lists.newArrayList(0,1,2);
        Result<Integer> result = new Result<Integer>(integers, 0, 3);

        // Get a page with a capacity of 5 items
        underTest = new PaginatedView<Integer>(result, new Page(0, 5));

        Assertions.assertThat(underTest).isNotNull();
        Assertions.assertThat(underTest.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(underTest.getView()).hasSize(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_paginated_view_incomplete_page2() {
        // List of 3 items -- full size equals 5
        List<Integer> integers = Lists.newArrayList(0,1,2);
        Result<Integer> result = new Result<Integer>(integers, 0, 10);

        // Get a page with a capacity of 5 items
        underTest = new PaginatedView<Integer>(result, new Page(0, 5));
        Assertions.assertThat(underTest.getView()).hasSize(3);
    }

    @Test
    public void test_paginated_view_sub_list() {
        // List of 10 items -- full size equals 10
        List<Integer> integers = Lists.newArrayList(0,1,2,3,4,5,6,7,8,9);
        Result<Integer> result = new Result<Integer>(integers, 0, 10);

        // Get a page with a capacity of 5 items
        underTest = new PaginatedView<Integer>(result, new Page(0, 5));

        Assertions.assertThat(underTest).isNotNull();
        Assertions.assertThat(underTest.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(underTest.getView()).hasSize(5);
    }

    @Test
    public void test_paginated_view_last_page() {
        // List of 3 items -- full size equals 13
        List<Integer> integers = Lists.newArrayList(10,11,12);
        Result<Integer> result = new Result<Integer>(integers, 10, 13);

        // Get the last page
        underTest = new PaginatedView<Integer>(result, new Page(1, 10));

        Assertions.assertThat(underTest).isNotNull();
        Assertions.assertThat(underTest.getPageIndex()).isEqualTo(1);
        Assertions.assertThat(underTest.getView()).hasSize(3);
    }
}
