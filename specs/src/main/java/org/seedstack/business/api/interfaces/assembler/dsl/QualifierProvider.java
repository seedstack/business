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

import org.seedstack.business.api.interfaces.assembler.AssemblerTypes;

import java.lang.annotation.Annotation;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface QualifierProvider<T> {

    T with(Annotation qualifier);

    T with(Class<? extends Annotation> qualifier);

    T with(AssemblerTypes qualifier);
}
