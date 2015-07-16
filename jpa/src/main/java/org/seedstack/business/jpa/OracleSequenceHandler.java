/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa;

import org.apache.commons.configuration.Configuration;
import org.seedstack.business.api.domain.Entity;
import org.seedstack.business.api.domain.BaseEntity;
import org.seedstack.business.api.domain.identity.IdentityErrorCodes;
import org.seedstack.business.api.domain.identity.SequenceHandler;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.seedstack.seed.core.api.SeedException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Handles oracle sequence for identity management. This handler needs the oracle
 * sequence name property passed through props configuration using the full
 * entity class name as section and <b>identity.sequence-name</b> as key
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
@Named("oracle-sequence")
public class OracleSequenceHandler implements SequenceHandler<BaseEntity<Long>, Long> {

	@Inject
	private EntityManager entityManager;

	private static final String SEQUENCE_NAME = "identity.sequence-name";

	@Override
	public Long handle(Entity entity,
			Configuration entityConfiguration) {
		String sequence = entityConfiguration.getString(SEQUENCE_NAME);
		if (StringUtils.isBlank(sequence)) {
			SeedException.createNew(IdentityErrorCodes.NO_SEQUENCE_NAME_FOUND_FOR_ENTITY)
					.put("entityClass", entity.getClass()).thenThrows();
		}
		Object id =  entityManager.createNativeQuery("SELECT " + sequence + ".NEXTVAL FROM DUAL").getSingleResult();
		
		Long convertedId;
		try {			
			LongConverter converter = new LongConverter();
			convertedId = (Long) converter.convert(Long.class, id);
		} catch (ConversionException e) {
			throw SeedException.wrap(e, IdentityErrorCodes.ID_CAST_EXCEPTION).put("object", id)
					.put("objectType", id.getClass()).put("entity", entity.getClass().getSimpleName());
		}
		
		return convertedId;
	}

}
