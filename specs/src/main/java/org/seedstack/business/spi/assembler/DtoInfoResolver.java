/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.spi.assembler;


import org.seedstack.business.domain.AggregateRoot;

public interface DtoInfoResolver {

    <D> boolean supports(D dto);

    <D, ID> ID resolveId(D dto, Class<ID> aggregateIdClass);

    <D, ID> ID resolveId(D dto, Class<ID> aggregateIdClass, int position);

    <D, A extends AggregateRoot<ID>, ID> A resolveAggregate(D dto, Class<A> aggregateRootClass);

    <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass, int position);

}
