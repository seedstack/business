/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.domain.product;

import org.seedstack.business.domain.DomainPolicy;

@DomainPolicy
public interface ProductNamePolicy {

    String transform(Short productCode);

    Short extractNumber(String productCode);

}
