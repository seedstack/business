/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.helpers;

/**
 * This class is the main containers of all {@link org.seedstack.business.api.domain.Repository}s.
 * <p>
 * it can be injected via
 * <pre>
 * {@literal @}Inject
 * Repositories repositories;
 * </pre>
 * and uses like the following.
 * <pre>
 * ...
 * Product product = repositories.load("ean13-12");
 * ...
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 * @deprecated Replaced by {@link org.seedstack.business.api.domain.Repositories}
 */
@Deprecated
public interface Repositories extends org.seedstack.business.api.domain.Repositories {
}
