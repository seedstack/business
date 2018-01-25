/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.fixtures.domain.specification.Address;
import org.seedstack.business.fixtures.domain.specification.Team;
import org.seedstack.business.fixtures.domain.specification.TeamWithLeader;

public class SpecificationsTest {

    private Team redTeam;
    private Team blueTeam;

    @Before
    public void setUp() {
        redTeam = new TeamWithLeader("RED", "Alice", 23, new Address(20, "Tulip street", "SinCity"));
        redTeam.addMember("Roger", 41, new Address(40, "Some street", "SomeCity"));
        redTeam.addMember("Cynthia", 34, new Address(45, "Some street", "SomeCity"));

        blueTeam = new TeamWithLeader("BLUE", "Bob", 44, new Address(12, "Dandelion street", "Metropolis"));
        blueTeam.addMember("Marguerite", 61, new Address(55, "Other street", "OtherCity"));
        blueTeam.addVip(0, "Robert", 60, new Address(58, "Other street", "OtherCity"));
        blueTeam.addVip(1, "Marcia", 62, new Address(60, "Other street", "OtherCity"));
    }

    @Test
    public void testSimpleEquality() {
        Specification<Team> equalSpecification = new AttributeSpecification<>("name", new EqualSpecification<>("RED"));
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isTrue();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isFalse();
    }

    @Test
    public void testNullCandidate() {
        Specification<Team> equalSpecification = new AttributeSpecification<>("name", new EqualSpecification<>("RED"));
        assertThat(equalSpecification.isSatisfiedBy(null)).isFalse();
    }

    @Test
    public void testNestedEquality() {
        Specification<Team> equalSpecification = new AttributeSpecification<>("leader.name",
                new EqualSpecification<>("Alice"));
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isTrue();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isFalse();
    }

    @Test
    public void testNestedArrayEquality() {
        Specification<Team> equalSpecification = new AttributeSpecification<>("vips.address.city",
                new EqualSpecification<>("OtherCity"));
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isFalse();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isTrue();
    }

    @Test
    public void testNestedCollectionEquality() {
        Specification<Team> equalSpecification = new AttributeSpecification<>("members.address.city",
                new EqualSpecification<>("SomeCity"));
        assertThat(equalSpecification.isSatisfiedBy(redTeam)).isTrue();
        assertThat(equalSpecification.isSatisfiedBy(blueTeam)).isFalse();
    }

    @Test
    public void testEqualityAsPredicate() {
        Specification<Team> equalSpecification = new AttributeSpecification<Team, String>("leader.name",
                new EqualSpecification<>("Alice")).and(
                new AttributeSpecification<Team, String>("members.address.street",
                        new EqualSpecification<>("Other street")).negate());
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(equalSpecification.asPredicate())
                .collect(Collectors.toList())).containsExactly(redTeam);
    }

    @Test
    public void testGreaterThan() {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number",
                        new GreaterThanSpecification<>(60)).asPredicate())
                .collect(Collectors.toList())).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number",
                        new GreaterThanSpecification<>(50)).asPredicate())
                .collect(Collectors.toList())).containsExactly(blueTeam);
    }

    @Test
    public void testGreaterThanOrEqual() {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number", new GreaterThanSpecification<>(60)).or(
                        new AttributeSpecification<>("members.address.number", new EqualSpecification<>(60)))
                        .asPredicate())
                .collect(Collectors.toList())).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number", new GreaterThanSpecification<>(55)).or(
                        new AttributeSpecification<>("members.address.number", new EqualSpecification<>(55)))
                        .asPredicate())
                .collect(Collectors.toList())).containsExactly(blueTeam);
    }

    @Test
    public void testLessThan() {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number",
                        new LessThanSpecification<>(40)).asPredicate())
                .collect(Collectors.toList())).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number",
                        new LessThanSpecification<>(45)).asPredicate())
                .collect(Collectors.toList())).containsExactly(redTeam);
    }

    @Test
    public void testLessThanOrEqual() {
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number", new LessThanSpecification<>(39)).or(
                        new AttributeSpecification<>("members.address.number", new EqualSpecification<>(39)))
                        .asPredicate())
                .collect(Collectors.toList())).isEmpty();
        assertThat(Stream.of(redTeam, blueTeam)
                .filter(new AttributeSpecification<>("members.address.number", new LessThanSpecification<>(55)).or(
                        new AttributeSpecification<>("members.address.number", new EqualSpecification<>(55)))
                        .asPredicate())
                .collect(Collectors.toList())).containsExactly(redTeam, blueTeam);
    }

    @Test
    public void testToString() {
        Specification<Team> spec = new AttributeSpecification<Team, Integer>("members.address.number",
                new LessThanSpecification<>(40)).and(new AttributeSpecification<Team, Integer>("members.address.number",
                new GreaterThanSpecification<>(20)).or(
                new AttributeSpecification<>("members.address.number", new EqualSpecification<>(20))));
        assertThat(spec.toString()).isEqualTo(
                "(members.address.number < 40) ∧ ((members.address.number > 20) ∨ (members.address.number =" + " 20))");
    }
}
