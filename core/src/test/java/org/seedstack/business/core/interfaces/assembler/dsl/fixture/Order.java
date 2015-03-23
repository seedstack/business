/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl.fixture;

import org.seedstack.business.api.domain.base.BaseAggregateRoot;

/**
* @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
*/
public class Order extends BaseAggregateRoot<String> {

    private String id;

    private String product;

    private int price;

    private String otherDetails;

    @Override
    public String getEntityId() {
        return id;
    }

    public Order() {
    }

    public Order(String id, String product) {
        this.id = id;
        this.product = product;
    }

    public Order(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
}
