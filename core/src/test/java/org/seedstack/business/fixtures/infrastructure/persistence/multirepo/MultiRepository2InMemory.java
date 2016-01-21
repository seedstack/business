/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.fixtures.infrastructure.persistence.multirepo;

import org.seedstack.business.fixtures.InMemoryRepository;
import org.seedstack.business.fixtures.domain.multi.Multi;
import org.seedstack.business.fixtures.domain.multi.MultiRepository;
import org.seedstack.seed.persistence.inmemory.api.Store;

import javax.inject.Named;

/**
 * Dummy repository for test
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
@Named("multi2")
@Store("MultiRepository2InMemory")
public class MultiRepository2InMemory extends InMemoryRepository <Multi,String> implements MultiRepository {

}
