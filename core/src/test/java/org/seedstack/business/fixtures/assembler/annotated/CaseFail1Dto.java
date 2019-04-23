/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.annotated;

import org.seedstack.business.assembler.AggregateId;

public class CaseFail1Dto {

    private String firstName;
    private String lastName;

    public CaseFail1Dto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @AggregateId
    public String getLastName() {
        return lastName;
    }

    @AggregateId
    public String getFirstName() {
        return firstName;
    }
}
