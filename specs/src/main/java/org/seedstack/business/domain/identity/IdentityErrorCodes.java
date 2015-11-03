/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.domain.identity;


import org.seedstack.seed.ErrorCode;

/**
 * IdentityErrorCodes
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public enum IdentityErrorCodes implements ErrorCode {
	RESULT_OBJECT_SHOULD_NOT_BE_NULL,
	RESULT_OBJECT_DOES_NOT_INHERIT_FROM_ENTITY,
	ID_MUST_BE_NULL,
	ID_INJECTION_ERROR,
	BAD_IDENTITY_HANDLER_DEFINE_FOR_ENTITY_ID,
	QUALIFIER_FOR_IDENTITY_HANDLER_NOT_FOUND_FOR_ENTITY,
	NO_IDENTITY_HANDLER_DEFINE_FOR_ENTITY_ID,
	NO_SEQUENCE_NAME_FOUND_FOR_ENTITY,
	ID_CAST_EXCEPTION
}
