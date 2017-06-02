/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.StringSpecification;

class StringValueOptionsImpl implements StringSpecification.Options {
    private boolean leftTrimmed;
    private boolean rightTrimmed;
    private boolean trimmed;
    private boolean ignoringCase;

    @Override
    public boolean isLeftTrimmed() {
        return leftTrimmed;
    }

    void setLeftTrimmed(boolean leftTrimmed) {
        this.leftTrimmed = leftTrimmed;
    }

    @Override
    public boolean isRightTrimmed() {
        return rightTrimmed;
    }

    void setRightTrimmed(boolean rightTrimmed) {
        this.rightTrimmed = rightTrimmed;
    }

    @Override
    public boolean isTrimmed() {
        return trimmed;
    }

    void setTrimmed(boolean trimmed) {
        this.trimmed = trimmed;
    }

    @Override
    public boolean isIgnoringCase() {
        return ignoringCase;
    }

    void setIgnoringCase(boolean ignoringCase) {
        this.ignoringCase = ignoringCase;
    }
}
