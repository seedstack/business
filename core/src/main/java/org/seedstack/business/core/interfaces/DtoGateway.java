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
import org.seedstack.business.api.interfaces.assembler.AssemblerErrorCodes;
import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.javatuples.Quintet;
import org.seedstack.seed.core.api.SeedException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Internal helper class that encapsulates id composite in dto.
 * 
 * @author epo.jemba@ext.mpsa.com
 */
class DtoGateway {

	private static final String MESSAGE = "message";
	private final List<Object> instances = Lists.newArrayList();
	@SuppressWarnings("rawtypes")
	private final List<Class> types = Lists.newArrayList();
	private final List<Integer> fieldIndices = Lists.newArrayList();
	private final List<Integer> typeIndices = Lists.newArrayList();
	private final List<Method> methods = Lists.newArrayList();

	private int count;

	@SuppressWarnings("rawtypes")
	private transient Map<Class, Integer> typesRepo = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private transient Map<Class, Object> instancesByTypes = Maps.newHashMap();

	// Tuple
	private Map<Integer, Integer> countByType = Maps.newConcurrentMap();
	@SuppressWarnings("rawtypes")
	private transient Map<Integer, Map<Class, Integer>> typesRepoByTypeIndex = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private transient Map<Integer, Map<Class, Object>> instancesByTypesByTypeIndex = Maps.newHashMap();
	private final Map<Integer, List<Integer>> fieldIndicesByTypeIndex = Maps.newHashMap();
	private final Map<Integer, List<Object>> instancesByTypeIndex = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private final Map<Integer, List<Class>> typesByTypeIndex = Maps.newHashMap();
	private final Map<Integer, SortedMap<Integer, Object>> parametersByTypeIndex = Maps.newHashMap();

	private static final Object NULL = new Object();

	private Class<?> targetClass;

	DtoGateway(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	void add(Object instance, Class<?> type, Integer fieldIndex, Integer typeIndex, Method method) {
		instances.add(instance);
		addType(type, typeIndex);
		fieldIndices.add(fieldIndex);
		typeIndices.add(typeIndex);
		methods.add(method);
		instancesByTypeIndex(typeIndex, instance);
		instancesByTypes.put(type, instance);
		instancesByTypesByTypeIndex(typeIndex, type, instance);
		addParametersByTypeIndex(typeIndex, instance, fieldIndex);
		count++;
		incrementCountByType(typeIndex);
		fieldIndicesByTypeIndex(typeIndex, fieldIndex);
	}

	private void addParametersByTypeIndex(Integer typeIndex, Object instance, Integer fieldIndex) {
		Map<Integer, Object> parameters = parametersByTypeIndex.get(typeIndex);
		if (parameters == null) {
			parametersByTypeIndex.put(typeIndex, new TreeMap<Integer, Object>());
			parameters = parametersByTypeIndex.get(typeIndex);
		}
		parameters.put(fieldIndex, instance);

	}

	Object[] getParametersByTypeIndex(Integer typeIndex) {
		if (parametersByTypeIndex.get(typeIndex) == null || parametersByTypeIndex.get(typeIndex).values() == null) {
			return null;
		}
		return parametersByTypeIndex.get(typeIndex).values().toArray();
	}

	private void instancesByTypeIndex(Integer typeIndex, Object instance) {
		List<Object> currentMap;

		if (!instancesByTypeIndex.containsKey(typeIndex)) {
			currentMap = Lists.newArrayList();
			instancesByTypeIndex.put(typeIndex, currentMap);
		} else {
			currentMap = instancesByTypeIndex.get(typeIndex);
		}
		currentMap.add(instance);

	}

	private void fieldIndicesByTypeIndex(Integer typeIndex, Integer fieldIndex) {
		List<Integer> currentMap;

		if (!fieldIndicesByTypeIndex.containsKey(typeIndex)) {
			currentMap = Lists.newArrayList();
			fieldIndicesByTypeIndex.put(typeIndex, currentMap);
		} else {
			currentMap = fieldIndicesByTypeIndex.get(typeIndex);
		}
		currentMap.add(fieldIndex);
	}

	int size() {
		return count;
	}

	int size(Integer typeIndex) {
		if (-1 == typeIndex) {
			return size();
		} else {
			int size = 0;

			if (countByType.containsKey(typeIndex)) {
				size = countByType.get(typeIndex);
			}

			return size;
		}
	}

	boolean hasDuplicateTypes() {
		for (Integer counts : typesRepo.values()) {
			if (counts > 1) {
				return true;
			}
		}
		return false;
	}

	boolean hasDuplicateTypes(Integer typeIndex) {
		if (-1 == typeIndex) {
			return hasDuplicateTypes();
		}

		for (Integer counts : getTypesRepoByTypeIndex(typeIndex).values()) {
			if (counts > 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * return the instances sorted for factory methods or value object constructor.
	 * 
	 * 
	 * @param parameterTypes
	 *            the parameter types
	 * @return array of object
	 */
	@SuppressWarnings("rawtypes")
	Object[] sortedInstances(Class[] parameterTypes) {

		List<Object> instancesSorted = Lists.newArrayList();
		if (!hasDuplicateTypes()) {
			for (Class parameterType : parameterTypes) {
				instancesSorted.add(getInstanceByType(parameterType));
			}
		} else {
			for (int i = 0; i < parameterTypes.length; i++) {
				instancesSorted.add(getBySortNumber(i).getValue0());
			}
		}
		return instancesSorted.toArray();
	}

	/**
	 * return the instances sorted for factory methods or value object constructor.
	 * 
	 * 
	 * @param parameterTypes
	 *            the parameter types
	 * @return array of object
	 */
	@SuppressWarnings("rawtypes")
	Object[] sortedInstances(Class[] parameterTypes, Integer typeIndex) {

		List<Object> instancesSorted = Lists.newArrayList();
		if (!hasDuplicateTypes(typeIndex)) {
			for (Class parameterType : parameterTypes) {
				instancesSorted.add(getInstanceByType(parameterType, typeIndex));
			}
		} else {
			for (int i = 0; i < parameterTypes.length; i++) {
				instancesSorted.add(getBySortNumber(i, typeIndex).getValue0());
			}
		}
		return instancesSorted.toArray();
	}

	/**
	 * this methods will throw an SeedException if the representation gateway have duplicate types and the dto of origin has no
	 * index > 0 on its annotated methods. {@link MatchingFactoryParameter} and {@link MatchingEntityId}
	 * 
	 */
	void throwsExceptionIfHaveDuplicated() {
		if (hasDuplicateTypes()) {
			List<String> incorrectMethods = Lists.newArrayList();
			Class<?> origClass = null;
			for (int i = 0; i < count; i++) {
				@SuppressWarnings("rawtypes")
				Quintet<Object, Class, Integer, Integer, Method> quartet = get(i);
				if (quartet.getValue2() == -1) {
					origClass = quartet.getValue4().getDeclaringClass();
					incorrectMethods.add(quartet.getValue4().getName());
				}
			}
			SeedException
					.createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
					.put(MESSAGE,
							"No attribute index for @" + MatchingEntityId.class.getSimpleName() + " or @"
									+ MatchingFactoryParameter.class.getSimpleName()).put("Class Name", origClass)
					.put("Incorrect Methods", incorrectMethods).throwsIf(!incorrectMethods.isEmpty());
		}

	}

	/**
	 * Throws an SeedException if the representation gateway have duplicate types and the dto of origin has no
	 * index > 0 on its annotated methods. {@link MatchingFactoryParameter} and {@link MatchingEntityId}
	 * 
	 */
	void throwsExceptionIfHaveDuplicated(int typeIndex) {
		if (hasDuplicateTypes()) {
			List<String> incorrectMethods = Lists.newArrayList();
			Class<?> origClass = null;
			for (int i = 0; i < count; i++) {
				@SuppressWarnings("rawtypes")
				Quintet<Object, Class, Integer, Integer, Method> quartet = get(i);
				if (quartet.getValue2() == -1) {
					origClass = quartet.getValue4().getDeclaringClass();
					incorrectMethods.add(quartet.getValue4().getName());
				}
			}
			SeedException
					.createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
					.put(MESSAGE,
							"No attribute index for @" + MatchingEntityId.class.getSimpleName() + " or @"
									+ MatchingFactoryParameter.class.getSimpleName()).put("Class Name", origClass)
					.put("Incorrect Methods", incorrectMethods).throwsIf(!incorrectMethods.isEmpty());
		}

	}

	void throwsExceptionIfNeeded() {
		throwsExceptionIfHaveDuplicated();

		SeedException
				.createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
				.put(MESSAGE,
						"No attribute annotation @" + MatchingEntityId.class.getSimpleName() + " or @"
								+ MatchingFactoryParameter.class.getSimpleName() + " were found on DTO")
				.put("Class Name", targetClass).throwsIf(count == 0);
	}

	void throwsExceptionIfNeeded(int typeIndex) {
		throwsExceptionIfHaveDuplicated(typeIndex);

		SeedException
				.createNew(AssemblerErrorCodes.REPRESENTATION_IS_NOT_VALID)
				.put(MESSAGE,
						"No attribute annotation @" + MatchingEntityId.class.getSimpleName() + " or @"
								+ MatchingFactoryParameter.class.getSimpleName() + " were found on DTO")
				.put("Class Name", targetClass).throwsIf(size(typeIndex) == 0);
	}

	boolean isPresent(Class<?> class1) {
		return typesRepo.containsKey(class1);
	}

	boolean isPresent(Class<?> class1, Integer typeIndex) {
		if (-1 == typeIndex) {
			return isPresent(class1);
		} else {
			return getTypesRepoByTypeIndex(typeIndex).containsKey(class1);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	<Instance extends Object> Instance getInstanceByType(Class type) {
		return (Instance) instancesByTypes.get(type);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	<Instance extends Object> Instance getInstanceByType(Class type, Integer typeIndex) {
		Instance instance = (Instance) instancesByTypesByTypeIndex.get(typeIndex).get(type);

		if (NULL.equals(instance)) {
			instance = null;
		}

		return instance;
	}

	@SuppressWarnings("rawtypes")
	<Instance extends Object> Quintet<Instance, Class, Integer, Integer, Method> getBySortNumber(int index) {
		return getBySortNumber(index, -1);
	}

	@SuppressWarnings("rawtypes")
	<Instance extends Object> Quintet<Instance, Class, Integer, Integer, Method> getBySortNumber(int index, int typeIndex) {

		List<Integer> currentFieldIndices = typeIndex < 0 ? fieldIndices : fieldIndicesByTypeIndex.get(typeIndex);

		Integer indexResult = null;

		for (Integer idx : currentFieldIndices) {
			if (idx.equals(index)) {
				indexResult = currentFieldIndices.indexOf(idx);
			}
		}
		if (indexResult != null) {
			if (typeIndex < 0) {
				return get(indexResult);
			} else {
				return get(index, typeIndex);
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	<Instance extends Object> Quintet<Instance, Class, Integer, Integer, Method> get(Integer index) {
		return Quintet.with((Instance) instances.get(index), types.get(index), fieldIndices.get(index), typeIndices.get(index),
				methods.get(index));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	<Instance extends Object> Quintet<Instance, Class, Integer, Integer, Method> get(Integer index, Integer typeIndex) {
		return Quintet.with((Instance) instancesByTypeIndex.get(typeIndex).get(index),
				typesByTypeIndex.get(typeIndex).get(index), fieldIndicesByTypeIndex.get(typeIndex).get(index),
				/* typeIndices .get(index) */null, /* methods .get(index) */null);
	}

	@SuppressWarnings("unchecked")
	<Instance extends Object> Instance getInstanceByTypeIndex(Integer index, Integer typeIndex) {
		return (Instance) instancesByTypeIndex.get(typeIndex).get(countByType.get(typeIndex) - 1);
	}

	@SuppressWarnings("rawtypes")
	private void addType(Class<?> type, Integer typeIndex) {
		Map<Class, Integer> currentTypesRepo;

		if (typeIndex < 0) {
			currentTypesRepo = typesRepo;
		} else {
			currentTypesRepo = getTypesRepoByTypeIndex(typeIndex);

			List<Class> listOfTypes;
			if (!typesByTypeIndex.containsKey(typeIndex)) {
				listOfTypes = Lists.newArrayList();
				typesByTypeIndex.put(typeIndex, listOfTypes);
			} else {
				listOfTypes = typesByTypeIndex.get(typeIndex);
			}
			listOfTypes.add(type);
		}

		types.add(type);

		if (!currentTypesRepo.containsKey(type)) {
			currentTypesRepo.put(type, 1);
		} else {
			Integer anInt = currentTypesRepo.get(type);
			currentTypesRepo.put(type, anInt + 1);
		}
	}

	@SuppressWarnings("rawtypes")
	Map<Class, Integer> getTypesRepoByTypeIndex(Integer typeIndex) {
		if (!typesRepoByTypeIndex.containsKey(typeIndex)) {
			typesRepoByTypeIndex.put(typeIndex, Maps.<Class, Integer> newHashMap());
		}
		return typesRepoByTypeIndex.get(typeIndex);
	}

	private void incrementCountByType(Integer typeIndex) {
		if (!countByType.containsKey(typeIndex)) {
			countByType.put(typeIndex, 1);
		} else {
			countByType.put(typeIndex, countByType.get(typeIndex) + 1);
		}
	}

	@SuppressWarnings("rawtypes")
	private void instancesByTypesByTypeIndex(Integer typeIndex, Class<?> type, Object instance) {
		Map<Class, Object> current;

		if (!instancesByTypesByTypeIndex.containsKey(typeIndex)) {
			current = Maps.newConcurrentMap();
			instancesByTypesByTypeIndex.put(typeIndex, current);
		} else {
			current = instancesByTypesByTypeIndex.get(typeIndex);
		}

		if (instance != null) {
			current.put(type, instance);
		} else {
			current.put(type, NULL);
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false);
	}

	@SuppressWarnings("rawtypes")
	Class getTargetClass() {
		return targetClass;
	}

}