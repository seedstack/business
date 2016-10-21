/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver.sample;

import org.seedstack.business.assembler.MatchingEntityId;

import java.util.Date;


public class Case2Dto {

    String name;

    Date birthDate;

    public Case2Dto(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    @MatchingEntityId(index = 0)
    public String getName() {
        return name;
    }

    @MatchingEntityId(index  = 1)
    public Date getBirthDate() {
        return birthDate;
    }

    public String otherDetails() {
        return "something";
    }
}
