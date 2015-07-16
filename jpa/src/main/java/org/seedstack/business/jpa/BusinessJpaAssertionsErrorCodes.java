/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa;


import org.seedstack.seed.core.api.ErrorCode;

/**
 * @author epo.jemba@ext.mpsa.com
 */
public enum BusinessJpaAssertionsErrorCodes implements ErrorCode {
    CLASS_MUST_IMPLEMENT_A_REPOSITORY_INTERFACE,
    CLASS_MUST_IMPLEMENT_A_FACTORY_INTERFACE,
    CLASS_MUST_IMPLEMENT_A_DOMAIN_SERVICE_INTERFACE,
    CLASS_MUST_IMPLEMENT_A_APPLICATION_SERVICE_INTERFACE,
    CLASS_MUST_IMPLEMENT_A_DOMAIN_POLICY_INTERFACE
}
