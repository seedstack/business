/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.util.inmemory.InMemorySequenceGenerator;

public class InMemorySequenceGeneratorTest {

    @Test
    public void testInMemorySequenceGenerator() {
        InMemorySequenceGenerator inMemorySequenceHandler = new InMemorySequenceGenerator();
        long seq1 = inMemorySequenceHandler.generate(null);
        long seq2 = inMemorySequenceHandler.generate(null);
        Assertions.assertThat(seq1 + 1).isEqualTo(seq2);
    }
}
