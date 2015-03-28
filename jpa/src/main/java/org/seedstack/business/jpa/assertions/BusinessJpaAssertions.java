/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.assertions;

import org.seedstack.seed.core.assertions.CoreObjectAssert;

/**
 * This class provides business assertions on JPA.
 *
 * @author epo.jemba@ext.mpsa.com
 */
public final class BusinessJpaAssertions {

    private BusinessJpaAssertions() {
    }

    /**
     * @param actual class to check
     * @return the actual Assert
     */
    public static BusinessJpaClassAssert assertThat(Class<?> actual) {
        return new BusinessJpaClassAssert(actual);
    }

    /**
     * Creates a new instance of <code>{@link CoreObjectAssert}</code>.
     *
     * @param actual the actual value.
     * @return the created assertion object.
     */
    public static CoreObjectAssert assertThat(Object actual) {
        return new BusinessJpaObjectAssert(actual);
    }
}
