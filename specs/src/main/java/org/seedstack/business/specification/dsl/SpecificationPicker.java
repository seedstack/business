/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

/**
 * An element of the {@link SpecificationBuilder} DSL to pick the specification that will apply to the current selection.
 *
 * @param <T>        the type of the object the specification applies to.
 * @param <SELECTOR> the type of the selector.
 */
public interface SpecificationPicker<T, SELECTOR extends BaseSelector> {
    /**
     * Negates the specification that will be picked.
     *
     * @return the next operation of the builder DSL, allowing to pick the negated specification.
     */
    SpecificationPicker<T, SELECTOR> not();

    /**
     * Picks a {@link String}-specific matching specification that will be satisfied if the current selection matches the specified pattern.
     * A pattern can contain:
     * <ul>
     * <li>'?' characters which match any character.</li>
     * <li>'*' characters which match any (possibly empty) sequence of characters.</li>
     * </ul>
     *
     * @param pattern the pattern to match.
     * @return the next operation of the builder DSL, allowing to pick the string matching options.
     */
    StringOptionPicker<T, SELECTOR> matching(String pattern);

    /**
     * Picks a {@link String}-specific equality specification that will be satisfied if the current selection equals to the specified value.
     *
     * @param value the value to be equal to.
     * @return the next operation of the builder DSL, allowing to pick the string equality options.
     */
    StringOptionPicker<T, SELECTOR> equalTo(String value);

    /**
     * Picks a general purpose equality specification that will be satisfied if the current selection equals to the specified value.
     *
     * @param value the value to be equal to.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V> OperatorPicker<T, SELECTOR> equalTo(V value);

    /**
     * Picks a greater than specification that will be satisfied if the current selection is strictly greater than the
     * specified value.
     *
     * @param value the value to be greater than.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> greaterThan(V value);

    /**
     * Picks a greater than or equal to specification that will be satisfied if the current selection is greater than or
     * equal to the specified value.
     *
     * @param value the value to be greater than or equal to.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> greaterThanOrEqualTo(V value);

    /**
     * Picks a less than specification that will be satisfied if the current selection is strictly less than the
     * specified value.
     *
     * @param value the value to be less than.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> lessThan(V value);

    /**
     * Picks a less than or equal to specification that will be satisfied if the current selection is less than or
     * equal to the specified value.
     *
     * @param value the value to be less than or equal to.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> lessThanOrEqualTo(V value);

    /**
     * Picks a between specification that will be satisfied if the current selection is strictly greater than and
     * strictly less than to the specified value.
     *
     * @param leftValue  the value to be greater than.
     * @param rightValue the value to be less than.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> between(V leftValue, V rightValue);

    /**
     * Picks a between specification that will be satisfied if the current selection is (strictly or not) greater than and
     * (strictly or not) less than to the specified value.
     *
     * @param leftValue      the value to be greater than (or equal to if leftInclusive is true).
     * @param rightValue     the value to be less than  (or equal to if rightInclusive is true).
     * @param leftInclusive  if true, the leftValue argument will be included in the interval, otherwise it will be excluded.
     * @param rightInclusive if true, the rightValue argument will be included in the interval, otherwise it will be excluded.
     * @return the next operation of the builder DSL, allowing to combine this specification with another one.
     */
    <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> between(V leftValue, V rightValue, boolean leftInclusive, boolean rightInclusive);
}
