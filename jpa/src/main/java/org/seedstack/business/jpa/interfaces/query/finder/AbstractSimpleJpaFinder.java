/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.interfaces.query.finder;


/**
 * A Simple Generic Finder that handles simple JPA Query.
 * <p>
 * Asks the developers to overrides two methods:
 * <ul>
 * <li>computeFullRequestSize(Map<String, Object> criteria)</li>
 * <li>computeResultList(Range range, Map<String, Object> criteria)</li>
 * </ul>
 * </p>
 *
 * @param <Item> the dto to paginate
 * @author epo.jemba@ext.mpsa.com
 * @deprecated This class will be removed in the next version. Please use {@link BaseSimpleJpaFinder} instead.
 */
@Deprecated
public abstract class AbstractSimpleJpaFinder<Item> extends BaseSimpleJpaFinder<Item> {
}
