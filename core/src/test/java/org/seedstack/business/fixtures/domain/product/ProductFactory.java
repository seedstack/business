/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.product;

import org.seedstack.business.domain.Factory;

public interface ProductFactory extends Factory<Product> {

    Product createProduct(Short storeId, Short productCode);

    Product createProduct(Short storeId, Short productCode, String name, String description);

}
