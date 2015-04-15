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

import org.javatuples.Pair;
import org.seedstack.business.fixtures.domain.discount.Discount;
import org.seedstack.business.fixtures.domain.export.Export;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class BaseTupleAssemblerTest {

	static class Representation {
		String discount;
		String export;
		
		public Representation() {
		}

		public String getDiscount() {
			return discount;
		}

		public void setDiscount(String discount) {
			this.discount = discount;
		}

		public String getExport() {
			return export;
		}

		public void setExport(String export) {
			this.export = export;
		}
	}
	
	
	static class TestAssembler extends BaseTupleAssembler<Pair<Discount,Export>, Representation> {

		@Override
		protected void doAssembleDtoFromAggregate(Representation targetDto, Pair<Discount, Export> sourceEntity) {
			targetDto.setDiscount(sourceEntity.getValue0().getDescription());
			targetDto.setExport(sourceEntity.getValue1().getDescription());
		}

		@Override
		protected void doMergeAggregateWithDto(Pair<Discount, Export> targetEntity, Representation sourceDto) {
			targetEntity.getValue0().setDescription(sourceDto.getDiscount());
			targetEntity.getValue1().setDescription(sourceDto.getExport());
		}
		
	}

}
