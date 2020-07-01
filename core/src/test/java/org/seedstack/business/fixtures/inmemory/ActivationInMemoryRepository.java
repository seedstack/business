/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.inmemory;

import org.seedstack.business.fixtures.domain.activation.Activation;
import org.seedstack.business.fixtures.domain.activation.ActivationRepository;
import org.seedstack.business.util.inmemory.BaseInMemoryRepository;

public class ActivationInMemoryRepository extends BaseInMemoryRepository<Activation, String> implements
        ActivationRepository {

}
