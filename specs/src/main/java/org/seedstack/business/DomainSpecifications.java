/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import static org.seedstack.seed.core.utils.BaseClassSpecifications.ancestorMetaAnnotatedWith;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.and;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.classAnnotatedWith;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.classIsAbstract;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.classIsAnnotation;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.classIsInterface;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.descendantOf;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.not;

import org.kametic.specifications.Specification;
import org.seedstack.business.domain.DomainAggregateRoot;
import org.seedstack.business.domain.DomainEntity;
import org.seedstack.business.domain.DomainFactory;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRepository;
import org.seedstack.business.domain.DomainValueObject;
import org.seedstack.business.domain.identity.IdentityHandler;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.finder.Finder;
import org.seedstack.business.spi.GenericImplementation;


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
    public static final Specification<Class<?>> SERVICE = and(
            ancestorMetaAnnotatedWith(Service.class),
            classIsInterface(),
            not(classIsAnnotation()));

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
            not(classIsAnnotation()));

    /**
     * The domain factory specification.
     */
    public static final Specification<Class<?>> FACTORY = and(
            ancestorMetaAnnotatedWith(DomainFactory.class),
            classIsInterface(),
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
