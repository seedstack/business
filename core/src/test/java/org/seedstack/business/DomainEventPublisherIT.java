/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import static org.junit.Assert.fail;

import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.business.fixtures.event.MyDomainEvent;
import org.seedstack.business.fixtures.event.MyDomainEvent2;
import org.seedstack.business.fixtures.event.cyclic.Event1;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.it.SeedITRunner;


/**
 * Tests fire event whether the handler throws an exception or not.
 */
@RunWith(SeedITRunner.class)
public class DomainEventPublisherIT {

  public static int countMyEvent = 0;
  public static int countGenericEvent = 0;

  @Inject
  private DomainEventPublisher domainEventPublisher;

  @Test
  public void fireEvent() {
    countMyEvent = 0;
    domainEventPublisher.publish(new MyDomainEvent());
    Assertions.assertThat(countMyEvent).isEqualTo(1);
  }

  @Test
  public void fireGenericEvent() {
    countGenericEvent = 0;
    domainEventPublisher.publish(new MyDomainEvent());
    Assertions.assertThat(countGenericEvent).isEqualTo(1);
  }

  @Test
  public void fireEventThenHandlerFailed() throws InterruptedException {
    try {
      domainEventPublisher.publish(new MyDomainEvent2());
      fail();
    } catch (Exception e) {
      Assertions.assertThat(e).isNotNull();
    }
  }

  @Test(expected = BusinessException.class)
  public void fire_cyclic_events() {
    domainEventPublisher.publish(new Event1());
  }
}
