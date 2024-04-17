/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.annotated;

import java.util.Date;
import org.seedstack.business.assembler.AggregateId;

public class Case2Dto {

    private String name;
    private Date birthDate;

    public Case2Dto(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    @AggregateId(index = 0)
    public String getName() {
        return name;
    }

    @AggregateId(index = 1)
    public Date getBirthDate() {
        return birthDate;
    }

    public String otherDetails() {
        return "something";
    }
}
