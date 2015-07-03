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
 * <p>
 * To be a valid repository interface, Type must respect the followings:
 * </p>
 * <ul>
 * <li>be an interface</li>
 * <li>extends {@link GenericRepository}</li>
 * </ul>
 * <p>
 * The following is a valid Domain repository interface.
 * </p>
 * <pre>
 *  public interface ProductRepository extends GenericRepository&lt;Product,String&gt; {
 *     // nothing needed, but you can add methods with specifics
 *     // then implements them
 *  }
 * </pre>
 * <p/>
 * Then this interface has to be implemented by the actual repository implementation.
 *
 * @param <A> the type of the aggregate root class.
 * @param <K> the type of the aggregate root class.
 * @author epo.jemba@ext.mpsa.com
 */
public interface GenericRepository<A extends AggregateRoot<K>, K> extends Repository<A, K> {
}