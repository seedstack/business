/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;
import org.seedstack.business.specification.AndSpecification;
import org.seedstack.business.specification.OrSpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.SubstitutableSpecification;
import org.seedstack.business.specification.dsl.BaseSelector;

class SpecificationBuilderContext<T, S extends BaseSelector<T, S>> {

    private final Class<T> targetClass;
    private final List<List<Specification<T>>> disjunction = new ArrayList<>();
    private S selector;
    private Mode mode = Mode.DISJUNCTION;
    private String path;

    SpecificationBuilderContext(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    void addSpecification(Specification<T> specification) {
        checkNotNull(specification, "Specification cannot be null");
        if (mode == Mode.CONJUNCTION) {
            checkArgument(!disjunction.isEmpty(), "Cannot add a conjunction without an existing disjunction");
            List<Specification<T>> lastConjunction = this.disjunction.get(this.disjunction.size() - 1);
            lastConjunction.add(specification);
            mode = Mode.NONE;
        } else if (mode == Mode.DISJUNCTION) {
            List<Specification<T>> newConjunction = new ArrayList<>();
            newConjunction.add(specification);
            disjunction.add(newConjunction);
            mode = Mode.NONE;
        } else {
            throw new IllegalStateException("Cannot add specification, invalid mode " + mode);
        }
    }

    void setProperty(String path) {
        checkNotNull(path, "Property cannot be null");
        if (this.path != null) {
            throw new IllegalStateException("A property is already set");
        }
        this.path = path;
    }

    boolean hasProperty() {
        return this.path != null;
    }

    String pickProperty() {
        if (this.path == null) {
            throw new IllegalStateException("No property has been set");
        }
        String ret = this.path;
        this.path = null;
        return ret;
    }

    void setMode(Mode mode) {
        checkState(this.mode == Mode.NONE, "Cannot change specification mode, it is already set");
        this.mode = mode;
    }

    public S getSelector() {
        return selector;
    }

    public void setSelector(S selector) {
        this.selector = selector;
    }

    Specification<T> build() {
        return new ClassSpecification<>(targetClass, buildOrSpecification());
    }

    private Specification<T> buildOrSpecification() {
        checkState(!disjunction.isEmpty(), "Illegal empty specification");
        if (disjunction.size() == 1) {
            return buildAndSpecification(disjunction.get(0));
        } else {
            return new OrSpecification<>(disjunction.stream()
                    .map(this::buildAndSpecification)
                    .toArray(this::createSpecificationArray));
        }
    }

    private Specification<T> buildAndSpecification(List<Specification<T>> conjunction) {
        checkState(!conjunction.isEmpty(), "Illegal empty conjunction");
        if (conjunction.size() == 1) {
            return conjunction.get(0);
        } else {
            return new AndSpecification<>(conjunction.toArray(createSpecificationArray(conjunction.size())));
        }
    }

    @SuppressWarnings("unchecked")
    Specification<T>[] createSpecificationArray(int size) {
        return new Specification[size];
    }

    enum Mode {
        DISJUNCTION, CONJUNCTION, NONE
    }

    private static class ClassSpecification<C> implements SubstitutableSpecification<C> {

        private final Class<C> targetClass;
        private final Specification<C> delegate;

        private ClassSpecification(Class<C> targetClass, Specification<C> delegate) {
            this.targetClass = targetClass;
            this.delegate = delegate;
        }

        @Override
        public Specification<C> getSubstitute() {
            return delegate;
        }

        @Override
        public String toString() {
            return targetClass.getSimpleName() + "[" + String.valueOf(delegate) + "]";
        }
    }
}
