/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.view;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.finder.Range;

public class RangeTest {

	@Test
	public void staticConstructorTest() {
		int offset = 5;
		int size = 10;
		Range range = Range.rangeFromChunkInfo(offset, size);
		Assertions.assertThat(range).isNotNull();
		Assertions.assertThat(range.getOffset()).isEqualTo(offset);
		Assertions.assertThat(range.getSize()).isEqualTo(size);
	}


	@Test
	public void constructorTest() {
		int offset = 5;
		int size = 10;
		Range range = new Range(offset, size);
		Assertions.assertThat(range).isNotNull();
		Assertions.assertThat(range.getOffset()).isEqualTo(offset);
		Assertions.assertThat(range.getSize()).isEqualTo(size);
	}

}
