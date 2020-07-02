/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal;

import static org.seedstack.shed.reflect.AnnotationPredicates.classOrAncestorAnnotatedWith;
import static org.seedstack.shed.reflect.AnnotationPredicates.elementAnnotatedWith;
import static org.seedstack.shed.reflect.ClassPredicates.classIsAnnotation;
import static org.seedstack.shed.reflect.ClassPredicates.classIsAssignableFrom;
import static org.seedstack.shed.reflect.ClassPredicates.classIsDescendantOf;
import static org.seedstack.shed.reflect.ClassPredicates.classIsInterface;
import static org.seedstack.shed.reflect.ClassPredicates.classModifierIs;

import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import org.seedstack.business.Service;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.data.DataExporter;
import org.seedstack.business.data.DataImporter;
import org.seedstack.business.data.DataSet;
import org.seedstack.business.domain.DomainAggregateRoot;
import org.seedstack.business.domain.DomainEntity;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventInterceptor;
import org.seedstack.business.domain.DomainFactory;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRepository;
import org.seedstack.business.domain.DomainValueObject;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.Producible;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.business.spi.SpecificationConverter;
import org.seedstack.business.spi.SpecificationTranslator;
import org.seedstack.shed.reflect.AnnotationPredicates;

/**
 * This class provides all the specifications use by the business plugins.
 */
public final class BusinessSpecifications {

    /**
     * The aggregate root specification.
     */
    public static final Predicate<Class<?>> AGGREGATE_ROOT = classOrAncestorAnnotatedWith(DomainAggregateRoot.class,
            true)
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsInterface().negate());

    /**
     * The domain entities specification.
     */
    public static final Predicate<Class<?>> ENTITY = classOrAncestorAnnotatedWith(DomainEntity.class, true)
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsInterface().negate());

    /**
     * The domain value objects specification.
     */
    public static final Predicate<Class<?>> VALUE_OBJECT = classOrAncestorAnnotatedWith(DomainValueObject.class,
            true)
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsInterface().negate());

    /**
     * The domain repository specification.
     */
    public static final Predicate<Class<?>> REPOSITORY = classOrAncestorAnnotatedWith(DomainRepository.class, true)
            .and(classIsInterface())
            .and(classIsAnnotation().negate());

    public static final Predicate<Class<?>> DEFAULT_REPOSITORY = AnnotationPredicates.<Class<?>>elementAnnotatedWith(
            GenericImplementation.class,
            true)
            .and(classIsInterface().negate())
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classOrAncestorAnnotatedWith(DomainRepository.class, true));

    /**
     * The domain service specification.
     */
    public static final Predicate<Class<?>> SERVICE = classOrAncestorAnnotatedWith(Service.class, true)
            .and(classIsInterface())
            .and(classIsAnnotation().negate());

    /**
     * The policy specification.
     */
    public static final Predicate<Class<?>> POLICY = classOrAncestorAnnotatedWith(DomainPolicy.class, true)
            .and(classIsInterface())
            .and(classIsAnnotation().negate());

    /**
     * The domain factory specification.
     *
     * @see #PRODUCIBLE
     */
    public static final Predicate<Class<?>> FACTORY = classOrAncestorAnnotatedWith(DomainFactory.class, true)
            .and(classIsInterface())
            .and(classIsAnnotation().negate());

    /**
     * The specification for the classes producible by factories.
     */
    public static final Predicate<Class<?>> PRODUCIBLE = classIsAssignableFrom(Producible.class);

    /**
     * The assembler specification. It accepts all assemblers: default assemblers and explicit
     * assemblers.
     */
    public static final Predicate<Class<?>> ASSEMBLER =
            classIsDescendantOf(Assembler.class).and(classIsInterface().negate())
                    .and(classModifierIs(Modifier.ABSTRACT).negate());

    /**
     * The assembler specification matching only the explicit assembler.
     */
    public static final Predicate<Class<?>> EXPLICIT_ASSEMBLER = ASSEMBLER.and(elementAnnotatedWith(
            GenericImplementation.class,
            true).negate());

    /**
     * The assembler specification matching only the default assemblers. <p> Default assemblers are
     * assembler implementation which are bound for all the DTOs matching the {@link #DTO_OF}
     * specification. </p>
     */
    public static final Predicate<Class<?>> DEFAULT_ASSEMBLER =
            ASSEMBLER.and(elementAnnotatedWith(GenericImplementation.class, true));

    /**
     * The specification for the DTO which require an default assembler to be bound.
     *
     * @see #DEFAULT_ASSEMBLER
     */
    public static final Predicate<Class<?>> DTO_OF = elementAnnotatedWith(DtoOf.class, true);

    /**
     * The specification for specification translators.
     */
    public static final Predicate<Class<?>> DTO_INFO_RESOLVER = classIsInterface().negate()
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsDescendantOf(DtoInfoResolver.class));
    /**
     * The data importer specification.
     */
    public static final Predicate<Class<?>> DATA_IMPORTER =
            classIsDescendantOf(DataImporter.class).and(classIsInterface().negate())
                    .and(classModifierIs(Modifier.ABSTRACT).negate());

    /**
     * The data exporter specification.
     */
    public static final Predicate<Class<?>> DATA_EXPORTER =
            classIsDescendantOf(DataExporter.class).and(classIsInterface().negate())
                    .and(classModifierIs(Modifier.ABSTRACT).negate());

    /**
     * The specification matching automatic DTO used for data import/export.
     */
    public static final Predicate<Class<?>> DATA_SET = DTO_OF.and(elementAnnotatedWith(DataSet.class, true));

    /**
     * The specification for identity generators.
     */
    public static final Predicate<Class<?>> IDENTITY_GENERATOR = classIsInterface().negate()
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsDescendantOf(IdentityGenerator.class));

    /**
     * The specification for specification translators.
     */
    public static final Predicate<Class<?>> SPECIFICATION_TRANSLATOR = classIsInterface().negate()
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsDescendantOf(SpecificationTranslator.class));

    /**
     * The specification for specification converters.
     */
    public static final Predicate<Class<?>> SPECIFICATION_CONVERTER = classIsInterface().negate()
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsDescendantOf(SpecificationConverter.class));

    /**
     * The specification for domain events.
     */
    public static final Predicate<Class<?>> DOMAIN_EVENT = classIsAssignableFrom(DomainEvent.class);

    /**
     * The specification for domain event handlers.
     */
    public static final Predicate<Class<?>> DOMAIN_EVENT_HANDLER = classIsInterface().negate()
            .and(classModifierIs(Modifier.ABSTRACT).negate())
            .and(classIsAssignableFrom(DomainEventHandler.class));

    /**
     * The specification for domain interceptors;
     */
    public static final Predicate<Class<?>> DOMAIN_EVENT_INTERCEPTOR = classIsInterface().negate()
            .and((classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsAssignableFrom(DomainEventInterceptor.class)));

    private BusinessSpecifications() {
        // no instantiation allowed
    }
}
