/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.specification;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.fixtures.domain.specification.Address;
import org.seedstack.business.fixtures.domain.specification.Team;
import org.seedstack.business.fixtures.domain.specification.TeamWithLeader;
import org.seedstack.business.specification.AndSpecification;
import org.seedstack.business.specification.AttributeSpecification;
import org.seedstack.business.specification.EqualSpecification;
import org.seedstack.business.specification.FalseSpecification;
import org.seedstack.business.specification.OrSpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.SubstitutableSpecification;
import org.seedstack.business.specification.TrueSpecification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;

public class SpecificationBuilderTest {

    private SpecificationBuilder specificationBuilder;
    private Team redTeam;
    private Team blueTeam;
    private Team greenTeam;

    @Before
    public void setUp() {
        specificationBuilder = new SpecificationBuilderImpl();

        redTeam = new TeamWithLeader("RED", "Alice", 23, new Address(20, "Tulip street", "SinCity"));
        redTeam.addMember("Roger", 41, new Address(40, "Some street", "SomeCity"));
        redTeam.addMember("Cynthia", 34, new Address(45, "Some street", "SomeCity"));

        blueTeam = new TeamWithLeader("BLUE", "Bob", 44, new Address(12, "Dandelion street", "Metropolis"));
        blueTeam.addMember("Marguerite", 61, new Address(55, "Other street", "OtherCity"));

        greenTeam = new TeamWithLeader("GREEN", "Sammy", 36, new Address(11, "Tulip street", "Metropolis"));
    }

    @Test
    public void testBetween() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.age")
                .between(20, 40)
                .build();
        assertThat(Stream.of(redTeam, blueTeam, greenTeam)
                .filter(spec.asPredicate())
                .collect(toList())).containsExactly(redTeam, greenTeam);
    }

    @Test
    public void testGreaterOrEqual() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.age")
                .greaterThanOrEqualTo(36)
                .build();
        assertThat(Stream.of(redTeam, blueTeam, greenTeam)
                .filter(spec.asPredicate())
                .collect(toList())).containsExactly(blueTeam, greenTeam);
    }

    @Test
    public void testStringOptions() {
        Specification<Team> specEqual = specificationBuilder.of(Team.class)
                .property("leader.name")
                .equalTo("ALICE")
                .ignoringCase()
                .build();

        Specification<Team> specMatching = specificationBuilder.of(Team.class)
                .property("leader.name")
                .matching("AL?CE")
                .ignoringCase()
                .build();

        assertThat(Stream.of(redTeam, blueTeam, greenTeam)
                .filter(specEqual.asPredicate())
                .collect(toList())).containsExactly(redTeam);
        assertThat(Stream.of(redTeam, blueTeam, greenTeam)
                .filter(specMatching.asPredicate())
                .collect(toList())).containsExactly(redTeam);
    }

    @Test
    public void testToString() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.name")
                .not()
                .equalTo("Alice")
                .or()
                .property("leader.name")
                .not()
                .equalTo("Roger")
                .or()
                .property("leader.age")
                .lessThan(15)
                .and()
                .property("leader.address.number")
                .not()
                .equalTo(5)
                .and()
                .property("leader.address.city")
                .equalTo("SomeCity")
                .build();
        assertThat(spec.toString()).isEqualTo(
                "Team[¬(leader.name = Alice) ∨ ¬(leader.name = Roger) ∨ ((leader.age < 15) ∧ ¬(leader" + ".address" +
                        ".number = 5) ∧ (leader.address.city = SomeCity))]");
    }

    @Test
    public void testIdentity() {
        Specification<Team> spec = specificationBuilder.ofAggregate(Team.class)
                .identity()
                .is("BLUE")
                .or()
                .identity()
                .is("GREEN")
                .build();
        assertThat(Stream.of(redTeam, blueTeam, greenTeam)
                .filter(spec.asPredicate())
                .collect(toList())).containsExactly(blueTeam, greenTeam);
    }

    @Test
    public void buildTrueSpecification() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .all()
                .build();
        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        assertThat(((SubstitutableSpecification<Team>) spec).getSubstitute()).isInstanceOf(TrueSpecification.class);
    }

    @Test
    public void buildFalseSpecification() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .none()
                .build();
        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        assertThat(((SubstitutableSpecification<Team>) spec).getSubstitute()).isInstanceOf(FalseSpecification.class);
    }

    @Test
    public void buildUnitarySpecification() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.name")
                .equalTo("ALICE")
                .build();
        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        assertThat(((SubstitutableSpecification<Team>) spec).getSubstitute()).isInstanceOf(
                AttributeSpecification.class);
    }

    @Test
    public void buildBinaryAndSpecification() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.name").not().equalTo("aliCe")
                .and()
                .property("leader.name").equalTo("ALICE").ignoringCase()
                .build();
        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        assertThat(((SubstitutableSpecification<Team>) spec).getSubstitute()).isInstanceOf(AndSpecification.class);
    }

    @Test
    public void buildBinaryOrSpecification() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .property("leader.name").equalTo("ALICE")
                .or()
                .property("leader.name").equalTo("Alice")
                .build();
        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        assertThat(((SubstitutableSpecification<Team>) spec).getSubstitute()).isInstanceOf(OrSpecification.class);
        assertThat(
                ((OrSpecification<Team>) ((SubstitutableSpecification<Team>) spec).getSubstitute()).getSpecifications()[0])
                .isInstanceOf(
                        AttributeSpecification.class);
    }

    @Test
    public void testWhole() {
        Specification<Team> spec = specificationBuilder.of(Team.class)
                .whole()
                .equalTo(new Team("BLUE"))
                .build();
        assertThat(spec).isInstanceOf(SubstitutableSpecification.class);
        assertThat(((SubstitutableSpecification<Team>) spec).getSubstitute()).isInstanceOf(EqualSpecification.class);
        assertThat(Stream.of(redTeam, blueTeam, greenTeam)
                .filter(spec.asPredicate())
                .collect(toList())).containsExactly(blueTeam);
    }

    @Test
    public void testCombine() {
        Specification<Team> spec1 = specificationBuilder.of(Team.class)
                .property("leader.name").equalTo("Alice")
                .build();
        Specification<Team> spec2 = specificationBuilder.of(Team.class)
                .whole()
                .satisfying(spec1)
                .or()
                .property("leader.name").equalTo("Bob")
                .build();
        Specification<Team> spec3 = specificationBuilder.satisfying(spec1)
                .or()
                .property("leader.name").equalTo("Bob")
                .build();
        assertThat(Stream.of(this.redTeam, blueTeam, greenTeam).filter(spec2.asPredicate()).collect(toList()))
                .containsExactly(this.redTeam, this.blueTeam);
        assertThat(Stream.of(this.redTeam, blueTeam, greenTeam).filter(spec3.asPredicate()).collect(toList()))
                .containsExactly(this.redTeam, this.blueTeam);
    }
}
