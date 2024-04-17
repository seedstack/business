/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.identity.embedded;

import org.seedstack.business.domain.BaseValueObject;
import org.seedstack.business.domain.Identity;

public class MyCompositeId extends BaseValueObject {
    @Identity(generator = AddOneGenerator.class)
    private Long seq;

    private String aTextInTheId;

    public MyCompositeId(String text){
        this.aTextInTheId=text;
    }

    public Long getSeq() {
        return seq;
    }

    public String getaTextInTheId() {
        return aTextInTheId;
    }
}
