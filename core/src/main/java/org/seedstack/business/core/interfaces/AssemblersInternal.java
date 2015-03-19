/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gag.annotation.remark.Hack;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import org.fest.reflect.core.Reflection;
import org.javatuples.Quintet;
import org.javatuples.Tuple;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.*;
import org.seedstack.business.helpers.Assemblers;
import org.seedstack.seed.core.api.SeedException;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.seedstack.business.helpers.Tuples.createTupleFromList;


/**
 * The class is the implementation of the {@link org.seedstack.business.api.interfaces.assembler.Assemblers} class.
 * <p/>
 * For now, it implements the deprecated {@link org.seedstack.business.helpers.Assemblers} class. This is intended
 * to provide a backward compatibility, but the new {@code Assemblers} class should be already usable. The two classes
 * will work the same way.
 *
 * @author epo.jemba@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
@SuppressWarnings({"deprecation"})
public class AssemblersInternal implements Assemblers {

    private static final String ENTITY_CLASS = "entityClass", DTO_CLASS = "dtoClass", TUPLE_CLASS = "tupleClass";
    private static final String MESSAGE = "message", TARGET_CLASS = "targetClass", REPRESENTATION_CLASS = "representationClass";
    private static final String REPRESENTATION_GATEWAY = "representationGateway", AGGREGATE_ROOT_CLASS = "aggregateRootClass";
    private static final String OF_TUPLE_CLASS = "ofTupleClass", ASSEMBLER_1 = "assembler1", ASSEMBLER_2 = "assembler2";
    private static final String CLASS_NAME = "className", METHOD_NAME = "methodName";

    @SuppressWarnings("rawtypes")
    private Map<Class, Assembler> assemblers;

    @SuppressWarnings("rawtypes")
    @Inject
    @Named("assemblersTypes")
    private Set<Class> assemblersClasses;

    @Inject
    private Injector injector;

    @Inject
    private Repositories repositories;

    @Inject
    private Factories factories;

    /**
     * Constructor.
     */
    AssemblersInternal() {
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> DTO assembleDtoFromEntity(Class<DTO> targetDtoClass, AGGREGATE_ROOT sourceEntity) {
        return assembleDtoFromAggregate(targetDtoClass, sourceEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> DTO assembleDtoFromAggregate(Class<DTO> targetDtoClass, AGGREGATE_ROOT aggregate) {
        DTO dto;

        Assembler<AggregateRoot<?>, Object, Type> a = findAssembler(aggregate.getClass(), targetDtoClass);

        if (a == null) {
            throw SeedException.createNew(AssemblerErrorCodes.ASSEMBLER_NOT_FOUND)
                    .put(ENTITY_CLASS, aggregate.getClass())
                    .put(DTO_CLASS, targetDtoClass);
        }

        dto = (DTO) a.assembleDtoFromAggregate(aggregate);

        return dto;
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<DTO> assembleDtoFromEntity(Class<DTO> targetDtoClass, List<AGGREGATE_ROOT> sourceEntity) {
        return assembleDtoFromAggregate(targetDtoClass, sourceEntity);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<DTO> assembleDtoFromAggregate(Class<DTO> targetDtoClass, List<AGGREGATE_ROOT> sourceAggregate) {
        List<DTO> dtos = Lists.newArrayListWithCapacity(sourceAggregate.size());

        for (AGGREGATE_ROOT aggregateRoot : sourceAggregate) {
            dtos.add(assembleDtoFromAggregate(targetDtoClass, aggregateRoot));
        }

        return dtos;
    }


    @Override
    @Deprecated
    public <TUPLE_OF_AGROOT extends Tuple, DTO> List<DTO> assembleDtoFromEntities(Class<DTO> targetDtoClass, List<TUPLE_OF_AGROOT> sourceEntities) {
        return assembleDtoFromAggregates(targetDtoClass, sourceEntities);
    }

    @Override
    public <TUPLE_OF_AGROOT extends Tuple, DTO> List<DTO> assembleDtoFromAggregates(Class<DTO> targetDtoClass, List<TUPLE_OF_AGROOT> sourceAggregates) {
        List<DTO> dtos = Lists.newArrayListWithCapacity(sourceAggregates.size());

        for (TUPLE_OF_AGROOT elem : sourceAggregates) {
            dtos.add(assembleDtoFromAggregates(targetDtoClass, elem));
        }
        return dtos;
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOT extends Tuple, AGGREGATE_ROOT extends AggregateRoot<?>, TYPE extends Type, DTO>
    DTO assembleDtoFromEntities(Class<DTO> targetDtoClass, TUPLE_OF_AGROOT sourceEntities) {
        return assembleDtoFromAggregates(targetDtoClass, sourceEntities);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <TUPLE_OF_AGROOT extends Tuple, AGGREGATE_ROOT extends AggregateRoot<?>, TYPE extends Type, DTO> DTO assembleDtoFromAggregates(Class<DTO> targetDtoClass, TUPLE_OF_AGROOT sourceAggregates) {

        // TODO add support for universal dtos
        // if the target dto class is equals to UniversalDto the method should return createUniversalDtoFromEntity(sourceAggregates).

        Assembler a = findAssembler(sourceAggregates.getClass(), targetDtoClass);

        if (a == null) {
            throw SeedException.createNew(AssemblerErrorCodes.ASSEMBLER_NOT_FOUND)
                    .put(ENTITY_CLASS, sourceAggregates.getClass())
                    .put(DTO_CLASS, targetDtoClass);
        }

        BaseTupleAssembler<TUPLE_OF_AGROOT, DTO> btaAssembler = (BaseTupleAssembler<TUPLE_OF_AGROOT, DTO>) a;

        return btaAssembler.assembleDtoFromAggregate(sourceAggregates);
    }


    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO, TYPE extends Type> void mergeEntityWithDto(DTO sourceDto, AGGREGATE_ROOT targetEntity) {
        mergeAggregateWithDto(sourceDto, targetEntity);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO, TYPE extends Type> void mergeAggregateWithDto(DTO sourceDto, AGGREGATE_ROOT targetAggregate) {
        Assembler<AggregateRoot<?>, Object, TYPE> a = findAssembler(targetAggregate.getClass(), sourceDto.getClass());
        a.mergeAggregateWithDto(targetAggregate, sourceDto);
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO>
    void mergeEntitiesWithDto(DTO sourceDto, TUPLE_OF_AGROOTS targetEntities) {
        mergeAggregatesWithDto(sourceDto, targetEntities);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> void mergeAggregatesWithDto(DTO sourceDto, TUPLE_OF_AGROOTS targetAggregates) {
        Assembler a = findAssembler(targetAggregates.getClass(), sourceDto.getClass());
        BaseTupleAssembler<TUPLE_OF_AGROOTS, DTO> btaAssembler = (BaseTupleAssembler<TUPLE_OF_AGROOTS, DTO>) a;
        btaAssembler.mergeAggregateWithDto(targetAggregates, sourceDto);
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    AGGREGATE_ROOT createThenMergeEntityWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetEntityClass) {
        return createThenMergeAggregateWithDto(sourceDto, targetEntityClass);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    AGGREGATE_ROOT createThenMergeAggregateWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass) {
        return mergeAggregate(sourceDto, targetAggregateClass, false, true);
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> createThenMergeEntityWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetEntityClass) {
        return createThenMergeAggregateWithDto(sourceDtos, targetEntityClass);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<AGGREGATE_ROOT> createThenMergeAggregateWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetAggregateClass) {
        List<AGGREGATE_ROOT> aggregateRoots = Lists.newArrayListWithCapacity(sourceDtos.size());
        for (DTO sourceDto : sourceDtos) {
            aggregateRoots.add(createThenMergeAggregateWithDto(sourceDto, targetAggregateClass));
        }
        return aggregateRoots;
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO>
    TUPLE_OF_AGROOTS createThenMergeEntitiesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass) {
        return createThenMergeAggregatesWithDto(sourceDto, targetEntityClass);
    }

    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS createThenMergeAggregatesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClass) {
        return mergeAggregates(sourceDto, targetAggregateClass, false, true);
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> createThenMergeEntitiesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass) {
        return createThenMergeAggregatesWithDto(sourceDtos, targetEntityClass);
    }

    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> List<TUPLE_OF_AGROOTS> createThenMergeAggregatesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClass) {
        List<TUPLE_OF_AGROOTS> dtos = Lists.newArrayListWithCapacity(sourceDtos.size());
        for (DTO sourceDto : sourceDtos) {
            dtos.add(createThenMergeAggregatesWithDto(sourceDto, targetAggregateClass));
        }
        return dtos;
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> AGGREGATE_ROOT retrieveThenMergeEntityWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetEntityClass) {
        return retrieveThenMergeAggregateWithDto(sourceDto, targetEntityClass);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> AGGREGATE_ROOT retrieveThenMergeAggregateWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass) {
        return mergeAggregate(sourceDto, targetAggregateClass, true, false);
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<AGGREGATE_ROOT> retrieveThenMergeEntityWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetEntityClass) {
        return retrieveThenMergeAggregateWithDto(sourceDtos, targetEntityClass);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<AGGREGATE_ROOT> retrieveThenMergeAggregateWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetAggregateClass) {
        List<AGGREGATE_ROOT> aggregateRoots = Lists.newArrayListWithCapacity(sourceDtos.size());
        for (DTO sourceDto : sourceDtos) {
            aggregateRoots.add(retrieveThenMergeAggregateWithDto(sourceDto, targetAggregateClass));
        }
        return aggregateRoots;
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveThenMergeEntitiesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetEntitiesClass) {
        return retrieveThenMergeAggregatesWithDto(sourceDto, targetEntitiesClass);
    }

    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveThenMergeAggregatesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClasses) {
        return mergeAggregates(sourceDto, targetAggregateClasses, true, false);
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> retrieveThenMergeEntitiesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetEntitiesClass) {
        return retrieveThenMergeAggregatesWithDto(sourceDtos, targetEntitiesClass);
    }

    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> List<TUPLE_OF_AGROOTS> retrieveThenMergeAggregatesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregatesClass) {
        List<TUPLE_OF_AGROOTS> tupleOfAggregates = Lists.newArrayListWithCapacity(sourceDtos.size());
        for (DTO sourceDto : sourceDtos) {
            tupleOfAggregates.add(retrieveThenMergeAggregatesWithDto(sourceDto, targetAggregatesClass));
        }
        return tupleOfAggregates;
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> AGGREGATE_ROOT retrieveOrCreateThenMergeEntityWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetEntityClass) {
        return retrieveOrCreateThenMergeAggregateWithDto(sourceDto, targetEntityClass);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> AGGREGATE_ROOT retrieveOrCreateThenMergeAggregateWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass) {
        return mergeAggregate(sourceDto, targetAggregateClass, true, true);
    }

    @Override
    @Deprecated
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> retrieveOrCreateThenMergeEntityWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetEntityClass) {
        return retrieveOrCreateThenMergeAggregateWithDto(sourceDtos, targetEntityClass);
    }

    @Override
    public <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<AGGREGATE_ROOT> retrieveOrCreateThenMergeAggregateWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetAggregateClass) {
        List<AGGREGATE_ROOT> tupleOfAggregates = Lists.newArrayListWithCapacity(sourceDtos.size());
        for (DTO sourceDto : sourceDtos) {
            tupleOfAggregates.add(retrieveOrCreateThenMergeAggregateWithDto(sourceDto, targetAggregateClass));
        }
        return tupleOfAggregates;
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveOrCreateThenMergeEntitiesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass) {
        return retrieveOrCreateThenMergeAggregatesWithDto(sourceDto, targetEntityClass);
    }

    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveOrCreateThenMergeAggregatesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClasses) {
        return mergeAggregates(sourceDto, targetAggregateClasses, true, true);
    }

    @Override
    @Deprecated
    public <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> retrieveOrCreateThenMergeEntitiesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass) {
        return retrieveOrCreateThenMergeAggregatesWithDto(sourceDtos, targetEntityClass);
    }

    @Override
    public <TUPLE_OF_AGROOTS extends Tuple, DTO> List<TUPLE_OF_AGROOTS> retrieveOrCreateThenMergeAggregatesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClasses) {
        List<TUPLE_OF_AGROOTS> tupleOfAggregates = Lists.newArrayListWithCapacity(sourceDtos.size());
        for (DTO sourceDto : sourceDtos) {
            tupleOfAggregates.add(retrieveOrCreateThenMergeAggregatesWithDto(sourceDto, targetAggregateClasses));
        }
        return tupleOfAggregates;
    }

    private <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO, TYPE extends Type>
    AGGREGATE_ROOT mergeAggregate(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass, boolean findFromRepository, boolean createFromFactory) {

        Assembler<AggregateRoot<?>, Object, TYPE> currentAssembler = findAssembler(targetAggregateClass, sourceDto.getClass());

        if (currentAssembler == null) {
            throw SeedException.createNew(AssemblerErrorCodes.ASSEMBLER_NOT_FOUND)
                    .put(ENTITY_CLASS, targetAggregateClass)
                    .put(DTO_CLASS, sourceDto.getClass());
        }

        AGGREGATE_ROOT targetAggregate = null;

        if (findFromRepository) {
            // find id from DTO
            DtoGateway entityIdGateway = findInfosFromDto(sourceDto, MatchingEntityId.class);

            // try to find A
            targetAggregate = findEntity(entityIdGateway, targetAggregateClass);
        }

        // try to create A if necessary and if allowed
        if (targetAggregate == null && createFromFactory) {
            DtoGateway factoryGateway = findInfosFromDto(sourceDto, MatchingFactoryParameter.class);

            targetAggregate = createNewAggregate(factoryGateway, targetAggregateClass);
        }

        // merge them
        currentAssembler.mergeAggregateWithDto(targetAggregate, sourceDto);

        return targetAggregate;
    }

    private <TUPLE_OF_AGROOTS extends Tuple, DTO, TYPE extends Type>
    TUPLE_OF_AGROOTS mergeAggregates(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregatesClass, boolean findFromRepository, boolean createFromFactory) {

        Assembler<AggregateRoot<?>, Object, TYPE> currentAssembler = findAssembler(targetAggregatesClass.getRawType(), sourceDto.getClass());

        @SuppressWarnings({"unchecked", "rawtypes"})
        BaseTupleAssembler<TUPLE_OF_AGROOTS, Object> tupleAssembler = (BaseTupleAssembler) currentAssembler;

        SeedException.createNew(AssemblerErrorCodes.ASSEMBLER_NOT_FOUND)
                .put(TUPLE_CLASS, targetAggregatesClass)
                .put(DTO_CLASS, sourceDto)
                .throwsIf(currentAssembler == null);

        TUPLE_OF_AGROOTS targetAggregate = null;

        if (findFromRepository) {
            // find id from DTO
            DtoGateway entityIdGateway = findInfosFromDto(sourceDto, MatchingEntityId.class);
            entityIdGateway.throwsExceptionIfNeeded();
            // try to find A
            targetAggregate = findEntities(entityIdGateway, targetAggregatesClass);
        }

        // try to create A if necessary and if allowed
        if (targetAggregate == null && createFromFactory) {
            DtoGateway factoryGateway = findInfosFromDto(sourceDto, MatchingFactoryParameter.class);

            targetAggregate = createNewEntities(factoryGateway, targetAggregatesClass);
        }

        // merge them
        tupleAssembler.mergeAggregateWithDto(targetAggregate, sourceDto);

        return targetAggregate;
    }

    @SuppressWarnings("unchecked")
    private <DTO, TYPE extends Type> Assembler<AggregateRoot<?>, DTO, TYPE> findAssembler(Class<?> entityClass, Class<?> dtoClass) {

        if (assemblers == null) {
            initAssemblers();
        }

        Assembler<AggregateRoot<?>, DTO, TYPE> assembler = null;

        for (Assembler<AggregateRoot<?>, DTO, TYPE> loopAssembler : assemblers.values()) {
            TYPE entityType = loopAssembler.getAggregateClass();

            boolean entities = entityType.getClass() == Class.class && entityType == entityClass;
            boolean tuples = entityType.getClass() == TupleType.class
                    && TupleType.class.cast(entityType).rawType == entityClass
                    && ((TupleType) entityType).aggregateRootTypes.length == entityClass.getGenericInterfaces().length;

            if ((entities || tuples) && loopAssembler.getDtoClass() == dtoClass) {
                if (assembler != null) {
                    throw SeedException.createNew(AssemblerErrorCodes.MORE_THAN_ONE_ASSEMBLER_FOUND)
                            .put(ENTITY_CLASS, entityClass.getSimpleName())
                            .put(DTO_CLASS, dtoClass.getSimpleName())
                            .put(ASSEMBLER_1, assembler.getClass().getSimpleName())
                            .put(ASSEMBLER_2, loopAssembler.getClass().getSimpleName());
                }
                assembler = loopAssembler;
            }
        }

        return assembler;
    }

    private void initAssemblers() {
        assemblers = Maps.newHashMap();

        for (Class<?> aClass : assemblersClasses) {
            Assembler<?, ?, ?> assembler = (Assembler<?, ?, ?>) injector.getInstance(aClass);
            assemblers.put(aClass, assembler);
        }
    }

    private <KEY> KEY newValueObjectFromConstructor(DtoGateway id, Class<KEY> class1) {
        return newValueObjectFromConstructor(id, class1, -1);
    }

    @SuppressWarnings("unchecked")
    private <KEY> KEY newValueObjectFromConstructor(DtoGateway id, Class<KEY> class1, int typeIndex) {

        Constructor<?>[] constructors = class1.getConstructors();

        Constructor<?> constructor = null;

        for (Constructor<?> constructorInLoop : constructors) {
            // normally only one
            if (isMatching(constructorInLoop, id, typeIndex)) {
                constructor = constructorInLoop;
                break;
            }
        }

        // Check That id is non empty
        SeedException.createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
                .put(MESSAGE, "[Representation/DTO]  @" + MatchingEntityId.class.getSimpleName() + " are not well configured and does not match targetClass constructor.")
                .put(TARGET_CLASS, class1)
                .put(REPRESENTATION_CLASS, id.getTargetClass())
                .put(REPRESENTATION_GATEWAY, id)
                .throwsIf(constructor == null && id.size() == 0); //NOSONAR - no isEmpty() method available

        KEY vo = null;

        try {
            if (typeIndex < 0) {
                // if tuple
                vo = (KEY) newInstance(constructor, id);
            } else {
                vo = (KEY) newInstance(constructor, id, typeIndex);
            }
        } catch (Exception e) {
            SeedException.wrap(e, AssemblerErrorCodes.INSTANCE_CREATION_ISSUE)
                    .put(CLASS_NAME, class1.getName())
                    .thenThrows();
        }

        return vo;
    }

    private Object newInstance(Constructor<?> constructor, DtoGateway id) {
        return Reflection.constructor().withParameterTypes(constructor.getParameterTypes())
                .in(constructor.getDeclaringClass())
                .newInstance(id.sortedInstances(constructor.getParameterTypes()));
    }

    private Object newInstance(Constructor<?> constructor, DtoGateway id, int typeIndex) {
        return Reflection.constructor().withParameterTypes(constructor.getParameterTypes())
                .in(constructor.getDeclaringClass())
                .newInstance(id.sortedInstances(constructor.getParameterTypes(), typeIndex));
    }

    @SuppressWarnings("unchecked")
    @Hack("THe code is working, only because args are objects")
    private <PRODUCEDCLASS /*extends DomainObject & Creatable*/> PRODUCEDCLASS newInstance(Method method, DtoGateway id, /*GenericFactory<PRODUCEDCLASS>*/ Object factory) {
        PRODUCEDCLASS invoke = null;

        invoke = (PRODUCEDCLASS) Reflection.method(method.getName())
                .withParameterTypes(method.getParameterTypes())

                .in(factory)
                .invoke(id.sortedInstances(method.getParameterTypes()));

        return invoke;
    }

    /**
     * Method isMatching.
     *
     * @param constructorOrMethod AccessibleObject
     * @param dtoGateway          DtoGateway
     * @return boolean
     */
    private boolean isMatching(AccessibleObject constructorOrMethod, DtoGateway dtoGateway) {
        return isMatching(constructorOrMethod, dtoGateway, -1);
    }

    /**
     * Method isMatching.
     *
     * @param constructorOrMethod AccessibleObject
     * @param dtoGateway          DtoGateway
     * @return boolean
     */
    private boolean isMatching(AccessibleObject constructorOrMethod, DtoGateway dtoGateway, int typeIndex) {

        Class<?>[] parameterTypes = Reflection.method("getParameterTypes").withReturnType(Class[].class).in(constructorOrMethod).invoke();

        if (parameterTypes.length != dtoGateway.size(typeIndex)) {
            return false;
        }

        dtoGateway.throwsExceptionIfHaveDuplicated(typeIndex);

        // if no duplicate type, set based equality.
        if (!dtoGateway.hasDuplicateTypes(typeIndex)) {
            for (Class<?> parameterType : parameterTypes) {
                if (!dtoGateway.isPresent(parameterType, typeIndex)) {
                    return false;
                }
            }
        } else {
            // Check that representation is with index attribute
            for (int i = 0; i < parameterTypes.length; i++) {
                @SuppressWarnings("rawtypes")
                Quintet<Object, Class, Integer, Integer, Method> quintet = dtoGateway.getBySortNumber(i, typeIndex);

                if (quintet != null) {
                    if (!parameterTypes[i].equals(quintet.getValue1())) {
                        // no matching information from dto
                        return false;
                    }
                } else {
                    // no representation from DTO
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Method findInfosFromDto.
     *
     * @param sourceDto       DTO
     * @param annotationClass Class<? extends Annotation>
     * @return DtoGateway
     */
    private <DTO> DtoGateway findInfosFromDto(DTO sourceDto, Class<? extends Annotation> annotationClass) {
        Object instanceFragment;
        // IDVS-6630 : we allow methods from parent DTo
        Method[] methods = sourceDto.getClass().getMethods();

        DtoGateway dtoGateway = new DtoGateway(sourceDto.getClass());

        for (Method method : methods) {
            Annotation anno;
            if ((anno = method.getAnnotation(annotationClass)) != null) {
                try {
                    int index = fieldIndexFromAnno(anno);
                    int typeIndex = typeIndexFromAnno(anno);
                    instanceFragment = method.invoke(sourceDto);
                    dtoGateway.add(instanceFragment, method.getReturnType(), index, typeIndex, method);
                } catch (Exception e) {
                    SeedException.
                            wrap(e, AssemblerErrorCodes.METHOD_INVOCATION_ISSUE).put(METHOD_NAME, method.getName()).thenThrows();
                }
            }
        }

        return dtoGateway;
    }

    private int fieldIndexFromAnno(Annotation anno) {
        return Reflection.method("index").withReturnType(int.class).withParameterTypes().in(anno).invoke();
    }

    private int typeIndexFromAnno(Annotation anno) {
        return Reflection.method("typeIndex").withReturnType(int.class).withParameterTypes().in(anno).invoke();
    }

    /**
     * Will create a new Entity from DtoGateway.
     *
     * @param <AGGREGATEROOT>
     * @param factoryGateway
     * @param entityClass
     * @return the newly created entity.
     */
    protected <AGGREGATEROOT extends AggregateRoot<?>> AGGREGATEROOT createNewAggregate(DtoGateway factoryGateway, Class<AGGREGATEROOT> entityClass) {

        GenericFactory<AGGREGATEROOT> genericFactory = factories.get(entityClass);

        AGGREGATEROOT targetAggreg8 = null;

        if (genericFactory != null) {
            @SuppressWarnings("rawtypes")
            Class<? extends GenericFactory> factoryClass = genericFactory.getClass(); // TODO BUG ?

            if (Factory.class.isAssignableFrom(factoryClass)) {
                targetAggreg8 = createAggregateFromDefaultFactory(factoryGateway, -1, entityClass, genericFactory, factoryClass);
            } else {
                targetAggreg8 = createEntityFromUserFactory(factoryGateway, entityClass, genericFactory, factoryClass);
            }
        } else {
            SeedException.createNew(AssemblerErrorCodes.FACTORY_NOT_FOUND_FOR_AGGREGATE)
                    .put(AGGREGATE_ROOT_CLASS, entityClass)
                    .thenThrows();
        }

        return targetAggreg8;
    }


    @SuppressWarnings({"rawtypes"})
    protected <AGGREGATEROOT extends AggregateRoot<?>> AGGREGATEROOT createEntityFromUserFactory(DtoGateway factoryGateway, Class<AGGREGATEROOT> entityClass,
                                                                                                 GenericFactory<AGGREGATEROOT> genericFactory, Class<? extends GenericFactory> factoryClass) {
        AGGREGATEROOT targetAggreg8;
        Method factorMethod = null;

        for (Method method : factoryClass.getMethods()) {
            if (method.getReturnType().isAssignableFrom(entityClass) && isMatching(method, factoryGateway)) {
                factorMethod = method;
                break;
            }
        }
        // Check That id is non empty
        SeedException.createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
                .put(MESSAGE, String.format("[Representation/DTO]  @%s are not well configured and does not match factory methods.", MatchingFactoryParameter.class.getSimpleName()))
                .put(TARGET_CLASS, SeedReflectionUtils.cleanProxy(factoryClass))
                .put(REPRESENTATION_CLASS, factoryGateway.getTargetClass())
                .put(REPRESENTATION_GATEWAY, factoryGateway)
                .throwsIf(factorMethod == null);

        targetAggreg8 = newInstance(factorMethod, factoryGateway, genericFactory);
        return targetAggreg8;
    }

    @SuppressWarnings({"rawtypes"})
    protected <AGGREGATEROOT extends AggregateRoot<?>> AGGREGATEROOT createAggregateFromDefaultFactory(DtoGateway factoryGateway, Integer typeIndex, Class<AGGREGATEROOT> entityClass,
                                                                                                       GenericFactory<AGGREGATEROOT> genericFactory, Class<? extends GenericFactory> factoryClass) {
        AGGREGATEROOT targetAggreg8 = null;
        Factory<AGGREGATEROOT> factory = (Factory<AGGREGATEROOT>) genericFactory;
        targetAggreg8 = factory.create(factoryGateway.getParametersByTypeIndex(typeIndex));
        return targetAggreg8;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <TUPLE_OF_AGROOTS extends Tuple> TUPLE_OF_AGROOTS createNewEntities(DtoGateway factoryGateway, TypeLiteral<TUPLE_OF_AGROOTS> entitiesType) {
        Object targetAggregates;
        List<Object> aggregateRoots = Lists.newArrayList();
        List<Class<? extends AggregateRoot<?>>> aggregateRootsClasses = aggregateRootsClassesFromTupleType(entitiesType);

        int counter = -1;
        for (Class<? extends AggregateRoot<?>> aggregateRootsClass : aggregateRootsClasses) {
            counter++;
            GenericFactory<? extends AggregateRoot> genericFactory = factories.get(aggregateRootsClass);
            AggregateRoot<?> targetAggreg8;

            if (genericFactory != null) {
                Class<? extends GenericFactory> factoryClass = (Class<? extends GenericFactory>) SeedReflectionUtils.cleanProxy(genericFactory.getClass());

                Method factorMethod = null;

                for (Method method : factoryClass.getDeclaredMethods()) {
                    if (method.getReturnType().equals(aggregateRootsClass) && isMatching(method, factoryGateway, counter)) {
                        factorMethod = method;
                        break;
                    }
                }

                // Check That id is non empty
                SeedException
                        .createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
                        .put(MESSAGE, "[Representation/DTO]  @" + MatchingFactoryParameter.class.getSimpleName() + " are not well configured and does not match factory methods.")
                        .put(TARGET_CLASS, genericFactory)
                        .put(REPRESENTATION_CLASS, factoryGateway.getTargetClass())
                        .put(REPRESENTATION_GATEWAY, factoryGateway)
                        .throwsIf(factorMethod == null);

                if (Factory.class.isAssignableFrom(factoryClass)) {
                    targetAggreg8 = createAggregateFromDefaultFactory(factoryGateway, counter, (Class) aggregateRootsClass, genericFactory, factoryClass);
                } else {
                    targetAggreg8 = newInstance(factorMethod, factoryGateway, genericFactory);
                }

                aggregateRoots.add(targetAggreg8);
            } else {
                SeedException.createNew(AssemblerErrorCodes.FACTORY_NOT_FOUND_FOR_AGGREGATE)
                        .put(AGGREGATE_ROOT_CLASS, aggregateRootsClass)
                        .thenThrows();
            }
        }

        // convert in tuple
        targetAggregates = createTupleFromList(aggregateRoots);

        return (TUPLE_OF_AGROOTS) targetAggregates;
    }

    @SuppressWarnings({"unchecked"})
    protected <AGGREGATE extends AggregateRoot<KEY>, KEY> AGGREGATE findEntity(DtoGateway id, Class<AGGREGATE> entityClass) {

        AGGREGATE targetAggregate = null;

        // Get the right repository
        Repository<AGGREGATE, KEY> repository = repositories.get(entityClass);

        Class<KEY> keyClass;

        if (repository != null) {
            keyClass = repository.getKeyClass();

            if (ValueObject.class.isAssignableFrom(keyClass)) {

                KEY newInstance = newValueObjectFromConstructor(id, keyClass);
                targetAggregate = repository.load(newInstance);

            } else {
                // if entity id is just a normal type.

                @SuppressWarnings("rawtypes")
                Quintet<KEY, Class, Integer, Integer, Method> quartet = id.get(0);

                KEY oo = quartet.getValue0();

                targetAggregate = repository.load(oo);
            }
        } else {
            SeedException.createNew(AssemblerErrorCodes.REPOSITORY_NOT_FOUND_FOR_AGGREGATE)
                    .put(AGGREGATE_ROOT_CLASS, entityClass)
                    .thenThrows();
        }
        return targetAggregate;
    }

    /**
     * Method findEntities.
     *
     * @param <TUPLE_OF_AGROOTS>
     * @param id                 DtoGateway
     * @param entitiesType       Class<ENTITY>
     * @return ENTITY
     */
    @SuppressWarnings("unchecked")
    protected <TUPLE_OF_AGROOTS extends Tuple> TUPLE_OF_AGROOTS
    findEntities(DtoGateway id, TypeLiteral<TUPLE_OF_AGROOTS> entitiesType) {

        Object targetAggregates;

        List<Object> aggregateRoots = Lists.newArrayList();

        List<Class<? extends AggregateRoot<?>>> aggregateRootsClasses = aggregateRootsClassesFromTupleType(entitiesType);

        Class<?> tupleType = (Class<?>) ((ParameterizedType) entitiesType.getType()).getRawType();

        int counter = -1;
        for (Class<? extends AggregateRoot<?>> aggregateRootsClass : aggregateRootsClasses) {
            counter++;
            AggregateRoot<?> targetAggregate = null;


            // // Get the right repository
            Repository<AggregateRoot<Object>, Object> repository = repositories.get(aggregateRootsClass);

            Class<?> keyClass;

            if (repository != null) {
                keyClass = repository.getKeyClass();

                if (ValueObject.class.isAssignableFrom(keyClass)) {
                    Object newKeyInstance = newValueObjectFromConstructor(id, keyClass, counter);
                    targetAggregate = repository.load(newKeyInstance);
                } else {
                    // if entity id is just a normal type.
//					@SuppressWarnings("rawtypes")
//					Quintet<Object, Class, Integer, Integer, Method> quartet = id.getInstanceByTypeIndex(0,counter);

                    Object oo = id.getInstanceByTypeIndex(0, counter);

                    targetAggregate = repository.load(oo);
                }
            } else {
                SeedException
                        .createNew(AssemblerErrorCodes.REPOSITORY_NOT_FOUND_FOR_AGGREGATE)
                        .put(AGGREGATE_ROOT_CLASS, aggregateRootsClass)
                        .put(OF_TUPLE_CLASS, tupleType)
                        .thenThrows();
            }
            aggregateRoots.add(targetAggregate);
        }

        targetAggregates = createTupleFromList(aggregateRoots);

        // Creating the tuple and update it from the list

        return (TUPLE_OF_AGROOTS) targetAggregates;
    }

    @SuppressWarnings({"unchecked"})
    protected <TUPLE_OF_AGROOTS extends Tuple>
    List<Class<? extends AggregateRoot<?>>> aggregateRootsClassesFromTupleType(TypeLiteral<TUPLE_OF_AGROOTS> tupleType) {

        List<Class<? extends AggregateRoot<?>>> list = Lists.newArrayList();

        Type[] actualTypeArguments = ((ParameterizedType) tupleType.getType()).getActualTypeArguments();

        for (Type type : actualTypeArguments) {
            list.add((Class<? extends AggregateRoot<?>>) type);
        }
        return list;
    }
}