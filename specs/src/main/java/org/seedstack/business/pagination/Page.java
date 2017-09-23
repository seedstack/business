/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.pagination;

/**
 * An indexed page of contiguous items taken from a bigger list.
 *
 * @param <T> the item type
 */
public interface Page<T> extends Slice<T> {

  /**
   * Returns the index of the page.
   *
   * @return the page index.
   */
  long getIndex();

  /**
   * Returns the maximum capacity of the page.
   *
   * @return the maximum capacity of the page.
   */
  long getCapacity();

  /**
   * Returns the total size of the list this page is derived from.
   *
   * @return the total size of the bigger list.
   */
  long getTotalSize();
}
