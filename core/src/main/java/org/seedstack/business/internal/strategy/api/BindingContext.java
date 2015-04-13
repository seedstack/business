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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import java.util.Collection;

/**
 * BindingContext will hold information on key during the computation of binding definitions.
 * 
 * @author epo.jemba@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public class BindingContext {

    private Multimap<TypeLiteral<?>, Key<?>> excludedKeysByType;
    private Multimap<Class<?>, Key<?>> boundKeysByClass;

	/**
	 * Constructor.
	 */
	public BindingContext() {
        excludedKeysByType = ArrayListMultimap.create();
        boundKeysByClass = ArrayListMultimap.create();
    }
	
	/**
	 * Indicates that a key is bound.
	 * 
	 * @param key the bound key
     * @return itself
	 */
	public BindingContext bound(Key<?> key) {
        boundKeysByClass.put(key.getTypeLiteral().getRawType(), key);
        return this;
	}

    /**
     * Indicates that a collection of key was bound.
     *
     * @param keys the bound keys
     * @return itself
     */
    public BindingContext bound(Collection<Key<?>> keys) {
        for (Key<?> key : keys) {
            boundKeysByClass.put(key.getTypeLiteral().getRawType(), key);
        }
        return this;
    }

    /**
     * Indicates that a collection of keys was already bound and should not be bound again, even with different qualifiers.
     *
     * @param keys the collection of key to add
     */
    public BindingContext excluded(Collection<Key<?>> keys) {
        for (Key<?> key : keys) {
            excludedKeysByType.put(key.getTypeLiteral(), key);
        }
        return this;
    }

    /**
     * Indicates that a key was already bound and should not be bound again, even with different qualifiers.
     *
     * @param key the key to add
     */
    public BindingContext excluded(Key<?> key) {
        excludedKeysByType.put(key.getTypeLiteral(), key);
        return this;
    }

	/**
	 * Checks if a typeLiteral was already bind and should not be bound again, even with different qualifier.
	 * 
	 * @param type the type to check
	 * @return true if the typeLiteral can't be bound, false otherwise
	 */
	public boolean isExcluded(TypeLiteral<?> type) {
		return excludedKeysByType.containsKey(type);
	}

    /**
     * Gets the collection of key bound for a given injectee class.
     *
     * @param clazz the requested class
     * @return the collection of key
     */
    public Collection<Key<?>> boundForClass(Class<?> clazz) {
        return boundKeysByClass.get(clazz);
    }
}
