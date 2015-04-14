/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.meta.specifications;

import org.kametic.specifications.Specification;
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

import static org.seedstack.business.api.domain.meta.specifications.BaseClassSpecifications.*;

/**
 * This class provides helper methods for domain specifications.
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
    public static final Specification<Class<?>> aggregateRootSpecification = and(
                ancestorMetaAnnotatedWith(DomainAggregateRoot.class),
                not(classIsAbstract()),
                not(classIsInterface())
        );

    /**
     * The domain entities specification.
     */
    public static final Specification<Class<?>> entitySpecification = and(
                ancestorMetaAnnotatedWith(DomainEntity.class),
                not(classIsAbstract()),
                not(classIsInterface())
        );

    /**
     * The domain value objects specification.
     */
    public static final Specification<Class<?>> valueObjectSpecification = and(
                ancestorMetaAnnotatedWith(DomainValueObject.class),
                not(classIsAbstract()),
                not(classIsInterface())
        );

    public static final Specification<Class<?>> domainRepoSpecification = and(
                ancestorMetaAnnotatedWith(DomainRepository.class),
                classIsInterface(),
                not(classIsAnnotation())
        );

    public static final Specification<Class<?>> domainServiceSpecification = and(
                ancestorMetaAnnotatedWith(DomainService.class),
                classIsInterface(),
                not(classIsAnnotation()),
                not(classIs(GenericDomainService.class)));

    public static final Specification<Class<?>> applicationServiceSpecification = and(
                ancestorMetaAnnotatedWith(ApplicationService.class),
                classIsInterface(),
                not(classIsAnnotation()),
                not(classIs(GenericApplicationService.class)));

    public static final Specification<Class<?>> finderServiceSpecification = and(
                ancestorMetaAnnotatedWith(Finder.class),
                classIsInterface(),
                not(classIsAnnotation()));

    public static final Specification<Class<?>> policySpecification = and(
                ancestorMetaAnnotatedWith(DomainPolicy.class),
                classIsInterface(),
                not(classIsAnnotation()),
                not(classIs(GenericDomainPolicy.class)));

    public static final Specification<Class<?>> interfacesServiceSpecification = and(
                ancestorMetaAnnotatedWith(InterfacesService.class),
                classIsInterface(),
                not(classIsAnnotation()),
                not(classIs(GenericInterfacesService.class)));

    public static final Specification<Class<?>> domainFactorySpecification = and(
                ancestorMetaAnnotatedWith(DomainFactory.class),
                classIsInterface(),
                not(classIsAnnotation()));

    public static final Specification<Class<?>> assemblerSpecification = and(
                not(classIsInterface()),
                not(classIsAbstract()),
                descendantOf(Assembler.class));

    public static final Specification<Class<?>> domainRepoImplSpecification = and(
                classAnnotatedWith(DomainRepositoryImpl.class),
                not(classIsInterface()),
                not(classIsAbstract()),
                not(classIsAnnotation()));

    public static final Specification<Class<?>> dtoWithDefaultAssemblerSpecification = and(
                classAnnotatedWith(DtoOf.class));

    public static final Specification<Class<?>> identityHandlerSpecification = and(
                not(classIsInterface()),
                not(classIsAbstract()),
                descendantOf(IdentityHandler.class));

}
