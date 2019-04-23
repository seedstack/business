/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.inmemory.test;

import org.seedstack.business.domain.BaseValueObject;

public class VehicleTypeId extends BaseValueObject {
    /**
     * The brand country code.
     */
    private String brandCountryCode;

    /**
     * The code.
     */
    private String code;

    /**
     * Instantiates a new vehicle type id.
     *
     * @param brandCountryCode the brand country code
     * @param code             the code
     */
    public VehicleTypeId(String brandCountryCode, String code) {
        this.brandCountryCode = brandCountryCode;
        this.code = code;
    }

    public String getBrandCountryCode() {
        return brandCountryCode;
    }

    public String getCode() {
        return code;
    }
}