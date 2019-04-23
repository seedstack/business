/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.inject.Singleton;
import org.seedstack.business.data.BaseDataImporter;

@Singleton
public class SomeCustomDtoImporter extends BaseDataImporter<SomeCustomDto> {
    private List<SomeAggregate> someAggregates = new ArrayList<>();

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void clear() {
        someAggregates.clear();
    }

    @Override
    public void importData(Stream<SomeCustomDto> data) {
        data.forEach(dto -> {
            SomeAggregate someAggregate = new SomeAggregate(dto.getId());
            someAggregate.setFirstName(dto.getFirstName());
            someAggregate.setLastName(dto.getLastName());
            someAggregate.setAge(dto.getAge());
            someAggregates.add(someAggregate);
        });
    }

    public List<SomeAggregate> getSomeAggregates() {
        return someAggregates;
    }
}
