/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Deprecated
@SuppressFBWarnings(value = "NM_SAME_SIMPLE_NAME_AS_INTERFACE", justification = "On purpose to facilitate migration "
        + "from 3.x")
public interface FluentAssembler extends org.seedstack.business.assembler.dsl.FluentAssembler {
}
