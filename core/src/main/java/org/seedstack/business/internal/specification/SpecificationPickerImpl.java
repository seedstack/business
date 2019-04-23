/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.AttributeSpecification;
import org.seedstack.business.specification.EqualSpecification;
import org.seedstack.business.specification.GreaterThanSpecification;
import org.seedstack.business.specification.LessThanSpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.StringEqualSpecification;
import org.seedstack.business.specification.StringMatchingSpecification;
import org.seedstack.business.specification.StringSpecification;
import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.OperatorPicker;
import org.seedstack.business.specification.dsl.SpecificationPicker;
import org.seedstack.business.specification.dsl.StringOptionPicker;

class SpecificationPickerImpl<T, S extends BaseSelector<T, S>> implements SpecificationPicker<T, S> {

    private final SpecificationBuilderContext<T, S> context;
    private boolean not;

    SpecificationPickerImpl(SpecificationBuilderContext<T, S> context) {
        this.context = context;
    }

    @Override
    public SpecificationPicker<T, S> not() {
        not = !not;
        return this;
    }

    @Override
    public OperatorPicker<T, S> satisfying(Specification<T> specification) {
        context.addSpecification(processSpecification(specification));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public StringOptionPicker<T, S> matching(String pattern) {
        StringSpecification.Options stringValueOptions = new StringSpecification.Options();
        context.addSpecification(processSpecification(new StringMatchingSpecification(pattern, stringValueOptions)));
        return new StringOptionPickerImpl<>(context, stringValueOptions);
    }

    @Override
    public StringOptionPicker<T, S> equalTo(String value) {
        StringSpecification.Options stringValueOptions = new StringSpecification.Options();
        context.addSpecification(processSpecification(new StringEqualSpecification(value, stringValueOptions)));
        return new StringOptionPickerImpl<>(context, stringValueOptions);
    }

    @Override
    public <V> OperatorPicker<T, S> equalTo(V value) {
        context.addSpecification(processSpecification(new EqualSpecification<>(value)));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, S> greaterThan(V value) {
        context.addSpecification(processSpecification(new GreaterThanSpecification<>(value)));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, S> greaterThanOrEqualTo(V value) {
        context.addSpecification(
                processSpecification(new EqualSpecification<>(value).or(new GreaterThanSpecification<>(value))));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, S> lessThan(V value) {
        context.addSpecification(processSpecification(new LessThanSpecification<>(value)));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, S> lessThanOrEqualTo(V value) {
        context.addSpecification(
                processSpecification(new EqualSpecification<>(value).or(new LessThanSpecification<>(value))));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, S> between(V leftValue, V rightValue) {
        return between(leftValue, rightValue, false, false);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, S> between(V leftValue, V rightValue,
            boolean leftInclusive, boolean rightInclusive) {
        Specification<V> gt = new GreaterThanSpecification<>(leftValue);
        if (leftInclusive) {
            gt = gt.or(new EqualSpecification<>(leftValue));
        }
        Specification<V> lt = new LessThanSpecification<>(rightValue);
        if (rightInclusive) {
            lt = lt.or(new EqualSpecification<>(rightValue));
        }
        context.addSpecification(processSpecification(gt.and(lt)));
        return new OperatorPickerImpl<>(context);
    }

    @SuppressWarnings("unchecked")
    private Specification<T> processSpecification(Specification<?> specification) {
        Specification<T> result;
        if (context.hasProperty()) {
            result = new AttributeSpecification<>(context.pickProperty(), specification);
        } else {
            result = (Specification<T>) specification;
        }
        return not ? result.negate() : result;
    }
}
