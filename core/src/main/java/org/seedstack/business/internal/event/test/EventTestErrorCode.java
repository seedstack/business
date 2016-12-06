/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;


import org.seedstack.shed.exception.ErrorCode;

enum EventTestErrorCode implements ErrorCode {
    EVENT_WAS_HANDLER_BY,
    EVENT_WAS_NOT_HANDLER_BY,
    EVENT_WAS_NOT_EXACTLY_HANDLER_BY,
    FAILED_TO_INVOKE_METHOD,
    HANDLER_WAS_NOT_CALLED,
    HANDLER_WAS_NOT_CALLED_WITH_EXPECTED_EVENT,
}
