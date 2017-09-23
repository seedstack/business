/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler;

import org.javatuples.Tuple;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.shed.reflect.Classes;

/**
 * An helper base class that can be extended to create an assembler between a tuple of aggregates and a DTO.
 *
 * @param <TupleT> the tuple of aggregates type.
 * @param <DtoT>   the dto type.
 */
public abstract class BaseTupleAssembler<TupleT extends Tuple, DtoT> implements Assembler<TupleT, DtoT> {

  private final Class<DtoT> dtoClass;

  /**
   * Creates an assembler with automatic resolution of its DTO class.
   */
  @SuppressWarnings("unchecked")
  public BaseTupleAssembler() {
    this.dtoClass = (Class<DtoT>) BusinessUtils.resolveGenerics(BaseTupleAssembler.class, getClass())[1];
  }

  /**
   * Creates an assembler with the DTO class explicitly specified.
   *
   * @param dtoClass the DTO class.
   */
  protected BaseTupleAssembler(Class<DtoT> dtoClass) {
    this.dtoClass = dtoClass;
  }

  @Override
  public Class<DtoT> getDtoClass() {
    return this.dtoClass;
  }

  @Override
  public DtoT createDto() {
    return Classes.instantiateDefault(getDtoClass());
  }
}
