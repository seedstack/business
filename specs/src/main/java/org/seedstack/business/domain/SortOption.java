/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SortOption implements Repository.Option {
    private final List<SortedAttribute> sortedAttributes = new ArrayList<>();
    private final Direction defaultDirection;

    public SortOption() {
        this.defaultDirection = Direction.ASCENDING;
    }

    public SortOption(Direction defaultDirection) {
        this.defaultDirection = defaultDirection;
    }

    public SortOption add(String attribute) {
        sortedAttributes.add(new SortedAttribute(defaultDirection, attribute));
        return this;
    }

    public SortOption add(Direction direction, String attribute) {
        sortedAttributes.add(new SortedAttribute(direction, attribute));
        return this;
    }

    public List<SortedAttribute> getSortedAttributes() {
        return Collections.unmodifiableList(sortedAttributes);
    }

    public static class SortedAttribute {
        private final Direction direction;
        private final String attribute;

        public SortedAttribute(Direction direction, String attribute) {
            this.direction = direction;
            this.attribute = attribute;
        }

        public Direction getDirection() {
            return direction;
        }

        public String getAttribute() {
            return attribute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SortedAttribute sortedAttribute = (SortedAttribute) o;
            return direction == sortedAttribute.direction &&
                    Objects.equals(attribute, sortedAttribute.attribute);
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, attribute);
        }
    }

    public enum Direction {
        ASCENDING,
        DESCENDING
    }
}
