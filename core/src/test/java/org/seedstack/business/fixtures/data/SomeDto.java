/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.data;

import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.FactoryArgument;
import org.seedstack.business.data.DataSet;

@DtoOf(SomeAggregate.class)
@DataSet(group = "test", name = "test")
public class SomeDto {
    private String id;
    private String firstName;
    private String lastName;

    @FactoryArgument
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
