/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.seedstack.business.core.domain.InMemoryRepository;
import org.seedstack.business.sample.domain.customer.Customer;
import org.seedstack.business.sample.domain.customer.CustomerId;
import org.junit.Test;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 26/09/2014
 */
public class poc {

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        TypeLiteral<?> defaultImplType = TypeLiteral.get(Types.newParameterizedType(InMemoryRepository.class, Customer.class, CustomerId.class));
        Object o = defaultImplType.getRawType().newInstance();
    }
}
