/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver.annotated;

import org.seedstack.business.assembler.MatchingEntityId;


public class CaseFail2Dto {
    private String firstName;
    private String lastName;

    public CaseFail2Dto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @MatchingEntityId(index = 0)
    public String getFirstName() {
        return firstName;
    }


    @MatchingEntityId(index = 0)
    public String getLastName() {
        return lastName;
    }

}
