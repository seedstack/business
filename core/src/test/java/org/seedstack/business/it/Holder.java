/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.it;

import org.seedstack.business.fixtures.application.GenericService;
import org.seedstack.business.fixtures.application.IndexService;
import org.seedstack.business.fixtures.domain.activation.ActivationFactory;
import org.seedstack.business.fixtures.domain.activation.ActivationRepository;
import org.seedstack.business.fixtures.domain.customer.CustomerFactory;
import org.seedstack.business.fixtures.domain.customer.CustomerRepository;
import org.seedstack.business.fixtures.domain.customer.CustomerSampleDomainService;
import org.seedstack.business.fixtures.domain.customer.CustomerSampleService;
import org.seedstack.business.fixtures.domain.order.OrderFactory;
import org.seedstack.business.fixtures.domain.order.OrderRepository;
import org.seedstack.business.fixtures.domain.product.ProductFactory;
import org.seedstack.business.fixtures.domain.product.ProductNamePolicy;
import org.seedstack.business.fixtures.domain.product.ProductRepository;
import org.seedstack.business.fixtures.finder.CustomerFinder;
import org.seedstack.seed.it.ITBind;

import javax.inject.Inject;

@ITBind
class Holder {
    @Inject
    ActivationRepository activationRepository;

    @Inject
    ActivationFactory activationFactory;

    @Inject
    CustomerRepository customerRepo;

    @Inject
    IndexService indexService;

    @Inject
    CustomerSampleService customerService;

    @Inject
    CustomerSampleDomainService customerDomainService;

    @Inject
    CustomerFactory customerFactory;

    @Inject
    CustomerFinder customerFinder;

    @Inject
    OrderRepository orderRepo;

    @Inject
    OrderFactory orderFactory;

    @Inject
    ProductRepository productRepo;

    @Inject
    ProductFactory productFactory;

    @Inject
    ProductNamePolicy productNamePolicy;

    @Inject
    GenericService<String> genericService;
}