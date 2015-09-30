/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.registry.fixtures.policy;

import org.seedstack.business.internal.registry.fixtures.domain.Product;

/**
 * Dummy class.
 * @author thierry.bouvet@mpsa.com
 *
 */
@PolicyQualifier
public class RebatePolicyInternalWithQualifier implements RebatePolicy{

	@Override
	public float calculateRebate(Product product, float quantity) {
		return quantity / 10;
	}

}
