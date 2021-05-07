/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures;

import org.seedstack.business.assembler.MatchingFactoryParameter;

public class SomeDto {
    private String id;

    public SomeDto() {
    }

    public SomeDto(String id) {
        this.id = id;
    }

    @MatchingFactoryParameter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
