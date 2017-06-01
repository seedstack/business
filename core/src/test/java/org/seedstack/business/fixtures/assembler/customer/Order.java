/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.seedstack.business.domain.BaseAggregateRoot;


public class Order extends BaseAggregateRoot<String> {

    private String orderId;

    private String product;

    private int price;

    private String otherDetails;

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getId() {
        return orderId;
    }

    public Order() {
    }

    public Order(String id, String product) {
        this.orderId = id;
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
