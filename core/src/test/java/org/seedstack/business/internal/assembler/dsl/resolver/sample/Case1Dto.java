/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver.sample;

import org.seedstack.business.assembler.MatchingEntityId;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class Case1Dto {

    int id;

    String name;

    public Case1Dto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @MatchingEntityId
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
