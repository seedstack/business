/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.qualifier.fixtures.domain;

import org.seedstack.business.domain.BaseValueObject;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class MyValueObject extends BaseValueObject {

    private final String firstName;

    private final String lastName;

    public MyValueObject(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
