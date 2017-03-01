/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.EqualSpecification;
import org.seedstack.business.domain.specification.GreaterThanSpecification;
import org.seedstack.business.domain.specification.LessThanSpecification;
import org.seedstack.business.domain.specification.Specification;
import org.seedstack.business.domain.specification.StringEqualSpecification;
import org.seedstack.business.domain.specification.StringMatchingSpecification;
import org.seedstack.business.domain.specification.builder.BaseOptionPicker;
import org.seedstack.business.domain.specification.builder.SpecificationPicker;
import org.seedstack.business.domain.specification.builder.StringOptionPicker;

class SpecificationPickerImpl<A extends AggregateRoot<?>> implements SpecificationPicker<A> {
    private final SpecificationBuilderContext<A> context;
    private boolean not;

    SpecificationPickerImpl(SpecificationBuilderContext<A> context) {
        this.context = context;
    }

    @Override
    public SpecificationPicker<A> not() {
        not = true;
        return this;
    }

    @Override
    public StringOptionPicker<A> equalTo(String value) {
        StringValueOptionsImpl stringValueOptions = new StringValueOptionsImpl();
        context.addSpecification(processSpecification(new StringEqualSpecification<>(context.pickProperty(), value, stringValueOptions)));
        return new StringOptionPickerImpl<>(context, stringValueOptions);
    }

    @Override
    public BaseOptionPicker<A> equalTo(Object value) {
        context.addSpecification(processSpecification(new EqualSpecification<>(context.pickProperty(), value)));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public BaseOptionPicker<A> greaterThan(Comparable<?> value) {
        context.addSpecification(processSpecification(new GreaterThanSpecification<>(context.pickProperty(), value)));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public BaseOptionPicker<A> greaterThanOrEqualTo(Comparable<?> value) {
        String path = context.pickProperty();
        context.addSpecification(processSpecification(new EqualSpecification<A>(path, value).or(new GreaterThanSpecification<>(path, value))));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public BaseOptionPicker<A> lessThan(Comparable<?> value) {
        context.addSpecification(processSpecification(new LessThanSpecification<>(context.pickProperty(), value)));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public BaseOptionPicker<A> lessThanOrEqualTo(Comparable<?> value) {
        String path = context.pickProperty();
        context.addSpecification(processSpecification(new EqualSpecification<A>(path, value).or(new LessThanSpecification<>(path, value))));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public BaseOptionPicker<A> between(Comparable<?> leftValue, Comparable<?> rightValue) {
        return between(leftValue, rightValue, true, true);
    }

    @Override
    public BaseOptionPicker<A> between(Comparable<?> leftValue, Comparable<?> rightValue, boolean leftInclusive, boolean rightInclusive) {
        String path = context.pickProperty();
        Specification<A> gt = new GreaterThanSpecification<>(path, leftValue);
        if (leftInclusive) {
            gt = gt.or(new EqualSpecification<>(path, leftValue));
        }
        Specification<A> lt = new LessThanSpecification<>(path, rightValue);
        if (rightInclusive) {
            lt = lt.or(new EqualSpecification<>(path, rightValue));
        }
        context.addSpecification(processSpecification(gt.and(lt)));
        return new BaseOptionPickerImpl<>(context);
    }

    @Override
    public StringOptionPicker<A> matching(String pattern) {
        StringValueOptionsImpl stringValueOptions = new StringValueOptionsImpl();
        context.addSpecification(processSpecification(new StringMatchingSpecification<>(context.pickProperty(), pattern, stringValueOptions)));
        return new StringOptionPickerImpl<>(context, stringValueOptions);
    }

    private Specification<A> processSpecification(Specification<A> specification) {
        return not ? specification.not() : specification;
    }
}
