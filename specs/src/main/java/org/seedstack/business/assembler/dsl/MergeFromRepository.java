/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.FactoryArgument;

import java.lang.annotation.Annotation;

/**
 * An element of the {@link FluentAssembler DSL} allowing to specify whether the aggregates should
 * be retrieved from a repository or created from a factory.
 **/
public interface MergeFromRepository<T> {

    /**
     * Loads the aggregates from their repository. <p> It uses the {@link AggregateId} annotation on
     * the DTO to find the aggregate IDs. </p>
     *
     * @return the next element of the DSL.
     */
    MergeFromRepositoryOrFactory<T> fromRepository();

    /**
     * Loads the aggregates from their repository, allowing to specify the qualifier of the repository to use.
     * <p> It uses the {@link AggregateId} annotation on the DTO to find the aggregate IDs. </p>
     *
     * @param qualifier the qualifier annotation.
     * @return the next element of the DSL.
     */
    MergeFromRepositoryOrFactory<T> fromRepository(Annotation qualifier);

    /**
     * Loads the aggregates from their repository, allowing to specify the qualifier of the repository to use.
     * <p> It uses the {@link AggregateId} annotation on the DTO to find the aggregate IDs. </p>
     *
     * @param qualifier the string qualifier, interpreted as a {@code @Named} annotation with the corresponding value.
     * @return the next element of the DSL.
     */
    MergeFromRepositoryOrFactory<T> fromRepository(String qualifier);

    /**
     * Loads the aggregates from their repository, allowing to specify the qualifier of the repository to use.
     * <p> It uses the {@link AggregateId} annotation on the DTO to find the aggregate IDs. </p>
     *
     * @param qualifier the qualifier annotation class.
     * @return the next element of the DSL.
     */
    MergeFromRepositoryOrFactory<T> fromRepository(Class<? extends Annotation> qualifier);

    /**
     * Create the aggregates from their factory. <p> It uses the {@link FactoryArgument} annotation on
     * the DTO to find the factory method parameters. </p>
     *
     * @return the next element of the DSL.
     */
    T fromFactory();

    /**
     * Create the aggregates from their factory, allowing to specify the qualifier of the factory to use.
     * <p> It uses the {@link FactoryArgument} annotation on the DTO to find the factory method parameters. </p>
     *
     * @param qualifier the qualifier annotation.
     * @return the next element of the DSL.
     */
    T fromFactory(Annotation qualifier);

    /**
     * Create the aggregates from their factory, allowing to specify the qualifier of the factory to use.
     * <p> It uses the {@link FactoryArgument} annotation on the DTO to find the factory method parameters. </p>
     *
     * @param qualifier the string qualifier, interpreted as a {@code @Named} annotation with the corresponding value.
     * @return the next element of the DSL.
     */
    T fromFactory(String qualifier);

    /**
     * Create the aggregates from their factory, allowing to specify the qualifier of the factory to use.
     * <p> It uses the {@link FactoryArgument} annotation on the DTO to find the factory method parameters. </p>
     *
     * @param qualifier the qualifier annotation class.
     * @return the next element of the DSL.
     */
    T fromFactory(Class<? extends Annotation> qualifier);
}
