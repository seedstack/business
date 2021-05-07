/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.seedstack.shed.exception.BaseException;
import org.seedstack.shed.exception.ErrorCode;

/**
 * This is the base class for all business framework exceptions.
 */
public class BusinessException extends BaseException {

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Create a new BusinessException from an {@link ErrorCode}.
     *
     * @param errorCode the error code to set.
     * @return the created BusinessException.
     */
    public static BusinessException createNew(ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }

    /**
     * Wrap a BusinessException with an {@link ErrorCode} around an existing {@link Throwable}.
     *
     * @param throwable the existing throwable to wrap.
     * @param errorCode the error code to set.
     * @return the created BusinessException.
     */
    public static BusinessException wrap(Throwable throwable, ErrorCode errorCode) {
        return new BusinessException(errorCode, throwable);
    }
}
