/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

/**
 * This interface has to be extended in order to create a Domain Repository <em>interface</em>.
 * <p/>
 * To be a valid repository interface, Type must respect the followings :
 * <ul>
 * <li> be an interface
 * <li> extends {@link GenericRepository}
 * </ul>
 * <p/>
 * The following is a valid Domain repository interface.
 * <pre>
 *
 *  public interface ProductRepository extends GenericRepository{@literal <Product,String>} {
 *     // nothing needed, but you can add methods with specifics
 *     // then implements them
 *  }
 * </pre>
 * <p/>
 * Then this interface has to be implemented by the actual repository implementation . See {@link BaseRepository} for details.
 *
 * @param <AGGREGATE> the type of the aggregate root class.
 * @param <KEY>       the type of the aggregate root class.
 * @author epo.jemba@ext.mpsa.com
 */
public interface GenericRepository<AGGREGATE extends AggregateRoot<KEY>, KEY> extends Repository<AGGREGATE, KEY> {
}