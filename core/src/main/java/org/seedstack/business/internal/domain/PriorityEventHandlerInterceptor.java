/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Priority;

import org.javatuples.Pair;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventInterceptor;

public class PriorityEventHandlerInterceptor implements DomainEventInterceptor {

    @SuppressWarnings("rawtypes")
    @Override
    public List<DomainEventHandler> interceptDomainHandler(
            Collection<DomainEventHandler> handlers) {

        if (handlers == null) {
            return Collections.emptyList();
        }

        return handlers.stream().map(PriorityEventHandlerInterceptor::mapToPriority)
                .sorted((x, y) -> x.getValue1().compareTo(y.getValue1()))
                .map(Pair<DomainEventHandler, Integer>::getValue0)
                .collect(Collectors.toList());

    }

    @SuppressWarnings("rawtypes")
    private static Pair<DomainEventHandler, Integer> mapToPriority(DomainEventHandler deh) {
        Priority priority = deh.getClass().getAnnotation(Priority.class);

        if (priority == null) {
            return Pair.with(deh, Integer.MIN_VALUE);
        }
        return Pair.with(deh, priority.value());

    }

}
