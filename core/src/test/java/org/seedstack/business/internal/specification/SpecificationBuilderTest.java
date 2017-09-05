/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.fixtures.domain.specification.Address;
import org.seedstack.business.fixtures.domain.specification.Team;
import org.seedstack.business.fixtures.domain.specification.TeamWithLeader;
import org.seedstack.business.specification.AndSpecification;
import org.seedstack.business.specification.SubstitutableSpecification;
import org.seedstack.business.specification.NotSpecification;
import org.seedstack.business.specification.OrSpecification;
import org.seedstack.business.specification.PropertySpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;

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
                .property("leader.name").equalTo("ALICE").ignoringCase()
                .or()
                .property("leader.age").greaterThan(25)
                .orNot()
                .property("name").equalTo("GREEN").and()
                .property("leader.name").equalTo("Sammy")
                .build();

        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        spec = ((SubstitutableSpecification<Team>) spec).getSubstitute();
        assertThat(spec).isInstanceOf(OrSpecification.class);
        assertThat(((OrSpecification<Team>) spec).getLhs()).isInstanceOf(OrSpecification.class);
        assertThat(((OrSpecification<Team>) ((OrSpecification<Team>) spec).getLhs()).getLhs()).isInstanceOf(PropertySpecification.class);
        assertThat(((PropertySpecification<Team, ?>) ((OrSpecification<Team>) ((OrSpecification<Team>) spec).getLhs()).getLhs()).getPath()).isEqualTo("leader.name");
        assertThat(((OrSpecification<Team>) ((OrSpecification<Team>) spec).getLhs()).getRhs()).isInstanceOf(PropertySpecification.class);
        assertThat(((PropertySpecification<? super Team, ?>) ((OrSpecification<Team>) ((OrSpecification<Team>) spec).getLhs()).getRhs()).getPath()).isEqualTo("leader.age");
        assertThat(((OrSpecification<Team>) spec).getRhs()).isInstanceOf(NotSpecification.class);
        assertThat(((NotSpecification<? super Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).isInstanceOf(AndSpecification.class);
        assertThat(((AndSpecification<? super Team>) ((NotSpecification<? super Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).getLhs()).isInstanceOf(PropertySpecification.class);
        assertThat(((PropertySpecification<? super Team, ?>) ((AndSpecification<? super Team>) ((NotSpecification<? super Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).getLhs()).getPath()).isEqualTo("name");
        assertThat(((PropertySpecification<? super Team, ?>) ((AndSpecification<? super Team>) ((NotSpecification<? super Team>) ((OrSpecification<Team>) spec).getRhs()).getSpecification()).getRhs()).getPath()).isEqualTo("leader.name");
    }

    @Test
    public void testBetween() throws Exception {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.age").between(20, 40)
                .build();
        assertThat(Stream.of(redTeam, blueTeam, greenTeam).filter(spec.asPredicate()).collect(toList())).containsExactly(redTeam, greenTeam);
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

    @Test
    public void testIdentity() throws Exception {
        Specification<Team> spec = specificationBuilder.ofAggregate(Team.class)
                .identity().is("BLUE")
                .or()
                .identity().is("GREEN")
                .build();
        assertThat(Stream.of(redTeam, blueTeam, greenTeam).filter(spec.asPredicate()).collect(toList())).containsExactly(blueTeam, greenTeam);
    }
}
