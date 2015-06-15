/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.strategy.api;

import com.google.inject.Binder;

/**
 * The BindingStrategy interface deports strategies to resolve bindings.
 * 
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public interface BindingStrategy {

	/**
	 * Resolves the bindings for the given strategy using the current module binder.
	 * 
	 * @param binder the current Binder
	 */
	void resolve(Binder binder);
}
