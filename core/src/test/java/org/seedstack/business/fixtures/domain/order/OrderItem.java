/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.domain.order;

import org.seedstack.business.domain.BaseEntity;
import org.seedstack.business.fixtures.domain.product.ProductId;


public class OrderItem extends BaseEntity<Long> {

  private Long id;
  private int quantity;
  private ProductId productId;

  public OrderItem() {
  }

  public OrderItem(int quantity, ProductId productId) {
    this.quantity = quantity;
    this.productId = productId;
  }

  public ProductId getProductId() {
    return productId;
  }

  public void setProductId(ProductId productId) {
    this.productId = productId;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
