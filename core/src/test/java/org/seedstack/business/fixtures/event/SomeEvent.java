/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event;

import org.seedstack.business.Event;


public class SomeEvent implements Event {

    public String businessInfo;

    public SomeEvent(String businessInfo) {

        this.businessInfo = businessInfo;
    }

    public String getBusinessInfo() {

        return businessInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SomeEvent someEvent = (SomeEvent) o;

        if (businessInfo != null ? !businessInfo.equals(someEvent.businessInfo) : someEvent.businessInfo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return businessInfo != null ? businessInfo.hashCode() : 0;
    }
}
