/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.domain.specification.AndSpecification;
import org.seedstack.business.domain.specification.GreaterThanSpecification;
import org.seedstack.business.domain.specification.NotSpecification;
import org.seedstack.business.domain.specification.OrSpecification;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.domain.specification.StringEqualSpecification;
import org.seedstack.business.domain.specification.builder.SpecificationBuilder;
import org.seedstack.business.fixtures.domain.specification.Address;
import org.seedstack.business.fixtures.domain.specification.Team;
import org.seedstack.business.fixtures.domain.specification.TeamWithLeader;
import org.seedstack.business.domain.specification.AggregateClassSpecification;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class SpecificationBuilderTest {
    private SpecificationBuilder specificationBuilder;
    private Team redTeam;
    private Team blueTeam;
    private Team greenTeam;

    @Before
    public void setUp() throws Exception {
        specificationBuilder = new SpecificationBuilderImpl();

        redTeam = new TeamWithLeader("RED", "Alice", 23, new Address(20, "Tulip street", "SinCity"));
        redTeam.addMember("Roger", 41, new Address(40, "Some street", "SomeCity"));
        redTeam.addMember("Cynthia", 34, new Address(45, "Some street", "SomeCity"));

        blueTeam = new TeamWithLeader("BLUE", "Bob", 44, new Address(12, "Dandelion street", "Metropolis"));
        blueTeam.addMember("Marguerite", 61, new Address(55, "Other street", "OtherCity"));

        greenTeam = new TeamWithLeader("GREEN", "Sammy", 36, new Address(11, "Tulip street", "Metropolis"));
    }

    @Test
    public void testComposition() throws Exception {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.name").equalTo("Alice")
                .or()
                .property("leader.age").greaterThan(25)
                .orNot()
                .property("name").equalTo("GREEN").and()
                .property("leader.name").equalTo("Sammy")
                .build();

        System.out.println(spec);

        assertThat(spec).isInstanceOf(AggregateClassSpecification.class);
        spec = ((AggregateClassSpecification<Team>) spec).getSpecification();
        assertThat(((OrSpecification<Team>) spec).getLhs()).isInstanceOf(OrSpecification.class);
        assertThat(((OrSpecification<Team>) ((OrSpecification<Team>) spec).getLhs()).getLhs()).isInstanceOf(StringEqualSpecification.class);
        assertThat(((OrSpecification<Team>) ((OrSpecification<Team>) spec).getLhs()).getRhs()).isInstanceOf(GreaterThanSpecification.class);
        assertThat(((OrSpecification<Team>) spec).getRhs()).isInstanceOf(NotSpecification.class);
        assertThat(((NotSpecification<Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).isInstanceOf(AndSpecification.class);
        assertThat(((AndSpecification<Team>) ((NotSpecification<Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).getLhs()).isInstanceOf(StringEqualSpecification.class);
        assertThat(((AndSpecification<Team>) ((NotSpecification<Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).getLhs()).isInstanceOf(StringEqualSpecification.class);
    }

    @Test
    public void testBetween() throws Exception {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.age").between(20, 30)
                .build();
        assertThat(spec.toString()).isEqualTo("Team[((leader.age > 20) ∨ (leader.age = 20)) ∧ ((leader.age < 30) ∨ (leader.age = 30))]");
    }

    @Test
    public void testStringOptions() throws Exception {
        Specification<Team> specEqual = specificationBuilder.of(Team.class)
                .property("leader.name").equalTo("ALICE").ignoringCase()
                .build();

        Specification<Team> specMatching = specificationBuilder.of(Team.class)
                .property("leader.name").matching("AL?CE").ignoringCase()
                .build();

        assertThat(Stream.of(redTeam, blueTeam, greenTeam).filter(specEqual.asPredicate()).collect(toList())).containsExactly(redTeam);
        assertThat(Stream.of(redTeam, blueTeam, greenTeam).filter(specMatching.asPredicate()).collect(toList())).containsExactly(redTeam);
    }

    @Test
    public void testToString() throws Exception {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.name").not().equalTo("Alice")
                .or()
                .property("leader.age").greaterThan(25)
                .build();
        assertThat(spec.toString()).isEqualTo("Team[(¬(leader.name = Alice)) ∨ (leader.age > 25)]");
    }
}
