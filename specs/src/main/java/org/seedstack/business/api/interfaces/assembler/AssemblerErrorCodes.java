/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;


import org.seedstack.seed.core.api.ErrorCode;

/**
 * Assembler error codes.
 */
public enum AssemblerErrorCodes implements ErrorCode {
	INSTANCE_CREATION_ISSUE,
	METHOD_INVOCATION_ISSUE,
	FACTORY_NOT_FOUND_FOR_AGGREGATE,
	REPOSITORY_NOT_FOUND_FOR_AGGREGATE,
	ASSEMBLER_NOT_FOUND,
	REPRESENTATION_IS_NOT_VALID,
	MORE_THAN_ONE_ASSEMBLER_FOUND
}
