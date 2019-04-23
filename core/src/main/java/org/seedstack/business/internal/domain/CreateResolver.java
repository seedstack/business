/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import java.lang.reflect.Method;
import org.seedstack.business.domain.Create;
import org.seedstack.shed.reflect.StandardAnnotationResolver;

class CreateResolver extends StandardAnnotationResolver<Method, Create> {

    static CreateResolver INSTANCE = new CreateResolver();

    private CreateResolver() {
        // no external instantiation allowed
    }
}
