/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.fixtures.domain.specification.Address;
import org.seedstack.business.fixtures.domain.specification.Team;
import org.seedstack.business.fixtures.domain.specification.TeamWithLeader;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecificationsTest {
    private Team redTeam;
    private Team blueTeam;

    @Before
    public void setUp() throws Exception {
        redTeam = new TeamWithLeader("RED", "Alice", 23, new Address(20, "Tulip street", "SinCity"));
        redTeam.addMember("Roger", 41, new Address(40, "Some street", "SomeCity"));
        redTeam.addMember("Cynthia", 34, new Address(45, "Some street", "SomeCity"));

        blueTeam = new TeamWithLeader("BLUE", "Bob", 44, new Address(12, "Dandelion street", "Metropolis"));
        blueTeam.addMember("Marguerite", 61, new Address(55, "Other street", "OtherCity"));
    }

    @Test
    public void testSimpleEquality() throws Exception {
        Specification<Team> equalSpecification = new EqualSpecification<>("name", "RED");
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isTrue();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isFalse();
    }

    @Test
    public void testNestedEquality() throws Exception {
        Specification<Team> equalSpecification = new EqualSpecification<>("leader.name", "Alice");
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isTrue();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isFalse();
    }

    @Test
    public void testNestedCollectionEquality() throws Exception {
        Specification<Team> equalSpecification = new EqualSpecification<>("members.address.city", "SomeCity");
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isTrue();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isFalse();
    }

    @Test
    public void testEqualityAsPredicate() throws Exception {
        Specification<Team> equalSpecification = new EqualSpecification<Team>("leader.name", "Alice")
                .and(new EqualSpecification<Team>("members.address.street", "Other street").not());

        assertThat(Stream.of(redTeam, blueTeam).filter(equalSpecification.asPredicate()).collect(Collectors.toList())).containsExactly(redTeam);
    }

    @Test
    public void testGreaterThan() throws Exception {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new GreaterThanSpecification<Team>("members.address.number", 60).asPredicate())
                .collect(Collectors.toList())
        ).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new GreaterThanSpecification<Team>("members.address.number", 50).asPredicate())
                .collect(Collectors.toList())
        ).containsExactly(blueTeam);
    }

    @Test
    public void testGreaterThanOrEqual() throws Exception {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new GreaterThanSpecification<>("members.address.number", 60)
                        .or(new EqualSpecification<>("members.address.number", 60))
                        .asPredicate())
                .collect(Collectors.toList())
        ).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new GreaterThanSpecification<>("members.address.number", 55)
                        .or(new EqualSpecification<>("members.address.number", 55))
                        .asPredicate())
                .collect(Collectors.toList())
        ).containsExactly(blueTeam);
    }

    @Test
    public void testLessThan() throws Exception {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new LessThanSpecification<>("members.address.number", 40).asPredicate())
                .collect(Collectors.toList())
        ).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new LessThanSpecification<>("members.address.number", 45).asPredicate())
                .collect(Collectors.toList())
        ).containsExactly(redTeam);
    }

    @Test
    public void testLessThanOrEqual() throws Exception {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new LessThanSpecification<>("members.address.number", 39)
                        .or(new EqualSpecification<>("members.address.number", 39))
                        .asPredicate())
                .collect(Collectors.toList())
        ).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new LessThanSpecification<>("members.address.number", 55)
                        .or(new EqualSpecification<>("members.address.number", 55))
                        .asPredicate())
                .collect(Collectors.toList())
        ).containsExactly(redTeam, blueTeam);
    }

    @Test
    public void testToString() throws Exception {
        Specification<Team> spec = new LessThanSpecification<Team>("members.address.number", 40)
                .and(new GreaterThanSpecification<Team>("members.address.number", 20)
                        .or(new EqualSpecification<>("members.address.number", 20))
                );
        assertThat(spec.toString()).isEqualTo("(members.address.number < 40) ∧ ((members.address.number > 20) ∨ (members.address.number = 20))");
    }
}
