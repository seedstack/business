/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.specification;

import org.seedstack.business.domain.BaseAggregateRoot;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Team extends BaseAggregateRoot<String> {
    private final String name;
    private Set<Person> members = new HashSet<>();

    public Team(String name) {
        this.name = name;
    }

    public void addMember(String name, int age, Address address) {
        members.add(new Person(name, age, address));
    }

    public Set<Person> members() {
        return Collections.unmodifiableSet(members);
    }

    @Override
    public String getEntityId() {
        return name;
    }
}
