/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.infrastructure.persistence.multirepo;

import org.seedstack.business.core.domain.InMemoryRepository;
import org.seedstack.business.sample.domain.multi.Multi;
import org.seedstack.business.sample.domain.multi.MultiRepository;
import org.seedstack.seed.persistence.inmemory.api.Store;

import javax.inject.Named;

/**
 * Dummy repository for test
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
@Named("multi1")
@Store("MultiRepository1InMemory")
public class MultiRepository1InMemory extends InMemoryRepository<Multi,String> implements MultiRepository {
}
