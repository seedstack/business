/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import org.seedstack.coffig.Config;

@Config("business")
public class BusinessConfig {
    private EventConfig events = new EventConfig();

    @Config("events")
    public static class EventConfig {
        private boolean publishRepositoryEvents = false;

        public boolean isPublishRepositoryEvents() {
            return publishRepositoryEvents;
        }

        public EventConfig setPublishRepositoryEvents(boolean publishRepositoryEvents) {
            this.publishRepositoryEvents = publishRepositoryEvents;
            return this;
        }
    }
}
