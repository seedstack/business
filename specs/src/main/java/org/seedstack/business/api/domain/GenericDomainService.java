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

import org.seedstack.business.api.Producible;

/**
 * This interface can be used as base for any Domain Service interface.
 *
 * @author epo.jemba@ext.mpsa.com
 */
@DomainService
public interface GenericDomainService extends DomainObject, Producible {

}
