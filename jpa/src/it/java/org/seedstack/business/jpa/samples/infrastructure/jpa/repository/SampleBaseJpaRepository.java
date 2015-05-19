/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.infrastructure.jpa.repository;

import org.seedstack.business.jpa.infrastructure.repository.BaseJpaRepository;
import org.seedstack.business.jpa.samples.domain.base.SampleBaseJpaAggregateRoot;
import org.seedstack.business.jpa.samples.domain.base.SampleBaseRepository;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
public class SampleBaseJpaRepository extends BaseJpaRepository<SampleBaseJpaAggregateRoot, String> implements SampleBaseRepository {
}
