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

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Objects;
import org.seedstack.seed.core.assertions.CoreClassAssert;


/**
 * This class provides business assertions.
 *
 * @author epo.jemba@ext.mpsa.com
 * @param <S> parent class assert.
 */
public class BusinessClassAssert<S extends BusinessClassAssert<S>> extends CoreClassAssert<S> {

	/**
	 * @param actual the class to test
	 */
	public BusinessClassAssert(Class<?> actual) {
		super(actual,BusinessClassAssert.class);
	}

	/**
     * Checks if the class is a valid aggregate root.
     *
	 * @return itself
	 */
	public S isValidAggregateRootClass() {
		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertAggregateRootClassIsValid(actual);

		return myself;
	}

    /**
     * Checks if the class is a valid value object.
	 *
     * @return itself
     */
	public S isValidValueObjectClass() {
		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertValueObjectClassIsValid(actual);

		return myself;
	}

    /**
     * Checks if the class is a valid entity.
	 *
     * @return itself
     */
	public S isValidEntityClass() {

		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertEntityClassIsValid(actual);

		return myself;
	}

    /**
     * Checks if the class is a valid repository.
	 *
     * @return itself
     */
	public S isValidRepositoryInterfaceClass() {
		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertRepositoryInterfaceClassIsValid(actual);

		return myself;
	}

    /**
     * Checks if the class is a valid factory interface.
	 *
     * @return itself
     */
	public S isValidFactoryInterfaceClass() {
		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertFactoryInterfaceClassIsValid(actual);

		return myself;
	}

    /**
     * Checks if the class is a valid domain service interface.
	 *
     * @return itself
     */
	public S isValidDomainServiceInterfaceClass() {

		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertDomainServiceInterfaceClassIsValid(actual);

		return myself;
	}

    /**
     * Checks if the class is a valid application service interface.
	 *
     * @return itself
     */
	public S isValidApplicationServiceInterfaceClass() {

		assertNotNull(info, actual);

		BusinessReflectionAsserts.assertApplicationServiceInterfaceClassIsValid(actual);

		return myself;
	}

	private static void assertNotNull(AssertionInfo info, Class<?> actual) {
		Objects.instance().assertNotNull(info, actual);
	}
}