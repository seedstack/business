/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import java.lang.annotation.Annotation;

/**
 * An element of the {@link FluentAssembler DSL} allowing to merge multiple DTO into aggregates or
 * tuples of aggregates or specify the qualifier of the assembler to use.
 **/
public interface MergeMultipleWithQualifier extends MergeMultiple {

    /**
     * Allows to specify the qualifier of the assembler to use.
     *
     * @param qualifier the qualifier annotation.
     * @return the next element of the DSL.
     */
    MergeMultiple with(Annotation qualifier);

    /**
     * Allows to specify the qualifier of the assembler to use with Named annotation.
     *
     * @param qualifier the value of javax.inject.Named annotation.
     * @return the next element of the DSL.
     */
    MergeMultiple with(String qualifier);

    /**
     * Allows to specify the qualifier class of the assembler to use.
     *
     * @param qualifier the qualifier annotation class.
     * @return the next element of the DSL.
     */
    MergeMultiple with(Class<? extends Annotation> qualifier);
}
