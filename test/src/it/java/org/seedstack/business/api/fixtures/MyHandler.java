/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.fixtures;

import org.seedstack.business.api.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
public class MyHandler implements EventHandler<MyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);

    @Override
    public void handle(MyEvent event) {
        LOGGER.info("MyHandler gets a MyEvent event.");
    }
}
