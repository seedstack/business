/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.kametic.specifications.Specification;
import org.seedstack.business.Service;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.domain.DomainAggregateRoot;
import org.seedstack.business.domain.DomainEntity;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainFactory;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRepository;
import org.seedstack.business.domain.DomainValueObject;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.Producible;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.business.spi.assembler.DtoInfoResolver;
import org.seedstack.business.spi.specification.SpecificationConverter;
import org.seedstack.business.spi.specification.SpecificationTranslator;
import org.seedstack.seed.core.internal.utils.SpecificationBuilder;
import org.seedstack.shed.reflect.AnnotationPredicates;

import java.lang.reflect.Modifier;

import static org.seedstack.shed.reflect.AnnotationPredicates.classOrAncestorAnnotatedWith;
import static org.seedstack.shed.reflect.AnnotationPredicates.elementAnnotatedWith;
import static org.seedstack.shed.reflect.ClassPredicates.classIsAnnotation;
import static org.seedstack.shed.reflect.ClassPredicates.classIsAssignableFrom;
import static org.seedstack.shed.reflect.ClassPredicates.classIsDescendantOf;
import static org.seedstack.shed.reflect.ClassPredicates.classIsInterface;
import static org.seedstack.shed.reflect.ClassPredicates.classModifierIs;


/**
 * This class provides all the specifications use by the business plugins.
 */
public final class BusinessSpecifications {
    /**
     * The aggregate root specification.
     */
    public static final Specification<Class<?>> AGGREGATE_ROOT = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(DomainAggregateRoot.class, true)
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsInterface().negate()))
            .build();

    /**
     * The domain entities specification.
     */
    public static final Specification<Class<?>> ENTITY = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(DomainEntity.class, true)
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsInterface().negate()))
            .build();

    /**
     * The domain value objects specification.
     */
    public static final Specification<Class<?>> VALUE_OBJECT = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(DomainValueObject.class, true)
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsInterface().negate()))
            .build();

    /**
     * The domain repository specification.
     */
    public static final Specification<Class<?>> REPOSITORY = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(DomainRepository.class, true)
                    .and(classIsInterface())
                    .and(classIsAnnotation().negate()))
            .build();

    public static final Specification<Class<?>> DEFAULT_REPOSITORY = new SpecificationBuilder<>(
            AnnotationPredicates.<Class<?>>elementAnnotatedWith(GenericImplementation.class, true)
                    .and(classIsInterface().negate())
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classOrAncestorAnnotatedWith(DomainRepository.class, true)))
            .build();

    /**
     * The domain service specification.
     */
    public static final Specification<Class<?>> SERVICE = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(Service.class, true)
                    .and(classIsInterface())
                    .and(classIsAnnotation().negate()))
            .build();

    /**
     * The policy specification.
     */
    public static final Specification<Class<?>> POLICY = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(DomainPolicy.class, true)
                    .and(classIsInterface())
                    .and(classIsAnnotation().negate()))
            .build();

    /**
     * The domain factory specification.
     *
     * @see #PRODUCIBLE
     */
    public static final Specification<Class<?>> FACTORY = new SpecificationBuilder<>(
            classOrAncestorAnnotatedWith(DomainFactory.class, true)
                    .and(classIsInterface())
                    .and(classIsAnnotation().negate()))
            .build();

    /**
     * The specification for the classes producible by factories.
     */
    public static final Specification<Class<?>> PRODUCIBLE = new SpecificationBuilder<>(
            classIsAssignableFrom(Producible.class))
            .build();

    /**
     * The assembler specification. It accepts all assemblers: default assemblers and explicit assemblers.
     */
    public static final Specification<Class<?>> ASSEMBLER = new SpecificationBuilder<>(
            classIsDescendantOf(Assembler.class)
                    .and(classIsInterface().negate())
                    .and(classModifierIs(Modifier.ABSTRACT).negate()))
            .build();

    /**
     * The assembler specification matching only the explicit assembler
     */
    public static final Specification<Class<?>> EXPLICIT_ASSEMBLER = ASSEMBLER.and(new SpecificationBuilder<>(
            elementAnnotatedWith(GenericImplementation.class, true).negate()
    ).build());

    /**
     * The assembler specification matching only the default assemblers.
     * <p>
     * Default assemblers are assembler implementation which are bound for all the DTOs
     * matching the {@link #DTO_OF} specification.
     * </p>
     */
    public static final Specification<Class<?>> DEFAULT_ASSEMBLER = ASSEMBLER.and(new SpecificationBuilder<>(
            elementAnnotatedWith(GenericImplementation.class, true)
    ).build());

    /**
     * The specification for the DTO which require an default assembler to be bound.
     *
     * @see #DEFAULT_ASSEMBLER
     */
    public static final Specification<Class<?>> DTO_OF = new SpecificationBuilder<Class<?>>(
            elementAnnotatedWith(DtoOf.class, true))
            .build();

    /**
     * The specification for specification translators.
     */
    public static final Specification<Class<?>> DTO_INFO_RESOLVER = new SpecificationBuilder<>(
            classIsInterface().negate()
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsDescendantOf(DtoInfoResolver.class)))
            .build();

    /**
     * The specification for identity generators.
     */
    public static final Specification<Class<?>> IDENTITY_GENERATOR = new SpecificationBuilder<>(
            classIsInterface().negate()
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsDescendantOf(IdentityGenerator.class)))
            .build();

    /**
     * The specification for specification translators.
     */
    public static final Specification<Class<?>> SPECIFICATION_TRANSLATOR = new SpecificationBuilder<>(
            classIsInterface().negate()
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsDescendantOf(SpecificationTranslator.class)))
            .build();

    /**
     * The specification for specification converters.
     */
    public static final Specification<Class<?>> SPECIFICATION_CONVERTER = new SpecificationBuilder<>(
            classIsInterface().negate()
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsDescendantOf(SpecificationConverter.class)))
            .build();

    /**
     * The specification for domain events.
     */
    public static final Specification<Class<?>> DOMAIN_EVENT = new SpecificationBuilder<>(
            classIsAssignableFrom(DomainEvent.class))
            .build();

    /**
     * The specification for domain event handlers.
     */
    public static final Specification<Class<?>> DOMAIN_EVENT_HANDLER = new SpecificationBuilder<>(
            classIsInterface().negate()
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsAssignableFrom(DomainEventHandler.class)))
            .build();

    private BusinessSpecifications() {
        // no instantiation allowed
    }
}
