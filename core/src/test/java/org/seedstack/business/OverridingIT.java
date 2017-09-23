/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.junit.Test;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.BaseFactory;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.util.inmemory.BaseInMemoryRepository;
import org.seedstack.seed.it.AbstractSeedIT;

public class OverridingIT extends AbstractSeedIT {

  @Inject
  private SomeRepository someRepository;
  @Inject
  private Repository<SomeAggregate, String> someAggregateRepository;
  @Inject
  private Factory<SomeAggregate> someAggregateFactory;
  @Inject
  private SomeFactory someFactory;
  @Inject
  private SomeService someService;
  @Inject
  private SomePolicy somePolicy;
  @Inject
  private Assembler<SomeAggregate, SomeDto> someDtoAssembler;

  @Test
  public void overrideRepository() throws Exception {
    assertThat(someRepository).isInstanceOf(SomeTestRepository.class);
    assertThat(someAggregateRepository).isInstanceOf(SomeTestRepository.class);
  }

  @Test
  public void overrideFactory() throws Exception {
    assertThat(someAggregateFactory).isInstanceOf(SomeTestFactory.class);
    assertThat(someFactory).isInstanceOf(SomeTestFactory.class);
  }

  @Test
  public void overrideService() throws Exception {
    assertThat(someService).isInstanceOf(SomeTestService.class);
  }

  @Test
  public void overridePolicy() throws Exception {
    assertThat(somePolicy).isInstanceOf(SomeTestPolicy.class);
  }

  @Test
  public void overrideAssembler() throws Exception {
    assertThat(someDtoAssembler).isInstanceOf(SomeTestAssembler.class);
  }

  interface SomeRepository extends Repository<SomeAggregate, String> {

  }

  interface SomeFactory extends Factory<SomeAggregate> {

  }

  @Service
  interface SomeService {

  }

  @DomainPolicy
  interface SomePolicy {

  }

  static class SomeAggregate extends BaseAggregateRoot<String> {

  }

  static class SomeDto {

  }

  static class SomeNormalRepository extends BaseInMemoryRepository<SomeAggregate, String> implements
    SomeRepository {

  }

  @Overriding
  static class SomeTestRepository extends BaseInMemoryRepository<SomeAggregate, String> implements
    SomeRepository {

  }

  static class SomeNormalFactory extends BaseFactory<SomeAggregate> implements SomeFactory {

  }

  @Overriding
  static class SomeTestFactory extends BaseFactory<SomeAggregate> implements SomeFactory {

  }

  static class SomeAssembler extends BaseAssembler<SomeAggregate, SomeDto> {

    @Override
    public void mergeAggregateIntoDto(SomeAggregate sourceAggregate, SomeDto targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(SomeDto sourceDto, SomeAggregate targetAggregate) {

    }
  }

  @Overriding
  static class SomeTestAssembler extends BaseAssembler<SomeAggregate, SomeDto> {

    @Override
    public void mergeAggregateIntoDto(SomeAggregate sourceAggregate, SomeDto targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(SomeDto sourceDto, SomeAggregate targetAggregate) {

    }
  }

  static class SomeNormalService implements SomeService {

  }

  @Overriding
  static class SomeTestService implements SomeService {

  }

  static class SomeNormalPolicy implements SomePolicy {

  }

  @Overriding
  static class SomeTestPolicy implements SomePolicy {

  }
}
