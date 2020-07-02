/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.modelmapper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.seedstack.business.domain.AggregateRoot;

@Deprecated
@SuppressFBWarnings(value = "NM_SAME_SIMPLE_NAME_AS_SUPERCLASS", justification = "On purpose to facilitate migration "
        + "from 3.x")
public abstract class ModelMapperAssembler<A extends AggregateRoot<?>, D>
        extends org.seedstack.business.modelmapper.ModelMapperAssembler<A, D> {
}
