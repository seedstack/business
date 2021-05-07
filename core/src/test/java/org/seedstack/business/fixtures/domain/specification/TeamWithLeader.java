/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.specification;

public class TeamWithLeader extends Team {

    private Person leader;

    public TeamWithLeader(String name, String leaderName, int leaderAge, Address leaderAddress) {
        super(name);
        this.leader = new Person(leaderName, leaderAge, leaderAddress);
    }

    public Person leader() {
        return leader;
    }
}
