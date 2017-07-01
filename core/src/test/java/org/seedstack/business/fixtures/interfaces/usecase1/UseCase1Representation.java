/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.interfaces.usecase1;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;


public class UseCase1Representation {


    private String activation;
    private String customer;
    private String order;
    private String productDescription;
    private String activationDescription;
    private String customerFirstName;
    private String customerLastName;
    private String orderDescription;
    private Short productStoreId;
    private String productCode;


    public UseCase1Representation() {
    }

    public UseCase1Representation( //
                                   String activation, String activationDescription,  //
                                   String customer, String customerFirstName, String customerLastName, //
                                   String order, String orderDescription, //
                                   Short productStoreId, String productCode, String productDescription) { //
        this.activation = activation;
        this.activationDescription = activationDescription;
        this.customer = customer;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.order = order;
        this.orderDescription = orderDescription;
        this.productStoreId = productStoreId;
        this.productCode = productCode;
        this.productDescription = productDescription;

    }

    @MatchingEntityId(typeIndex = 0)
    @MatchingFactoryParameter(typeIndex = 0, index = 0)
    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    @MatchingEntityId(typeIndex = 1)
    @MatchingFactoryParameter(typeIndex = 1, index = 0)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @MatchingEntityId(typeIndex = 2)
    @MatchingFactoryParameter(typeIndex = 2, index = 0)
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @MatchingEntityId(typeIndex = 3)
    @MatchingFactoryParameter(typeIndex = 3, index = 0)
    public Short getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(Short productStoreId) {
        this.productStoreId = productStoreId;
    }

    @MatchingEntityId(typeIndex = 3)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @MatchingFactoryParameter(typeIndex = 3, index = 1)
    public Short getProductCodeShort() {
        return Short.valueOf(productCode.split("-")[1]);
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @MatchingFactoryParameter(typeIndex = 0, index = 1)
    public String getActivationDescription() {
        return activationDescription;
    }

    public void setActivationDescription(String activationDescription) {
        this.activationDescription = activationDescription;
    }

    @MatchingFactoryParameter(typeIndex = 1, index = 1)
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    @MatchingFactoryParameter(typeIndex = 1, index = 2)
    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

}
