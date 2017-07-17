/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver.annotated;

import org.seedstack.business.assembler.AggregateId;

/**
 * Case 4: The first name and last name are mapped to a {@code Pair&lt;String, String&gt;} in the constructor.
 */
public class Case4Dto {
    private String firstName;
    private String lastName;
    private String orderItem;
    private String orderDescription;

    public Case4Dto(String firstName, String lastName, String orderItem, String orderDescription) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderItem = orderItem;
        this.orderDescription = orderDescription;
    }

    @AggregateId(index = 0, aggregateIndex = 0)
    public String getFirstName() {
        return firstName;
    }

    @AggregateId(index = 1, aggregateIndex = 0)
    public String getLastName() {
        return lastName;
    }

    @AggregateId(index = 0, aggregateIndex = 1)
    public String getOrderItem() {
        return orderItem;
    }

    @AggregateId(index = 1, aggregateIndex = 1)
    public String getOrderDescription() {
        return orderDescription;
    }
}
