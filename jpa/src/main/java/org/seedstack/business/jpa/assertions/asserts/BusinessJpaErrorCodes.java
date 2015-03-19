/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.assertions.asserts;


import org.seedstack.seed.core.api.ErrorCode;

/**
 * @author epo.jemba@ext.mpsa.com
 */
public enum BusinessJpaErrorCodes implements ErrorCode {
    CLASS_MUST_IMPLEMENTS_A_REPOSITORY_INTERFACE, CLASS_MUST_IMPLEMENTS_A_FACTORY_INTERFACE,
    CLASS_MUST_IMPLEMENTS_A_DOMAIN_SERVICE_INTERFACE, CLASS_MUST_IMPLEMENTS_A_APPLICATION_SERVICE_INTERFACE,
    SPECIFICATION_WITH_FAULTY_JPA_JOIN_PATH, NAMEDSPECIFICATION_NOT_FOUND,
    NAMEDSPECIFICATION_UNRESOLVE_AGGREGATE_TYPE, REFERENCE_FOR_ASSOCIATION_NOT_FOUND,
    CLASS_MUST_IMPLEMENTS_A_DOMAIN_POLICY_INTERFACE, SPECIFICATION_SELECTION_CAN_NOT_BE_RESOLVE

}
