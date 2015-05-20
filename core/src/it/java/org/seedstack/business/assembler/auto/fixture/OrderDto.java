/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto.fixture;

import org.seedstack.business.api.interfaces.assembler.DtoOf;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DtoOf(Order.class)
public class OrderDto {

    private String id;

    private Address billingAddress;

    public OrderDto() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }
}
