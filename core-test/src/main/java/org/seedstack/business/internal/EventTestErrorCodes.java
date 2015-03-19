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


import org.seedstack.seed.core.api.ErrorCode;

/**
 * Error codes for event fixtures.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 10/06/2014
 */
public enum EventTestErrorCodes implements ErrorCode {
    EVENT_WAS_HANDLER_BY,
    EVENT_WAS_NOT_HANDLER_BY,
    EVENT_WAS_NOT_EXACTLY_HANDLER_BY,
    FAILED_TO_INVOKE_METHOD,
    HANDLER_WAS_NOT_CALLED,
    HANDLER_WAS_NOT_CALLED_WITH_EXPECTED_EVENT,
}
