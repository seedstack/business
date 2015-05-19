/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.fixtures;

import org.seedstack.business.api.Event;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
public class MyEvent implements Event {

    public String businessInfo;

    public MyEvent(String businessInfo) {

        this.businessInfo = businessInfo;
    }

    public String getBusinessInfo() {

        return businessInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyEvent myEvent = (MyEvent) o;

        if (businessInfo != null ? !businessInfo.equals(myEvent.businessInfo) : myEvent.businessInfo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return businessInfo != null ? businessInfo.hashCode() : 0;
    }
}
