/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.domain.specification;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.seedstack.business.domain.BaseAggregateRoot;

public class Team extends BaseAggregateRoot<String> {

    private final String name;
    private Set<Person> members = new HashSet<>();
    private Person[] vips = new Person[3];

    public Team(String name) {
        this.name = name;
    }

    public void addMember(String name, int age, Address address) {
        members.add(new Person(name, age, address));
    }

    public Set<Person> members() {
        return Collections.unmodifiableSet(members);
    }

    public void addVip(int position, String name, int age, Address address) {
        checkArgument(position >= 0 && position < 3, "position must be in interval [0,3[");
        vips[position] = new Person(name, age, address);
    }

    public Person[] getVips() {
        return vips.clone();
    }

    @Override
    public String getId() {
        return name;
    }
}
