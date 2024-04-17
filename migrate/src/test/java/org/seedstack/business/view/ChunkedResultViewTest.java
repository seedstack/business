/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ChunkedResultViewTest {

    @Test
    public void nominalTest() {
        // list from 0 to 39
        List<Integer> list = subList(40);

        ChunkedView<Integer> cResult = new ChunkedView<>(list, 10, 7);

        Assertions.assertThat(cResult.getView()).contains(10, 11, 12, 13, 14, 15, 16);

    }

    private List<Integer> subList(int subListSize) {
        List<Integer> lit = new LinkedList<>();

        for (int i = 0; i < subListSize; i++) {
            lit.add(i);
        }

        return lit;
    }

}
