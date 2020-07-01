/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.finder;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.seed.Ignore;

public class BaseRangeFinderTest {
    @Test
    public void testFind() {
        MyRangeFinder myRangeFinder = new MyRangeFinder();
        Result<String> result = myRangeFinder.find(new Range(0, 2), "o");
        Assertions.assertThat(result.getResult()).hasSize(2);
        Assertions.assertThat(result.getResult()).containsOnly("john", "doe");
    }

    @Test
    public void testFindWithOffset() {
        MyRangeFinder myRangeFinder = new MyRangeFinder();
        Result<String> result = myRangeFinder.find(new Range(1, 2), "o");
        Assertions.assertThat(result.getResult()).hasSize(2);
        Assertions.assertThat(result.getResult()).containsOnly("doe", "bob");
    }

    @Ignore
    private static class MyRangeFinder extends BaseRangeFinder<String, String> {
        private List<String> names = Lists.newArrayList("john", "doe", "jane", "bob", "martin");

        @Override
        protected List<String> computeResultList(Range range, String criteria) {
            List<String> results = new ArrayList<>();
            int count = 0;
            for (String name : names) {
                if (count++ < range.getOffset())
                    continue;

                if (name.contains(criteria))
                    results.add(name);

                if (results.size() == range.getSize())
                    break;
            }
            return results;
        }

        @Override
        protected long computeFullRequestSize(String criteria) {
            int count = 0;
            for (String name : names) {
                if (name.contains(criteria))
                    count++;
            }
            return count;
        }
    }
}
