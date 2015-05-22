/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl.resolver;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.internal.interfaces.assembler.dsl.resolver.impl.ParameterHolderInternal;

/**
 * Test {@link ParameterHolder} implementation.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class ParameterHolderTest {

    private static final String SOURCE = "MyDto.getSomeParam()";
    private static final int param1 = 1;
    private static final int param2 = 2;
    private static final int param3 = 3;

    @Test
    public void testPutParameter() {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        parameterHolder.put(SOURCE, 1, param2);
        parameterHolder.put(SOURCE, 0, param1);
        parameterHolder.put(SOURCE, 2, param3);

        Assertions.assertThat(parameterHolder.isEmpty()).isFalse();

        Assertions.assertThat(parameterHolder.uniqueElement()).isNull();

        Assertions.assertThat(parameterHolder.parameters()).isNotEmpty();
        Assertions.assertThat(parameterHolder.parameters()).isEqualTo(new Object[]{1,2,3});
        Assertions.assertThat(parameterHolder.parameters()).isEqualTo(parameterHolder.parametersOfAggregateRoot(-1));
    }
    @Test
    public void testUniqueElement() {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        parameterHolder.put(SOURCE, -1, param1);

        Assertions.assertThat(parameterHolder.uniqueElement()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutParameterFail() {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        parameterHolder.put(SOURCE, 0, param1);
        parameterHolder.put(SOURCE, 0, param2);
    }

    @Test
    public void testPutParameterWithMultipleAggregate() {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        parameterHolder.put(SOURCE, 0, 0, param1);
        parameterHolder.put(SOURCE, 0, 1, param2);
        parameterHolder.put(SOURCE, 1, 0, param3);

        Assertions.assertThat(parameterHolder.isEmptyForAggregateRoot(0)).isFalse();
        Assertions.assertThat(parameterHolder.isEmptyForAggregateRoot(1)).isFalse();
        Assertions.assertThat(parameterHolder.isEmpty()).isFalse();

        Assertions.assertThat(parameterHolder.parameters()).isEmpty();
        Assertions.assertThat(parameterHolder.parametersOfAggregateRoot(0)).isEqualTo(new Object[]{1, 2});
        Assertions.assertThat(parameterHolder.parametersOfAggregateRoot(1)).isEqualTo(new Object[]{3});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutParameterFailWithMultipleAggregate() {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        parameterHolder.put(SOURCE, 2, 1, param1);
        parameterHolder.put(SOURCE, 2, 1, param2);
    }
}
