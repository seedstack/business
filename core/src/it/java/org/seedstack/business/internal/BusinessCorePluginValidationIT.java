/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.seedstack.business.sample.domain.activation.Activation;
import org.seedstack.business.sample.domain.activation.ActivationException;
import org.seedstack.business.sample.domain.activation.ActivationFactory;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.validation.api.ValidationException;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class check the validation feature of the business support
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class BusinessCorePluginValidationIT extends AbstractSeedIT {

    @Inject
    ActivationFactory activationFactory;

    @Test(expected = ValidationException.class)
    public void activation_factory_should_validate_on_parameters() throws ActivationException {
        assertThat(activationFactory).isNotNull();
        activationFactory.createNewActivation(null, "pok");
    }

    @Test(expected = ValidationException.class)
    public void activation_factory_should_validate_aggreggate_root() throws ActivationException {
        assertThat(activationFactory).isNotNull();

        // Check AOP on factory create method
        activationFactory.createNewActivation("id", "");
    }

    @Test
    public void activation_factory_should_validate_on_returned() throws ActivationException {
        assertThat(activationFactory).isNotNull();
        Activation createNewActivation = activationFactory.createNewActivation("zerezr", "pok");
        assertThat(createNewActivation).isNotNull();
    }

}
