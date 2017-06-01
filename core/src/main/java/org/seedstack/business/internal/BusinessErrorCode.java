/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.seedstack.shed.exception.ErrorCode;

public enum BusinessErrorCode implements ErrorCode {
    AMBIGUOUS_CONSTRUCTOR_FOUND,
    AMBIGUOUS_METHOD_FOUND,
    CLASS_IS_NOT_AN_ANNOTATION,
    DOMAIN_OBJECT_CONSTRUCTOR_NOT_FOUND,
    ENTITY_ALREADY_HAS_AN_IDENTITY,
    ERROR_ACCESSING_FIELD,
    EVENT_CYCLE_DETECTED,
    EXCEPTION_OCCURRED_DURING_EVENT_HANDLER_INVOCATION,
    IDENTITY_TYPE_CANNOT_BE_GENERATED_BY_HANDLER,
    ILLEGAL_FACTORY,
    ILLEGAL_IDENTITY_HANDLER,
    ILLEGAL_POLICY,
    ILLEGAL_REPOSITORY,
    ILLEGAL_SERVICE,
    NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY,
    NO_IDENTITY_HANDLER_QUALIFIER_FOUND_ON_ENTITY,
    UNABLE_TO_FIND_ASSEMBLER,
    UNABLE_TO_FIND_ASSEMBLER_WITH_QUALIFIER,
    UNABLE_TO_FIND_FACTORY_METHOD,
    UNABLE_TO_INJECT_ENTITY_IDENTITY,
    UNABLE_TO_INVOKE_CONSTRUCTOR,
    UNABLE_TO_INVOKE_FACTORY_METHOD
}
