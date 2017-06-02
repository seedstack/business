/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.builder;

public interface SpecificationPicker<T> {
    SpecificationPicker<T> not();

    StringOptionPicker<T> matching(String pattern);

    StringOptionPicker<T> equalTo(String value);

    <V> BaseOptionPicker<T> equalTo(V value);

    <V extends Comparable<?>> BaseOptionPicker<T> greaterThan(V value);

    <V extends Comparable<?>> BaseOptionPicker<T> greaterThanOrEqualTo(V value);

    <V extends Comparable<?>> BaseOptionPicker<T> lessThan(V value);

    <V extends Comparable<?>> BaseOptionPicker<T> lessThanOrEqualTo(V value);

    <V extends Comparable<?>> BaseOptionPicker<T> between(V leftValue, V rightValue);

    <V extends Comparable<?>> BaseOptionPicker<T> between(V leftValue, V rightValue, boolean leftInclusive, boolean rightInclusive);
}
