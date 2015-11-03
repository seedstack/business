/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.reporting;

import org.seedstack.business.domain.BaseFactory;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class ReportFactoryBase  extends BaseFactory<Report>  implements ReportFactory {

	@Override
	public Report createNewActivation(String id, String description) {
		
		Report report = new Report(id);
		
		report.setDescription(description);
		
		return report;
	}

}
