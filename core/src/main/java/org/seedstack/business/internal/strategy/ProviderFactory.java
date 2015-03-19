/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.internal.strategy;

import com.google.inject.Provider;
import org.seedstack.business.internal.domain.repository.GenericImplementationProvider;

/**
 * This factory is used to create {@link GenericImplementationProvider}s with resolved generic. This should be used
 * when instantiating a {@link GenericBindingStrategy}.
 *
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public class ProviderFactory<T> {

    /**
     * Creates a new provider for the given default implementation and its resolved generics.
     *
     * @param defaultClassImpl       the default implementation class
     * @param genericResolvedClasses the resolved generics
     * @return the new provider
     */
	Provider<T> createProvider(Class<?> defaultClassImpl,  Class<?>... genericResolvedClasses) {
		return new GenericImplementationProvider<T>(defaultClassImpl, genericResolvedClasses);
	}

}
