/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;

import java.util.List;

/**
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public interface AssembleDtosProvider {

    /**
     * Returns a list of dtos.
     *
     * @param dtoClass the dto class to assemble
     * @param <D> the dto type
     * @return the list of dtos
     */
    <D> List<D> to(Class<D> dtoClass);
}
