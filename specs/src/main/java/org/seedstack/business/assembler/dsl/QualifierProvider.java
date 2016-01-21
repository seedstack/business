/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.assembler.AssemblerTypes;

import java.lang.annotation.Annotation;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface QualifierProvider<T> {

    T with(Annotation qualifier);

    T with(Class<? extends Annotation> qualifier);

    T with(AssemblerTypes qualifier);
}
