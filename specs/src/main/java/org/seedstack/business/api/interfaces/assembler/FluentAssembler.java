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

import org.seedstack.business.api.interfaces.assembler.dsl.AssembleSecurely;

/**
 * FluentAssembler provides the entry point for an assembler DSL.
 * <p>
 * It allows to programmatically assemble aggregate roots into DTOs with additional features
 * like automatically retrieving the aggregate from its repository. Or automatically creating
 * it from its factory.
 * </p>
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public interface FluentAssembler {

    /**
     * DSL entry point.
     *
     * @return an Assemble class
     */
    AssembleSecurely assemble();
}
