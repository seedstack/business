/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assertions;


import org.seedstack.seed.core.api.ErrorCode;

/**
 * @author epo.jemba@ext.mpsa.com
 */
public enum BusinessAssertionsErrorCodes implements ErrorCode {
	CLASS_CONSTRUCTORS_MUST_NOT_BE_PUBLIC,
	CLASS_MUST_NOT_BE_ABSTRACT,
	CLASS_OR_PARENT_MUST_BE_ANNOTATED_WITH,
	CLASS_MUST_EXTENDS ,
	CLASS_MUST_BE_INTERFACE,
	CLASS_MUST_HAVE_ONLY_PACKAGED_VIEW_SETTERS,
}
