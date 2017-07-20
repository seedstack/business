/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * Interface for generating ever-incrementing numbers as identity of entities.
 *
 * @param <ID> the type of generated numbers.
 */
public interface SequenceGenerator<ID extends Number> extends IdentityGenerator<ID> {
}
