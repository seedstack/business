/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * {@link Repository} option for sorting aggregates.
 */
public class SortOption implements Repository.Option {
    private final List<SortedAttribute> sortedAttributes = new ArrayList<>();
    private final Direction defaultDirection;

    /**
     * Creates an empty sort option with {@link Direction#ASCENDING} as default sort direction.
     */
    public SortOption() {
        this.defaultDirection = Direction.ASCENDING;
    }

    /**
     * Creates an empty sort option with the specified argument as default sort direction.
     *
     * @param defaultDirection the default sort direction.
     */
    public SortOption(Direction defaultDirection) {
        this.defaultDirection = defaultDirection;
    }

    /**
     * Adds the specified attribute to the list of sorted attributes with the default direction.
     *
     * @param attribute the attribute to sort.
     * @return the sort option itself.
     */
    public SortOption add(String attribute) {
        sortedAttributes.add(new SortedAttribute(attribute, defaultDirection));
        return this;
    }

    /**
     * Adds the specified attribute to the list of sorted attributes with the specified direction.
     *
     * @param attribute the attribute to sort.
     * @param direction the direction this attribute will be sorted with.
     * @return the sort option itself.
     */
    public SortOption add(String attribute, Direction direction) {
        sortedAttributes.add(new SortedAttribute(attribute, direction));
        return this;
    }

    /**
     * Returns the sorted attributes.
     *
     * @return the list of currently registered sorted attributes.
     */
    public List<SortedAttribute> getSortedAttributes() {
        return Collections.unmodifiableList(sortedAttributes);
    }

//    /**
//     * Returns a comparator derived from the sort options.
//     *
//     * @param <T> the type of compared object.
//     * @return the comparator.
//     */
//    public <T> Comparator<T> asComparator() {
//        if (sortedAttributes.isEmpty()) {
//            return (o1, o2) -> 0;
//        } else if (sortedAttributes.size() == 1) {
//            return buildComparator(sortedAttributes.get(0));
//        } else {
//            Comparator<T> comparator;
//        }
//    }
//
//    private <T> Comparator<T> buildComparator(SortedAttribute sortedAttribute) {
//    }

    /**
     * Sort direction associated to a sorted attribute.
     */
    public enum Direction {
        ASCENDING, DESCENDING
    }

    /**
     * Represents a specific sorted attribute in a {@link SortOption}.
     */
    public static class SortedAttribute {

        private final String attribute;
        private final Direction direction;

        /**
         * Creates a sorted attribute.
         *
         * @param attribute the name of the attribute to sort.
         * @param direction the direction of the sort.
         */
        SortedAttribute(String attribute, Direction direction) {
            this.direction = direction;
            this.attribute = attribute;
        }

        /**
         * Returns the sort direction of the attribute.
         *
         * @return the direction the attribute will be sorted with.
         */
        public Direction getDirection() {
            return direction;
        }

        /**
         * Returns the attribute name.
         *
         * @return the sorted attribute name.
         */
        public String getAttribute() {
            return attribute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SortedAttribute sortedAttribute = (SortedAttribute) o;
            return direction == sortedAttribute.direction && Objects.equals(attribute, sortedAttribute.attribute);
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, attribute);
        }
    }
}
