/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;

import com.google.inject.TypeLiteral;
import org.seedstack.business.api.domain.AggregateRoot;
import org.javatuples.Tuple;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This class centralizes all {@link Assembler} in the application. SEED
 * Business Framework will fill it the right way.
 * <p/>
 * It can be obtained using the following:
 * <pre>
 *     {@literal @}Inject
 *     Assemblers assemblers;
 * </pre>
 * On top of aggregating all the {@link Assembler}S of the application, it
 * offers more value with services like:
 * <ul>
 * <li>automatic aggregate loading from appropriate {@link org.seedstack.business.api.domain.Repository}</li>
 * <li>automatic aggregate creation from appropriate {@link org.seedstack.business.api.domain.GenericFactory}</li>
 * <li>a combination of both</li>
 * </ul>
 * Public method will describes the possibilities.
 *
 * @author epo.jemba@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 21/08/2014
 */
public interface Assemblers {

    /**
     * This method will assemble a DTO from the entity.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate(Object)}.
     *
     * @param targetDtoClass Class<DTO> the Type of DTO to be assembled.
     * @param sourceEntity   Entity  the entity from which fill the DTO
     * @return the DTO assembled with the right assembler.
     * @deprecated Replaced by {@link Assemblers#assembleDtoFromAggregate(Class, org.seedstack.business.api.domain.AggregateRoot)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> DTO assembleDtoFromEntity(Class<DTO> targetDtoClass, AGGREGATE_ROOT sourceEntity);

    /**
     * This method will assemble a DTO from the given aggregate.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate}.
     *
     * @param targetDtoClass  The Class<DTO> the Type of DTO to be assembled.
     * @param sourceAggregate The aggregate from which fill the DTO
     * @return the DTO assembled with the right assembler.
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> DTO assembleDtoFromAggregate(Class<DTO> targetDtoClass, AGGREGATE_ROOT sourceAggregate);

    /**
     * This method will assemble a DTO from the entity.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate(Object)}.
     *
     * @param targetDtoClass The type of DTO to be assembled.
     * @param sourceEntity   The entity from which fill the DTO
     * @return the DTO assembled with the right assembler.
     * @deprecated Replaced by {@link Assemblers#assembleDtoFromAggregate(Class, java.util.List)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<DTO> assembleDtoFromEntity(Class<DTO> targetDtoClass, List<AGGREGATE_ROOT> sourceEntity);

    /**
     * This method will assemble a DTO from the aggregate.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate}.
     *
     * @param targetDtoClass  The type of DTO to be assembled.
     * @param sourceAggregate The aggregate from which fill the DTO
     * @return the DTO assembled with the right assembler.
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> List<DTO> assembleDtoFromAggregate(Class<DTO> targetDtoClass, List<AGGREGATE_ROOT> sourceAggregate);

    /**
     * This method will assemble a DTO from n entities.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate(Object)}.
     *
     * @param targetDtoClass The type of DTO to be assembled
     * @param sourceEntities The entity from which fill the DTO
     * @return the DTO assembled with the right assembler.
     * @deprecated Replaced by {@link Assemblers#assembleDtoFromAggregates(Class, java.util.List)}
     */
    @Deprecated
    <TUPLE_OF_AGROOT extends Tuple, DTO> List<DTO> assembleDtoFromEntities(Class<DTO> targetDtoClass, List<TUPLE_OF_AGROOT> sourceEntities);

    /**
     * This method will assemble a DTO from n aggregates.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate}.
     *
     * @param targetDtoClass   The type of DTO to be assembled
     * @param sourceAggregates The entity from which fill the DTO
     * @return the DTO assembled with the right assembler
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOT extends Tuple, DTO> List<DTO> assembleDtoFromAggregates(Class<DTO> targetDtoClass, List<TUPLE_OF_AGROOT> sourceAggregates);

    /**
     * This method will assemble a DTO from n entities.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate(Object)}.
     *
     * @param targetDtoClass Class<DTO> the Type of DTO to be assembled
     * @param sourceEntities Entity  the entity from which fill the DTO
     * @return the DTO assembled with the right assembler
     * @deprecated Replaced by {@link Assemblers#assembleDtoFromAggregates(Class, org.javatuples.Tuple)}
     */
    @Deprecated
    <TUPLE_OF_AGROOT extends Tuple, AGGREGATE_ROOT extends AggregateRoot<?>, TYPE extends Type, DTO>
    DTO assembleDtoFromEntities(Class<DTO> targetDtoClass, TUPLE_OF_AGROOT sourceEntities);

    /**
     * This method will assemble a DTO from n entities.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and call its method
     * {@link Assembler#assembleDtoFromAggregate}.
     *
     * @param targetDtoClass   The type of DTO to be assembled
     * @param sourceAggregates The entity from which fill the DTO
     * @return the DTO assembled with the right assembler
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOT extends Tuple, AGGREGATE_ROOT extends AggregateRoot<?>, TYPE extends Type, DTO>
    DTO assembleDtoFromAggregates(Class<DTO> targetDtoClass, TUPLE_OF_AGROOT sourceAggregates);

    /**
     * This method will merge the specified DTO with the specified entity.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and <br>
     * call its method {@link Assembler#mergeAggregateWithDto(Object, Object)}.
     *
     * @param sourceDto        DTO  the DTO from which merge the assembler.
     * @param targetEntity     Entity the entity to be merged with the DTO.
     * @param <AGGREGATE_ROOT> the aggregate type
     * @param <KEY>            the aggregate key type
     * @param <DTO>            the representation type
     * @param <TYPE>           the aggregate root class type
     * @deprecated Replaced by {@link Assemblers#mergeAggregateWithDto(Object, org.seedstack.business.api.domain.AggregateRoot)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO, TYPE extends Type> void mergeEntityWithDto(DTO sourceDto, AGGREGATE_ROOT targetEntity);

    /**
     * This method will merge the specified DTO with the specified entity.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and <br>
     * call its method {@link Assembler#mergeAggregateWithDto}.
     *
     * @param sourceDto       the dto from which merge the assembler.
     * @param targetAggregate the aggregate to be merged with the DTO.
     * @param <AGGREGATE_ROOT> the aggregate type
     * @param <KEY>            the aggregate key type
     * @param <DTO>            the representation type
     * @param <TYPE>           the aggregate root class type
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO, TYPE extends Type> void mergeAggregateWithDto(DTO sourceDto, AGGREGATE_ROOT targetAggregate);

    /**
     * This method will merge the specified DTO with the specified entity.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and <br>
     * call its method {@link Assembler#mergeAggregateWithDto(Object, Object)}.
     *
     * @param sourceDto        DTO  the DTO from which merge the assembler.
     * @param targetEntities   Entity the entity to be merged with the DTO.
     * @param <DTO>            the representation type
     * @deprecated Replaced by {@link Assemblers#mergeAggregatesWithDto(Object, org.javatuples.Tuple)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    void mergeEntitiesWithDto(DTO sourceDto, TUPLE_OF_AGROOTS targetEntities);

    /**
     * This method will merge the specified DTO with the specified aggregate.
     * <p/>
     * It will first find the right {@link Assembler} from the list of assemblers and <br>
     * call its method {@link Assembler#mergeAggregateWithDto}.
     *
     * @param sourceDto        the dto from which merge the assembler.
     * @param targetAggregates the aggregate to be merged with the DTO.
     * @param <DTO>            the representation type
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    void mergeAggregatesWithDto(DTO sourceDto, TUPLE_OF_AGROOTS targetAggregates);

    /**
     * This methods will return an aggregate root created from factory and then merged with the given DTO.
     * <p/>
     * It won't try to find it from the repository. So if the entity already exists in the persistence any commit
     * will override or return error.
     *
     * @param sourceDto         dto to merge
     * @param targetEntityClass the type of aggregate root targeted.
     * @return the aggregate root created and merged
     * @deprecated Replaced by {@link Assemblers#createThenMergeAggregateWithDto(Object, Class)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    AGGREGATE_ROOT createThenMergeEntityWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetEntityClass);

    /**
     * This methods will return an aggregate root created from factory and then merged with the given DTO.
     * <p/>
     * It won't try to find it from the repository. So if the entity already exists in the persistence any commit
     * will override or return error.
     *
     * @param sourceDto            the dto to merge
     * @param targetAggregateClass the type of aggregate root targeted.
     * @return the aggregate root created and merged
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    AGGREGATE_ROOT createThenMergeAggregateWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass);

    /**
     * This methods will return a list of aggregate root created from factory and then merged with the given DTO.
     * <p/>
     * It won't try to find it from the repository. So if the entity already exists in the persistence any commit
     * will override or return error.
     *
     * @param sourceDtos        dto to merge
     * @param targetEntityClass the targeted aggregate root
     * @param <AGGREGATE_ROOT>  the targeted aggregate root type
     * @param <KEY>             aggregate root key type
     * @param <DTO>             DTO type
     * @return list of aggregate root
     * @deprecated Replaced by {@link Assemblers#createThenMergeAggregateWithDto(java.util.List, Class)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> createThenMergeEntityWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetEntityClass);

    /**
     * This methods will return a list of aggregate root created from factory and then merged with the given DTO.
     * <p/>
     * It won't try to find it from the repository. So if the entity already exists in the persistence any commit
     * will override or return error.
     *
     * @param sourceDtos           the dto to merge
     * @param targetAggregateClass the targeted aggregate root
     * @param <AGGREGATE_ROOT>     the targeted aggregate root type
     * @param <KEY>                the aggregate root key type
     * @param <DTO>                the DTO type
     * @return the list of aggregate root
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> createThenMergeAggregateWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetAggregateClass);

    /**
     * This methods will return a tuple of aggregate root created from factory and then merged with the given DTO.
     * <p/>
     * It won't try to find it from the repository. So if the entities already exists in the persistence any commit
     * will override or return error.
     *
     * @param sourceDto         the dto to merge
     * @param targetEntityClass the target aggregate root
     * @return the tuple of aggregate root created and merged
     * @deprecated Replaced by {@link Assemblers#createThenMergeAggregatesWithDto(Object, com.google.inject.TypeLiteral)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    TUPLE_OF_AGROOTS createThenMergeEntitiesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass);

    /**
     * This methods will return a tuple of aggregate root created from factory and then merged with the given DTO.
     * <p/>
     * It won't try to find it from the repository. So if the entities already exists in the persistence any commit
     * will override or return error.
     *
     * @param sourceDto            the dto to merge
     * @param targetAggregateClass the target aggregate root
     * @return the tuple of aggregate root created and merged
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    TUPLE_OF_AGROOTS createThenMergeAggregatesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClass);

    /**
     * This methods will return merged Entities only if created from factory. It won't try to find it from the repository.
     * So if the entities already exists in the persistence any commit will override or return error.
     * <ol>
     * <li> It "tries" to retrieve the entities from the tuple targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDtos        the dto that came from interface.
     * @param targetEntityClass the type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto or null if none was retrieved
     * @deprecated Replaced by {@link Assemblers#createThenMergeAggregatesWithDto(Object, com.google.inject.TypeLiteral)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> createThenMergeEntitiesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass);

    /**
     * This methods will return merged Entities only if created from factory. It won't try to find it from the repository.
     * So if the entities already exists in the persistence any commit will override or return error.
     * <ol>
     * <li> It "tries" to retrieve the entities from the tuple targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDtos           the dto that came from interface.
     * @param targetAggregateClass the type of aggregate targeted.
     * @return the entity retrieved from repository and merged with sourceDto or null if none was retrieved
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> createThenMergeAggregatesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClass);

    /**
     * This methods will return a merged Entity only loaded from repository. It won't create a new one.
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDto         The dto that came from interface.
     * @param targetEntityClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto or null if none was retrieved.
     * @deprecated Replaced by {@link Assemblers#retrieveThenMergeAggregateWithDto(Object, Class)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    AGGREGATE_ROOT retrieveThenMergeEntityWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetEntityClass);

    /**
     * This methods will return a merged aggregate loaded from repository. It won't create a new one.
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the aggregate is merged with sourceDto.
     *
     * @param sourceDto            The dto that came from interface.
     * @param targetAggregateClass The type of aggregate targeted.
     * @return the aggregate retrieved from repository and merged with sourceDto or null if none was retrieved
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    AGGREGATE_ROOT retrieveThenMergeAggregateWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass);

    /**
     * This methods will return a merged Entity only loaded from repository. It won't create a new one.
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDtos        The dto that came from interface.
     * @param targetEntityClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto or null if none was retrieved
     * @deprecated Replaced by {@link Assemblers#retrieveThenMergeAggregateWithDto(java.util.List, Class)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> retrieveThenMergeEntityWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetEntityClass);

    /**
     * This methods will return a list of merged aggregate loaded from repository. It won't create a new ones.
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the aggregate is merged with sourceDto.
     *
     * @param sourceDtos           The dto that came from interface.
     * @param targetAggregateClass The type of aggregate targeted.
     * @return the list of aggregate retrieved from repository and merged with sourceDto or null if none was retrieved.
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> retrieveThenMergeAggregateWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetAggregateClass);

    /**
     * This methods will return merged Entities only loaded from repository. It won't create a new one.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDto           The dto that came from interface.
     * @param targetEntitiesClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto or null if none was retrieved
     * @deprecated Replaced by {@link Assemblers#retrieveThenMergeAggregatesWithDto(Object, com.google.inject.TypeLiteral)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveThenMergeEntitiesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetEntitiesClass);

    /**
     * This methods will return the merged aggregates loaded from repository. It won't create a new one.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDto              The dto that came from interface.
     * @param targetAggregateClasses The types of aggregate targeted.
     * @return the aggregates retrieved from repository and merged with sourceDto or null if none was retrieved.
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveThenMergeAggregatesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClasses);

    /**
     * This methods will return merged Entities only loaded from repository. It won't create a new one.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the entity is merged with sourceDto.
     *
     * @param sourceDtos          The dto that came from interface.
     * @param targetEntitiesClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto or null if none was retrieved.
     * @deprecated Replaced by {@link Assemblers#retrieveThenMergeAggregatesWithDto(java.util.List, com.google.inject.TypeLiteral)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> retrieveThenMergeEntitiesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetEntitiesClass);

    /**
     * This methods will return merged aggregates loaded from repository. It won't create a new one.
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the
     * information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.
     * </ol>
     * <p/>
     * When retrieved the aggregate is merged with sourceDto.
     *
     * @param sourceDtos            The dto that came from interface.
     * @param targetAggregatesClass The type of entity targeted.
     * @return the aggregate retrieved from repository and merged with sourceDto or null if none was retrieved.
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> retrieveThenMergeAggregatesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregatesClass);

    /**
     * This methods will return a merged Entity loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the entity is then merged with sourceDto.
     *
     * @param sourceDto         The dto that came from interface.
     * @param targetEntityClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if  <br>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for target entity <br>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for target entity
     * @deprecated Replaced by {@link Assemblers#retrieveOrCreateThenMergeAggregateWithDto(Object, Class)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> AGGREGATE_ROOT retrieveOrCreateThenMergeEntityWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetEntityClass);

    /**
     * This methods will return a merged aggregate loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the aggregate is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the aggregate is then merged with sourceDto.
     *
     * @param sourceDto            The dto that came from interface.
     * @param targetAggregateClass The type of entity targeted.
     * @return the aggregate retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if <br/>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetAggregateClass<br/>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for the targeted aggregate <br/>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for the targeted aggregate
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO> AGGREGATE_ROOT retrieveOrCreateThenMergeAggregateWithDto(DTO sourceDto, Class<AGGREGATE_ROOT> targetAggregateClass);

    /**
     * This methods will return a merged Entity loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the entity is then merged with sourceDto.
     *
     * @param sourceDtos        The list of source DTOs.
     * @param targetEntityClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if  <br>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for target entity <br>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for target entity
     * @deprecated Replaced by {@link Assemblers#retrieveOrCreateThenMergeAggregateWithDto(Object, Class)}
     */
    @Deprecated
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> retrieveOrCreateThenMergeEntityWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetEntityClass);

    /**
     * This methods will return a merged aggregate loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the aggregate is then merged with sourceDto.
     *
     * @param sourceDtos           The list of source DTOs.
     * @param targetAggregateClass The type of aggregate targeted.
     * @return the aggregate retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if  <br>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br/>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for targeted aggregate <br/>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for targeted aggregate
     * @since 1.4.0
     */
    <AGGREGATE_ROOT extends AggregateRoot<KEY>, KEY, DTO>
    List<AGGREGATE_ROOT> retrieveOrCreateThenMergeAggregateWithDto(List<DTO> sourceDtos, Class<AGGREGATE_ROOT> targetAggregateClass);

    /**
     * This methods will return a merged Entity loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the entity is then merged with sourceDto.
     *
     * @param sourceDto         The dto that came from interface.
     * @param targetEntityClass The type of entity targeted.
     * @return the entity retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if  <br>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for target entity <br>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for target entity
     * @deprecated Replaced by {@link Assemblers#retrieveOrCreateThenMergeAggregatesWithDto(Object, com.google.inject.TypeLiteral)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveOrCreateThenMergeEntitiesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass);

    /**
     * This methods will return a merged Entity loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the entity is then merged with sourceDto.
     *
     * @param sourceDto              The dto that came from interface.
     * @param targetAggregateClasses The tuple of aggregate type targeted.
     * @return the aggregate retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if  <br>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for target entity <br>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for target entity
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO> TUPLE_OF_AGROOTS retrieveOrCreateThenMergeAggregatesWithDto(DTO sourceDto, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClasses);

    /**
     * This methods will return a merged Entity loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the entity is then merged with sourceDto.
     *
     * @param sourceDtos        the list of source DTOs
     * @param targetEntityClass the type of entity targeted
     * @return the entity retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if  <br>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for target entity <br>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for target entity
     * @deprecated Replaced by {@link Assemblers#retrieveOrCreateThenMergeAggregatesWithDto(java.util.List, com.google.inject.TypeLiteral)}
     */
    @Deprecated
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> retrieveOrCreateThenMergeEntitiesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetEntityClass);

    /**
     * This methods will return a merged aggregate loaded from repository or created by a factory respectively.
     * <p/>
     * <ol>
     * <li> It "tries" to retrieve the entity of type targetEntityClass from the information inside sourceDto annotated with {@link MatchingEntityId}.
     * It retrieves the Entity thanks to its associated {@link org.seedstack.business.api.domain.Repository}.<br><br>
     * <p/>
     * <li> If the entity is not retrieved, then this method use a descendant of {@link org.seedstack.business.api.domain.GenericFactory} to create a new entity thanks to a set {@link MatchingFactoryParameter}.
     * </ol>
     * When retrieved the entity is then merged with sourceDto.
     *
     * @param sourceDtos             the list of source DTOs
     * @param targetAggregateClasses the tuple of aggregate type targeted
     * @return the aggregate retrieved from repository and merged with sourceDto
     * @throws org.seedstack.seed.core.api.SeedException can be thrown if <br/>
     *                                                 1) no {@link Assembler} is found for sourceDto and targetEntity. <br/>
     *                                                 2) no descendant of {@link org.seedstack.business.api.domain.Repository} is found for target entity <br/>
     *                                                 3) no descendant of {@link org.seedstack.business.api.domain.GenericFactory} is found for target entity
     * @since 1.4.0
     */
    <TUPLE_OF_AGROOTS extends Tuple, DTO>
    List<TUPLE_OF_AGROOTS> retrieveOrCreateThenMergeAggregatesWithDto(List<DTO> sourceDtos, TypeLiteral<TUPLE_OF_AGROOTS> targetAggregateClasses);
}
