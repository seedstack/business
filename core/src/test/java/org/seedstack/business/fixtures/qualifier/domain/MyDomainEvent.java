/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.qualifier.domain;

import org.seedstack.business.domain.BaseDomainEvent;

public class MyDomainEvent extends BaseDomainEvent {

    private String cause;

    public MyDomainEvent(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
