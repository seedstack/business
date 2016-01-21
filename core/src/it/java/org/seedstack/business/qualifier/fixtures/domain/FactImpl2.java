/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.qualifier.fixtures.domain;

import javax.inject.Named;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@Named("2")
public class FactImpl2 implements MyFactory {
    @Override
    public Class<MyDomainPolicy> getProducedClass() {
        return null;
    }
}
