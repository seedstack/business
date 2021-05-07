/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.identity.embedded;

import org.seedstack.business.domain.BaseAggregateRoot;

public class MyAggregateWithCompositeId extends BaseAggregateRoot<MyCompositeId> {

    private String description;

    private MyCompositeId identitier;


    public MyAggregateWithCompositeId(MyCompositeId identitier, String description){
        this.identitier=identitier;
        this.description=description;
    }
    @Override
    public MyCompositeId getId() {
        return this.identitier;
    }

    public String getDescription() {
        return description;
    }
}
