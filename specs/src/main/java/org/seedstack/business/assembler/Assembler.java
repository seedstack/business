/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler;

import org.seedstack.business.assembler.dsl.FluentAssembler;

/**
 * An assembler implementation contains the logic responsible for assembling an aggregate (or a
 * tuple of multiple aggregates) into into a DTO and back. Assemblers can be used by the {@link
 * FluentAssembler} DSL to execute complex mapping tasks.
 *
 * @param <A> the type of the aggregate root or of the {@link org.javatuples.Tuple} of aggregate
 *            roots.
 * @param <D> the type of the DTO.
 * @see FluentAssembler
 */
public interface Assembler<A, D> {

  /**
   * Creates a new DTO and merge the given aggregate into it. Method {@link #createDto()} is called
   * to create the DTO instance, then {@link #mergeAggregateIntoDto(Object, Object)} is called to do
   * the merge.
   *
   * @param sourceAggregate the source aggregate.
   * @return the resulting dto.
   */
  default D createDtoFromAggregate(A sourceAggregate) {
    D dto = createDto();
    mergeAggregateIntoDto(sourceAggregate, dto);
    return dto;
  }

  /**
   * Merge a source aggregate into an existing target DTO.
   *
   * @param sourceAggregate the source aggregate.
   * @param targetDto       the target dto.
   */
  void mergeAggregateIntoDto(A sourceAggregate, D targetDto);

  /**
   * Merges a source DTO into an existing target aggregate root.
   *
   * @param sourceDto       the source dto.
   * @param targetAggregate the target aggregate.
   */
  void mergeDtoIntoAggregate(D sourceDto, A targetAggregate);

  /**
   * This method is responsible for creating a new DTO instance during the assembling task.
   *
   * @return a newly-created DTO instance.
   * @see #createDtoFromAggregate(Object)
   */
  D createDto();

  /**
   * The DTO class the assemblers works on.
   *
   * @return the DTO class.
   */
  Class<D> getDtoClass();
}
