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


import org.seedstack.seed.core.api.Logging;
import org.slf4j.Logger;

/**
 * This assembler is intended to be extended by the base assemblers not directly by the users.
 *
 * @param <AGGREGATE_ROOT>      the aggregate root
 * @param <DTO>                 the dto type
 * @author epo.jemba@ext.mpsa.com
 * @see BaseAssembler
 * @see BaseTupleAssembler
 */
public abstract class AbstractBaseAssembler<AGGREGATE_ROOT, DTO>
        implements Assembler<AGGREGATE_ROOT, DTO> {

	@Logging
	private Logger logger;

	protected Class<DTO> dtoClass;

	@Override
	public Class<DTO> getDtoClass() {
		return this.dtoClass;
	}

	/**
	 * This protected method is in charge of creating a new instance of the DTO.
	 * <p>
	 * The actual implementation is fine for simple POJO, but it can be
	 * extended. The developers will then use {@link #getDtoClass()} to retrieve
	 * the destination class.
	 *
	 * @return the DTO instance.
	 */
	protected DTO newDto() {
		DTO newInstance;
		try {
			newInstance = dtoClass.newInstance();
		} catch (Exception e) {
			logger.error("Error when creating new instance of " + dtoClass.getName(), e);
            throw new IllegalStateException(e);
		}

		return newInstance;
	}

}
