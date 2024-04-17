/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import java.lang.annotation.Annotation;

/**
 * An element of the {@link FluentAssembler} DSL allowing to assemble to a {@link
 * org.seedstack.business.pagination.Page} of multiple DTO or specify the qualifier of the assembler
 * to use. This is only available in the DSL if a page of aggregates has been specified as input.
 */
public interface AssemblePageWithQualifier extends AssemblePage {

    /**
     * Allows to specify the qualifier of the assembler to use.
     *
     * @param qualifier the qualifier annotation.
     * @return the next element of the DSL.
     */
    AssemblePage with(Annotation qualifier);

    /**
     * Allows to specify the qualifier class of the assembler to use.
     *
     * @param qualifier the qualifier annotation class.
     * @return the next element of the DSL.
     */
    AssemblePage with(Class<? extends Annotation> qualifier);
}
