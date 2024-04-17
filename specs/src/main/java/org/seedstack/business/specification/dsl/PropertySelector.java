/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

/**
 * An element of the {@link SpecificationBuilder} DSL to select a property of the object a
 * specification will apply to.
 *
 * @param <T> the type of the object the specification applies to.
 * @param <S> the type of the selector.
 */
public interface PropertySelector<T, S extends BaseSelector<T, S>> extends BaseSelector<T, S> {

    /**
     * Selects a property of the object to be the subject of a specification. For instance, if an
     * equality specification is chosen, the equality will only apply on the chosen property.
     *
     * <p> Property path support the "dot" syntax to specify a property in a nested object. For
     * instance, a "team.leader.name" property path can be specified, meaning that the attribute
     * "name" of the attribute "leader" of the attribute "team" will be selected. </p>
     *
     * <p> {@link java.util.Collection}, {@link java.util.Map} or array properties are supported, with
     * the semantics that at least one element must satisfy the specification. </p>
     *
     * @param path the path of the the property to select.
     * @return the next operation of the builder DSL, allowing to choose the specification.
     */
    SpecificationPicker<T, S> property(String path);
}
