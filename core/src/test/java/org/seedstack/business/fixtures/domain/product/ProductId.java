/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.domain.product;

import org.seedstack.business.domain.BaseValueObject;

public class ProductId extends BaseValueObject {

    /**
     * between 0 and 1000
     */
    private Short storeId;
    private String productCode;

    ProductId() {
    }

    public ProductId(Short storeId, String productCode) {
        this.storeId = storeId;
        this.productCode = productCode;
    }

    public ProductId(Short storeId, Short productCode) {
        this.storeId = storeId;
        this.productCode = "ean13-" + productCode;
    }

    public Short getStoreId() {
        return storeId;
    }

    public String getProductCode() {
        return productCode;
    }

}
