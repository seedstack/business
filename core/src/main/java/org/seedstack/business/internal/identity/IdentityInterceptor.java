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
package org.seedstack.business.internal.identity;

import com.google.inject.Inject;
import org.seedstack.business.api.domain.Entity;
import org.seedstack.business.api.domain.identity.IdentityErrorCodes;
import org.seedstack.business.api.domain.identity.IdentityService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seedstack.seed.core.api.SeedException;

/**
 * Interceptor used for identity management when creating a new entity using a factory 
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
class IdentityInterceptor implements MethodInterceptor {

	@Inject
	private IdentityService identityService;

	public IdentityInterceptor() {
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = invocation.proceed();
		if (object == null) {
			throw SeedException.createNew(IdentityErrorCodes.RESULT_OBJECT_SHOULD_NOT_BE_NULL)
                    .put("factoryClass", invocation.getMethod().getDeclaringClass().getName());
		}
		if (!Entity.class.isAssignableFrom(object.getClass())) {
			throw SeedException.createNew(IdentityErrorCodes.RESULT_OBJECT_DOES_NOT_INHERIT_FROM_ENTITY)
					.put("factoryClass", invocation.getMethod().getDeclaringClass().getName())
					.put("objectClass", object.getClass().getName())
					.put("entityClass", Entity.class.getName());
		}
		Entity<?> entity = (Entity<?>) object;
		return identityService.identify(entity);
	}
}
