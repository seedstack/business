/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.builder;

public interface SpecificationPicker<T, SELECTOR extends BaseSelector> {
    SpecificationPicker<T, SELECTOR> not();

    StringOptionPicker<T, SELECTOR> matching(String pattern);

    StringOptionPicker<T, SELECTOR> equalTo(String value);

    <V> BaseOptionPicker<T, SELECTOR> equalTo(V value);

    <V extends Comparable<? super V>> BaseOptionPicker<T, SELECTOR> greaterThan(V value);

    <V extends Comparable<? super V>> BaseOptionPicker<T, SELECTOR> greaterThanOrEqualTo(V value);

    <V extends Comparable<? super V>> BaseOptionPicker<T, SELECTOR> lessThan(V value);

    <V extends Comparable<? super V>> BaseOptionPicker<T, SELECTOR> lessThanOrEqualTo(V value);

    <V extends Comparable<? super V>> BaseOptionPicker<T, SELECTOR> between(V leftValue, V rightValue);

    <V extends Comparable<? super V>> BaseOptionPicker<T, SELECTOR> between(V leftValue, V rightValue, boolean leftInclusive, boolean rightInclusive);
}
