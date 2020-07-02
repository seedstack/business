/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler.annotated;

import java.util.Date;

public class PersonId {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private NameVO name;

    public PersonId(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonId(String firstName, Date birthDate) {
        this.firstName = firstName;
        this.birthDate = birthDate;
    }

    public PersonId(NameVO name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public NameVO getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonId personId = (PersonId) o;

        if (birthDate != null ? !birthDate.equals(personId.birthDate) : personId.birthDate != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(personId.firstName) : personId.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(personId.lastName) : personId.lastName != null) {
            return false;
        }
        if (name != null ? !name.equals(personId.name) : personId.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
