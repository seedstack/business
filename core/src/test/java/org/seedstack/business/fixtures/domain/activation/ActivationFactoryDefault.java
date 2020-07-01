/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.activation;

import org.seedstack.business.domain.BaseFactory;

public class ActivationFactoryDefault extends BaseFactory<Activation> implements ActivationFactory {

    @Override
    public Activation createNewActivation(String id, String description) throws ActivationException {
        if (id.equals("42")) {
            throw new ActivationException();
        }

        Activation activation = new Activation(id);
        activation.setDescription(description);
        return activation;
    }
}
