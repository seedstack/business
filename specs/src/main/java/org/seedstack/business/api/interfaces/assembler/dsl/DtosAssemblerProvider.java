/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;

import java.util.List;

/**
* @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
*/
public interface DtosAssemblerProvider {

    <D> List<D> to(Class<D> dtoClass);
}
