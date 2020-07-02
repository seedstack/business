/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.data;

import java.util.stream.Stream;
import org.seedstack.business.data.BaseDataExporter;

public class SomeCustomDtoExporter extends BaseDataExporter<SomeCustomDto> {
    @Override
    public Stream<SomeCustomDto> exportData() {
        SomeCustomDto someCustomDto = new SomeCustomDto();
        someCustomDto.setId("bstark");
        someCustomDto.setFirstName("Brandon");
        someCustomDto.setLastName("STARK");
        someCustomDto.setAge(48);
        return Stream.of(someCustomDto);
    }
}
