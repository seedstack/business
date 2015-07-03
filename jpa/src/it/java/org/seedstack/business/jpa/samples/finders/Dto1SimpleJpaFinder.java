/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.finders;

import org.seedstack.business.api.interfaces.finder.Range;
import org.seedstack.business.jpa.BaseSimpleJpaFinder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * criteria needed :
 * <li> param1 : string </li>
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class Dto1SimpleJpaFinder extends BaseSimpleJpaFinder<Dto1> {
	
	@Inject
	EntityManager entityManager;
	
	@Override
    @SuppressWarnings("unchecked")
	protected List<Dto1> computeResultList(Range range , Map<String,Object> criteria) {
		Query query = entityManager.createQuery("select new " + Dto1.class.getName() + "(  a.entityId, a.field1, a.field2 , a.field3 , a.field4  )  from SampleSimpleJpaAggregateRoot a where a.field2 = :param1");

		query.setFirstResult( (int)range.getOffset() );
		query.setMaxResults ( range.getSize() );
		
		updateQuery(query , criteria);
		
		return (List<Dto1>)query.getResultList();
	}

	@Override
	protected long computeFullRequestSize(Map<String,Object> criteria) {
		
		Query query = entityManager.createQuery("select count(*) from SampleSimpleJpaAggregateRoot a where a.field2 = :param1");
		updateQuery(query , criteria);
		return (Long) query.getSingleResult();
	}
}
