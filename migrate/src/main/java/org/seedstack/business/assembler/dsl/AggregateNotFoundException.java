/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Deprecated
@SuppressFBWarnings(value = "NM_SAME_SIMPLE_NAME_AS_SUPERCLASS", justification = "On purpose to facilitate migration "
        + "from 3.x")
public class AggregateNotFoundException extends org.seedstack.business.domain.AggregateNotFoundException {
}
