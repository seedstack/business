/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification.builder;

import org.seedstack.business.domain.AggregateRoot;

public interface SpecificationPicker<A extends AggregateRoot<?>> {
    SpecificationPicker<A> not();

    StringOptionPicker<A> equalTo(String value);

    BaseOptionPicker<A> equalTo(Object value);

    BaseOptionPicker<A> greaterThan(Comparable<?> value);

    BaseOptionPicker<A> greaterThanOrEqualTo(Comparable<?> value);

    BaseOptionPicker<A> lessThan(Comparable<?> value);

    BaseOptionPicker<A> lessThanOrEqualTo(Comparable<?> value);

    BaseOptionPicker<A> between(Comparable<?> leftValue, Comparable<?> rightValue);

    BaseOptionPicker<A> between(Comparable<?> leftValue, Comparable<?> rightValue, boolean leftInclusive, boolean rightInclusive);

    StringOptionPicker<A> matching(String pattern);
}
