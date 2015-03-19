/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.query.view.page;

import org.seedstack.business.api.interfaces.annotations.InterfacesService;

/**
 * This interfaces service give utilities methods around pages.
 * 
 * @author epo.jemba@ext.mpsa.com
 */
@InterfacesService
public interface PaginationService {

    /**
     * Get the last page.
     *
     * @param page the current page
     * @return the new page
     */
	Page getLastPage(Page page);

}
