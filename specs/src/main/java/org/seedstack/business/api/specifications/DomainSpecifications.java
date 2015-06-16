/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.specifications;

import org.kametic.specifications.Specification;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.business.api.application.GenericApplicationService;
import org.seedstack.business.api.application.annotations.ApplicationService;
import org.seedstack.business.api.domain.GenericDomainPolicy;
import org.seedstack.business.api.domain.GenericDomainService;
import org.seedstack.business.api.domain.annotations.*;
import org.seedstack.business.api.domain.identity.IdentityHandler;
import org.seedstack.business.api.interfaces.GenericInterfacesService;
import org.seedstack.business.api.interfaces.annotations.InterfacesService;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.api.interfaces.query.finder.Finder;

import static org.seedstack.business.api.specifications.BaseClassSpecifications.*;


/**
 * This class provides all the specifications use by the business plugins.
 *
 * @author epo.jemba@ext.mpsa.com
 */
@SuppressWarnings("unchecked")
public final class DomainSpecifications {

    private DomainSpecifications() {
    }

    /**
     * The aggregate root specification.
     */
    public static final Specification<Class<?>> AGGREGATE_ROOT = and(
            ancestorMetaAnnotatedWith(DomainAggregateRoot.class),
            not(classIsAbstract()),
            not(classIsInterface())
    );

    /**
     * The domain entities specification.
     */
    public static final Specification<Class<?>> ENTITY = and(
            ancestorMetaAnnotatedWith(DomainEntity.class),
            not(classIsAbstract()),
            not(classIsInterface())
    );

    /**
     * The domain value objects specification.
     */
    public static final Specification<Class<?>> VALUE_OBJECT = and(
            ancestorMetaAnnotatedWith(DomainValueObject.class),
            not(classIsAbstract()),
            not(classIsInterface())
    );

    /**
     * The domain repository specification.
     */
    public static final Specification<Class<?>> REPOSITORY = and(
            ancestorMetaAnnotatedWith(DomainRepository.class),
            classIsInterface(),
            not(classIsAnnotation())
    );

    /**
     * The domain service specification.
     */
    public static final Specification<Class<?>> DOMAIN_SERVICE = and(
            ancestorMetaAnnotatedWith(DomainService.class),
            classIsInterface(),
            not(classIsAnnotation()),
            not(classIs(GenericDomainService.class)));

    /**
     * The application service specification.
     */
    public static final Specification<Class<?>> APPLICATION_SERVICE = and(
            ancestorMetaAnnotatedWith(ApplicationService.class),
            classIsInterface(),
            not(classIsAnnotation()),
            not(classIs(GenericApplicationService.class)));

    /**
     * The finder service specification.
     */
    public static final Specification<Class<?>> FINDER = and(
            ancestorMetaAnnotatedWith(Finder.class),
            classIsInterface(),
            not(classIsAnnotation()));

    /**
     * The policy specification.
     */
    public static final Specification<Class<?>> POLICY = and(
            ancestorMetaAnnotatedWith(DomainPolicy.class),
            classIsInterface(),
            not(classIsAnnotation()),
            not(classIs(GenericDomainPolicy.class)));

    /**
     * The interface service specification.
     */
    public static final Specification<Class<?>> INTERFACE_SERVICE = and(
            ancestorMetaAnnotatedWith(InterfacesService.class),
            classIsInterface(),
            not(classIsAnnotation()),
            not(classIs(GenericInterfacesService.class)));

    /**
     * The domain factory specification.
     */
    public static final Specification<Class<?>> FACTORY = and(
            ancestorMetaAnnotatedWith(DomainFactory.class),
            classIsInterface(),
            not(classIs(Factory.class)),
            not(classIsAnnotation()));

    /**
     * The assembler specification. It accepts all assemblers: default assemblers and classic assemblers.
     */
    public static final Specification<Class<?>> ASSEMBLER = and(
            not(classIsInterface()),
            not(classIsAbstract()),
            descendantOf(Assembler.class));

    /**
     * The assembler specification matching only the classic assembler, i.e. non-default assemblers.
     */
    public static final Specification<Class<?>> CLASSIC_ASSEMBLER = and(
            ASSEMBLER, not(classAnnotatedWith(GenericImplementation.class)));

    /**
     * The assembler specification matching only the default assemblers.
     * <p>
     * Default assemblers are assembler implementation which are bound for all the DTOs
     * matching the {@link #DTO_OF} specification.
     * </p>
     */
    public static final Specification<Class<?>> DEFAULT_ASSEMBLER =
            ASSEMBLER.and(classAnnotatedWith(GenericImplementation.class));

    public static final Specification<Class<?>> DEFAULT_REPOSITORY = and(
            not(classIsInterface()),
            not(classIsAbstract()),
            ancestorMetaAnnotatedWith(DomainRepository.class),
            classAnnotatedWith(GenericImplementation.class));

    /**
     * The specification for the dtos which require an default assembler to be bound.
     *
     * @see #DEFAULT_ASSEMBLER
     */
    public static final Specification<Class<?>> DTO_OF = classAnnotatedWith(DtoOf.class);

    /**
     * The identity handler specification. It matches all the classes implementing
     * the identity handler SPI.
     */
    public static final Specification<Class<?>> IDENTITY_HANDLER = and(
            not(classIsInterface()),
            not(classIsAbstract()),
            descendantOf(IdentityHandler.class));

}
