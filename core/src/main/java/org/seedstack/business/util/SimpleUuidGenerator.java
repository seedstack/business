/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.util;

import java.util.Map;
import java.util.UUID;
import javax.inject.Named;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.UuidGenerator;

/**
 * Identity generator that generates {@link UUID} by using {@link UUID#randomUUID()}.
 */
@Named("simpleUUID")
public class SimpleUuidGenerator implements UuidGenerator {

  @Override
  public <E extends Entity<UUID>> UUID generate(Class<E> entityClass, Map<String, String> entityProperties) {
    return UUID.randomUUID();
  }
}
