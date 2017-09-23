/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler.customer;

import java.util.Date;
import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.FactoryArgument;


@DtoOf(Order.class) // specify the link to the aggregate root
public class OrderDto {

  private String orderId;
  private String product;
  private String customerName;
  private int price;
  private Date orderDate;

  public OrderDto() {
  }

  public OrderDto(String id, String product) {
    this.orderId = id;
    this.product = product;
  }

  public OrderDto(String id, String product, int price) {
    this.orderId = id;
    this.product = product;
    this.price = price;
  }

  @AggregateId // no need for index because id is primitive
  @FactoryArgument(index = 0)
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @FactoryArgument(index = 1)
  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }
}
