/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * Interface for generating ever-incrementing numbers to be used as identity of entities.
 *
 * @param <IdT> the type of generated numbers.
 */
public interface SequenceGenerator<IdT extends Number> extends IdentityGenerator<IdT> {

}
