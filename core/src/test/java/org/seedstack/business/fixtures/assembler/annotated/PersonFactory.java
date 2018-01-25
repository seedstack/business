/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler.annotated;

import java.util.Date;
import org.javatuples.Pair;

public class PersonFactory {

    public PersonId createPersonId(Pair<String, String> name) {
        return new PersonId(new NameVO(name.getValue0(), name.getValue1()));
    }

    public PersonId createPersonId(String firstName, String lastName) {
        return new PersonId(firstName, lastName);
    }

    public PersonId createPersonId(String firstName, Date birthDate) {
        return new PersonId(firstName, birthDate);
    }
}
