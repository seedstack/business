/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.pagination.dsl;

import org.seedstack.business.pagination.Slice;
import org.seedstack.business.specification.Specification;

/**
 * An element of the {@link Paginator} DSL allowing to specify the specification that will be used
 * to retrieve objects from the repository.
 *
 * @param <S> the type of the slice.
 * @param <T> the type of the paginated object.
 */
public interface SpecificationPicker<S extends Slice<T>, T> {

    /**
     * Restricts objects coming from the repository to ones matching the specification. This is a
     * terminal operation of the paginator DSL.
     *
     * @param spec the specification that objects must match.
     * @return the {@link Slice} or {@link org.seedstack.business.pagination.Page}.
     */
    S matching(Specification<T> spec);

    /**
     * Do not restrict objects coming from the repository. This is a terminal operation of the
     * paginator DSL.
     *
     * @return the {@link Slice} or {@link org.seedstack.business.pagination.Page}.
     */
    S all();
}
