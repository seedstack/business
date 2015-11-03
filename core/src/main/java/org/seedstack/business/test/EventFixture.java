/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.test;

import org.seedstack.business.Event;

/**
 * This fixture provides methods for integration test on events. The {@code given(Event event)} method allow to test
 * which handler will received a given event. And the {@code given(Class aClass)} method allow to test which event is
 * fired by a given method.
 *
 * <ul><li>
 * Test that a given event was handled by an expected <code>EventHandler</code>:
 * </li></ul>
 *
 * <pre>
 * {@literal @}Inject
 * private EventFixture fixture;
 *
 * fixture.given(eventFactory.createMyEvent())
 *     .whenFired()
 *     .wasHandledBy(MyHandler.class);
 * </pre>
 *
 * <ul><li>
 * Test that a given event was handled by exactly a provided list of <code>EventHandler</code>s:
 * </li></ul>
 *
 * <pre>
 * {@literal @}Inject
 * private EventFixture fixture;
 *
 * fixture.given(eventFactory.createMyEvent())
 *     .whenFired()
 *     .wasHandledExactlyBy(MyHandler.class, MyHandler2.class);
 * </pre>
 *
 * <ul><li>
 * Test that a given event was not handled by an expected  <code>EventHandler</code>:
 * </li></ul>
 *
 * <pre>
 * {@literal @}Inject
 * private EventFixture fixture;
 *
 * fixture.given(eventFactory.createMyEvent())
 *     .whenFired()
 *     .wasNotHandledBy(MyHandler3.class);
 * </pre>
 *
 * <ul><li>
 * Test that a given event was generated from an expected <code>method()</code> with appropriate *parameters*
 * </li></ul>
 *
 * <pre>
 * {@literal @}Inject
 * private EventFixture fixture;
 *
 * MyEvent myEvent = eventFactory.createMyEvent(SOME_EVENT_PARAM);
 * fixtures.given(MyService.class)
 *     .whenCalled("doSomething", SOME_METHOD_PARAM)
 *     .eventWasHandledBy(myEvent, MyHandler.class);
 * </pre>
 * <em> Test if <code>MyHandler</code> handler received <code>myEvent</code> event when <code>doSomething()</code> method of <code>MyService</code> is called.</em>
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
public interface EventFixture {

    /**
     * Indicates the event to test.
     *
     * @param event event to test
     * @return itself
     */
    EventProvider given(Event event);

    /**
     * Indicates the class to test.
     *
     * @param aClass class to test
     * @return itself
     */
    ServiceProvider given(Class aClass);
}
