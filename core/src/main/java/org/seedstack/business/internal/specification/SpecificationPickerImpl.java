/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.specification.EqualSpecification;
import org.seedstack.business.specification.GreaterThanSpecification;
import org.seedstack.business.specification.LessThanSpecification;
import org.seedstack.business.specification.PropertySpecification;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.StringEqualSpecification;
import org.seedstack.business.specification.StringMatchingSpecification;
import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.OperatorPicker;
import org.seedstack.business.specification.dsl.SpecificationPicker;
import org.seedstack.business.specification.dsl.StringOptionPicker;


class SpecificationPickerImpl<T, SELECTOR extends BaseSelector<T, SELECTOR>> implements SpecificationPicker<T, SELECTOR> {
    private final SpecificationBuilderContext<T, SELECTOR> context;
    private boolean not;

    SpecificationPickerImpl(SpecificationBuilderContext<T, SELECTOR> context) {
        this.context = context;
    }

    @Override
    public SpecificationPicker<T, SELECTOR> not() {
        not = !not;
        return this;
    }

    @Override
    public StringOptionPicker<T, SELECTOR> matching(String pattern) {
        StringValueOptionsImpl stringValueOptions = new StringValueOptionsImpl();
        context.addSpecification(processSpecification(new StringMatchingSpecification(pattern, stringValueOptions)));
        return new StringOptionPickerImpl<>(context, stringValueOptions);
    }

    @Override
    public StringOptionPicker<T, SELECTOR> equalTo(String value) {
        StringValueOptionsImpl stringValueOptions = new StringValueOptionsImpl();
        context.addSpecification(processSpecification(new StringEqualSpecification(value, stringValueOptions)));
        return new StringOptionPickerImpl<>(context, stringValueOptions);
    }

    @Override
    public <V> OperatorPicker<T, SELECTOR> equalTo(V value) {
        context.addSpecification(processSpecification(new EqualSpecification<>(value)));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> greaterThan(V value) {
        context.addSpecification(processSpecification(new GreaterThanSpecification<>(value)));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> greaterThanOrEqualTo(V value) {
        context.addSpecification(processSpecification(new EqualSpecification<>(value).or(new GreaterThanSpecification<>(value))));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> lessThan(V value) {
        context.addSpecification(processSpecification(new LessThanSpecification<>(value)));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> lessThanOrEqualTo(V value) {
        context.addSpecification(processSpecification(new EqualSpecification<>(value).or(new LessThanSpecification<>(value))));
        return new OperatorPickerImpl<>(context);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> between(V leftValue, V rightValue) {
        return between(leftValue, rightValue, false, false);
    }

    @Override
    public <V extends Comparable<? super V>> OperatorPicker<T, SELECTOR> between(V leftValue, V rightValue, boolean leftInclusive, boolean rightInclusive) {
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
            result = new PropertySpecification<>(context.pickProperty(), specification);
        } else {
            result = (Specification<T>) specification;
        }
        return not ? result.negate() : result;
    }
}
