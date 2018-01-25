/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.domain.order;

import org.seedstack.business.domain.BaseValueObject;

public class OrderId extends BaseValueObject {

    private String value;

    OrderId() {
    }

    public OrderId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
