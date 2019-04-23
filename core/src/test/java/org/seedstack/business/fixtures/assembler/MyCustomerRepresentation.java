/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler;

import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.FactoryArgument;

public class MyCustomerRepresentation {

    private String id;
    private String name;
    private String primaryAccountNumber;

    @AggregateId
    @FactoryArgument(index = 0)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @FactoryArgument(index = 1)
    public String factoryFirstName() {
        if (name != null) {
            return name.split(" ")[0];
        }
        return null;
    }

    @FactoryArgument(index = 2)
    public String factoryLastName() {
        if (name != null) {
            return name.split(" ")[1];
        }
        return null;

    }

    public String getPrimaryAccountNumber() {
        return primaryAccountNumber;
    }

    public void setPrimaryAccountNumber(String primaryAccountNumber) {
        this.primaryAccountNumber = primaryAccountNumber;
    }
}

