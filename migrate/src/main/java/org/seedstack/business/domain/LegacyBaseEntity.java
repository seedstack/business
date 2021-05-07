/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

@Deprecated
public abstract class LegacyBaseEntity<I> extends BaseEntity<I> implements LegacyEntity<I> {
    @Override
    public I getId() {
        // Removes the automatic detection of identity
        return getEntityId();
    }
}
