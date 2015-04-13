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

/**
 * A part of the assembler dsl allowing to choose whether the data security feature will be applied on the DSL.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public interface AssembleSecurely extends Assemble {

    /**
     * Enable the data security feature on the DSL.
     *
     * @return an Assemble
     */
    Assemble securely();
}
