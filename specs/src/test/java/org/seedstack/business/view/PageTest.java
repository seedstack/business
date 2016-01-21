/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.seedstack.business.finder.Result;

import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class PageTest {

    @Test
    public void test_paginated_view() {
        List<String> strings = Lists.newArrayList("one", "two", "three", "four", "five");
        Page page = new Page(0, 5);
        Result<String> result = new Result<String>(strings, 0, 100);

        PaginatedView<String> paginatedView = new PaginatedView<String>(result, page);
        Assertions.assertThat(paginatedView.getPagesCount()).isEqualTo(20);
        Assertions.assertThat(paginatedView.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(paginatedView.getPageSize()).isEqualTo(5);
    }

    @Test
    public void test_paginated_view_page_navigation() {
        List<String> strings = Lists.newArrayList("one", "two", "three", "four", "five");
        Page page = new Page(0, 5);
        Result<String> result = new Result<String>(strings, 0, 100);

        // Test the first page
        PaginatedView<String> paginatedView = new PaginatedView<String>(result, page);
        Assertions.assertThat(paginatedView.hasPrev()).isFalse();
        Assertions.assertThat(paginatedView.hasNext()).isTrue();
        Assertions.assertThat(paginatedView.prev()).isNull();
        Assertions.assertThat(paginatedView.next()).isNotNull();
        Assertions.assertThat(paginatedView.next().getIndex()).isEqualTo(1);

        // Navigate to the second page
        paginatedView = new PaginatedView<String>(result, paginatedView.next());
        Assertions.assertThat(paginatedView.hasPrev()).isTrue();
        Assertions.assertThat(paginatedView.hasNext()).isTrue();
        Assertions.assertThat(paginatedView.prev()).isNotNull();
        Assertions.assertThat(paginatedView.prev().getIndex()).isEqualTo(0);

        // test the last page
        paginatedView = new PaginatedView<String>(result, new Page(19, 5));
        Assertions.assertThat(paginatedView.hasPrev()).isTrue();
        Assertions.assertThat(paginatedView.hasNext()).isFalse();
        Assertions.assertThat(paginatedView.prev()).isNotNull();
        Assertions.assertThat(paginatedView.next()).isNull();
    }

    @Test
    public void test_paginated_view_edge_case() {
        List<String> strings = Lists.newArrayList("one", "two", "three", "four", "five");
        Page page = new Page(0, 10);
        Result<String> result = new Result<String>(strings, 0, 92233720368547758L);

        PaginatedView<String> paginatedView = new PaginatedView<String>(result, page);
        Assertions.assertThat(paginatedView.getPagesCount()).isEqualTo(9223372036854776L);
        Assertions.assertThat(paginatedView.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(paginatedView.getPageSize()).isEqualTo(10);
    }

    @Test
    @Ignore("approximation error")
    public void test_paginated_view_edge_case_max() {
        List<String> strings = Lists.newArrayList("one", "two", "three", "four", "five");
        Page page = new Page(0, 10);
        // Long.MAX_VALUE = 9223372036854775807L
        Result<String> result = new Result<String>(strings, 0, Long.MAX_VALUE);

        PaginatedView<String> paginatedView = new PaginatedView<String>(result, page);
        Assertions.assertThat(paginatedView.getPagesCount()).isEqualTo(922337203685477581L);
        Assertions.assertThat(paginatedView.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(paginatedView.getPageSize()).isEqualTo(10);
    }

    @Test
    public void test_paginated_view_edge_case_min() {
        List<String> strings = Lists.newArrayList();
        Page page = new Page(0, 10);
        Result<String> result = new Result<String>(strings, 0, 0);

        PaginatedView<String> paginatedView = new PaginatedView<String>(result, page);
        Assertions.assertThat(paginatedView.getPagesCount()).isEqualTo(0);
        Assertions.assertThat(paginatedView.getPageIndex()).isEqualTo(0);
        Assertions.assertThat(paginatedView.getPageSize()).isEqualTo(0);
    }

}
