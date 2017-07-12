/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.spi.assembler;

public class DtoInfoResolverPriority {
    /**
     * Matching annotations DTO info resolver uses {@link org.seedstack.business.assembler.MatchingEntityId} and
     * {@link org.seedstack.business.assembler.MatchingFactoryParameter} annotations to resolve DTO information. It is
     * able to use factories for {@link org.seedstack.business.domain.ValueObject}s and {@link org.seedstack.business.domain.AggregateRoot}s
     * creation.
     */
    public static final int MATCHING_ANNOTATIONS = 0;

    /**
     * Fallback DTO info resolver is only capable of instantiating a default value of identifiers and aggregates by
     * calling their factory without parameter or their default constructor.
     */
    public static final int FALLBACK = Integer.MIN_VALUE;
}
