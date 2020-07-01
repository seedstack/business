/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import java.util.Date;
import org.seedstack.business.domain.BaseAggregateRoot;

public class Order extends BaseAggregateRoot<String> {

    private String id;
    private String product;
    private int price;
    private Date orderDate;
    private String otherDetails;

    public Order() {
    }

    public Order(String id, String product) {
        this.id = id;
        this.product = product;
    }

    public Order(String id, String product, Date orderDate) {
        this.id = id;
        this.product = product;
        this.orderDate = orderDate;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String getId() {
        return id;
    }
}
