/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event.interceptors;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventInterceptor;

public class EventPrunnerInterceptor implements DomainEventInterceptor {

    @SuppressWarnings("rawtypes")
    @Override
    public List<DomainEventHandler> interceptDomainHandler(
            Collection<DomainEventHandler> handlers) {
        return Collections.emptyList();
    }

}
