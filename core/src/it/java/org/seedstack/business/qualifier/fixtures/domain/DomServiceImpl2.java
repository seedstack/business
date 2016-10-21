/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.qualifier.fixtures.domain;

import javax.inject.Named;


@Named("2")
public class DomServiceImpl2 extends MyAbstractDomainService implements MyDomainService {

    String message;

    public DomServiceImpl2() {
    }


    public DomServiceImpl2(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
