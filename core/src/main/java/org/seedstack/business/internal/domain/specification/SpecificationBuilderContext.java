/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import com.google.common.base.Preconditions;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.domain.specification.TrueSpecification;

import java.util.ArrayList;
import java.util.List;

class SpecificationBuilderContext<A extends AggregateRoot<?>> {
    private final Class<A> aggregateClass;
    private final List<Specification<A>> disjunctions = new ArrayList<>();
    private Mode mode = Mode.DISJUNCTION;
    private String path;
    private boolean not;

    SpecificationBuilderContext(Class<A> aggregateClass) {
        this.aggregateClass = aggregateClass;
    }

    void addSpecification(Specification<A> specification) {
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

    Specification<A> build() {
        processNegativeDisjunction(false);
        if (disjunctions.isEmpty()) {
            return new AggregateSpecification<>(aggregateClass, new TrueSpecification<>());
        } else {
            return new AggregateSpecification<>(aggregateClass, disjunctions.stream().skip(1).reduce(disjunctions.get(0), Specification::or));
        }
    }

    enum Mode {
        DISJUNCTION,
        NEGATIVE_DISJUNCTION,
        CONJUNCTION,
        NONE
    }
}
