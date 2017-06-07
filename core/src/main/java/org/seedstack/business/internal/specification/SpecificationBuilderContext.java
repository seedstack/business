/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import com.google.common.base.Preconditions;
import org.seedstack.business.specification.DelegatingSpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.TrueSpecification;
import org.seedstack.business.specification.builder.BaseSelector;

import java.util.ArrayList;
import java.util.List;

class SpecificationBuilderContext<T, SELECTOR extends BaseSelector<T, SELECTOR>> {
    private final Class<T> targetClass;
    private final List<Specification<T>> disjunctions = new ArrayList<>();
    private SELECTOR selector;
    private Mode mode = Mode.DISJUNCTION;
    private String path;
    private boolean not;

    SpecificationBuilderContext(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    void addSpecification(Specification<T> specification) {
        Preconditions.checkNotNull(specification, "Specification cannot be null");
        if (mode == Mode.CONJUNCTION) {
            Preconditions.checkArgument(!disjunctions.isEmpty(), "Cannot add a conjunction without an existing disjunction");
            int index = disjunctions.size() - 1;
            disjunctions.set(index, disjunctions.get(index).and(specification));
            mode = Mode.NONE;
        } else if (mode == Mode.NEGATIVE_DISJUNCTION) {
            processNegativeDisjunction(true);
            disjunctions.add(specification);
            mode = Mode.NONE;
        } else if (mode == Mode.DISJUNCTION) {
            processNegativeDisjunction(false);
            disjunctions.add(specification);
            mode = Mode.NONE;
        } else {
            throw new IllegalStateException("Cannot add specification, invalid mode " + mode);
        }
    }

    private void processNegativeDisjunction(boolean newStatus) {
        if (not) {
            int index = disjunctions.size() - 1;
            disjunctions.set(index, disjunctions.get(index).not());
        }
        not = newStatus;
    }

    void setProperty(String path) {
        Preconditions.checkNotNull(path, "Property cannot be null");
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
        this.mode = mode;
    }

    public SELECTOR getSelector() {
        return selector;
    }

    public void setSelector(SELECTOR selector) {
        this.selector = selector;
    }

    Specification<T> build() {
        processNegativeDisjunction(false);
        if (disjunctions.isEmpty()) {
            return new ClassSpecification(new TrueSpecification<>());
        } else {
            return new ClassSpecification(disjunctions.stream().skip(1).reduce(disjunctions.get(0), Specification::or));
        }
    }

    private class ClassSpecification implements DelegatingSpecification<T> {
        private final Specification<T> delegate;

        private ClassSpecification(Specification<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Specification<T> getDelegate() {
            return delegate;
        }

        @Override
        public String toString() {
            return targetClass.getSimpleName() + "[" + String.valueOf(delegate) + "]";
        }
    }

    enum Mode {
        DISJUNCTION,
        NEGATIVE_DISJUNCTION,
        CONJUNCTION,
        NONE
    }
}
