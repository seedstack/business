/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.view;

import static junitparams.JUnitParamsRunner.$;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class VirtualListTest {
    private VirtualList<Integer> underTest;

    @Test
    public void test_virtual_list() {
        List<Integer> integers = Lists.newArrayList(3, 4, 5);

        VirtualList<Integer> underTest = new VirtualList<>(integers, 3, 10);
        Assertions.assertThat(underTest.subList(3, 6)).hasSize(3);
        Assertions.assertThat(underTest.get(3)).isEqualTo(3);
    }

    @Test
    public void test_virtual_list_edge_cases() {
        List<Integer> integers = Lists.newArrayList(0);

        VirtualList<Integer> underTest = new VirtualList<>(integers, 0, 1);
        Assertions.assertThat(underTest.subList(0, 1)).hasSize(1);
        Assertions.assertThat(underTest.get(0)).isEqualTo(0);
    }

    private Object[] listValues2() {
        return $(
                virtualist(100, 15, 10, 20, 10),
                virtualist(100, 15, 85, 99, 14)
        );
    }

    private Object[] virtualist(int realSize, int subListSize, int subListOffset, Integer index,
            Integer expectedValue) {
        return new Object[]{index, expectedValue, new VirtualList<>(subList(subListSize), subListOffset, realSize)};
    }

    private List<Integer> subList(int subListSize) {
        List<Integer> lit = new LinkedList<>();
        for (int i = 0; i < subListSize; i++) {
            lit.add(i);
        }
        return lit;
    }

    @Test
    public void check_sublist() {
        // list from 0 to 29
        List<Integer> subList = subList(30);

        // virtual list from 0 to 100 with sublist from 50 to 80
        VirtualList<Integer> virtualList = new VirtualList<>(subList, 50, 100);

        List<Integer> subList2 = virtualList.subList(50, 65);
        Assertions.assertThat(subList2).contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    }
}
