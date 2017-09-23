/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.seedstack.business.pagination.Slice;

/**
 * Terminal operation of the {@link FluentAssembler} DSL, allowing to choose the result of merging a
 * DTO into multiple aggregates (stream, list, set, array, ...).
 *
 * @param <T> the type of the merged item.
 */
public interface MergeAs<T> {

  /**
   * Merge as a stream of items.
   *
   * @return the stream.
   */
  Stream<T> asStream();

  /**
   * Merge as a collection of items.
   *
   * @param <C>                the type of the collection of items.
   * @param collectionSupplier the provider of a (preferably empty) collection.
   * @return the collection.
   */
  <C extends Collection<T>> C asCollection(Supplier<C> collectionSupplier);

  /**
   * Merge as a list of items.
   *
   * @return the list.
   */
  List<T> asList();

  /**
   * Merge as a set of items.
   *
   * @return the set.
   */
  Set<T> asSet();

  /**
   * Merge as a slice of items.
   *
   * @return the slice.
   */
  Slice<T> asSlice();

  /**
   * Merge as an array of items.
   *
   * @return the array.
   */
  T[] asArray();
}
