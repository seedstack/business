/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;


import org.seedstack.seed.core.api.ErrorCode;

/**
 * Domain error codes.
 */
public enum DomainErrorCodes implements ErrorCode {
	
	/**
	 * This code is raised when an instantiation issue occurred.
	 * <p>
	 * Specifically inside Factory and Assemblers.
	 */
	AGGREGATEROOT_INSTANTIATION_ISSUE,
	
	/**
	 * This code is raised when a creation issue occurred.
	 * <p>
	 * Specifically inside Factory and Assemblers.
	 */
	AGGREGATEROOT_CREATION_ISSUE,
	
	/**
	 * This code is raised when an entity is used with no identity defined. 
	 * <p>
	 * Specifically inside Factory and Assemblers.
	 */
	ENTITY_WITHOUT_IDENTITY_ISSUE

}
