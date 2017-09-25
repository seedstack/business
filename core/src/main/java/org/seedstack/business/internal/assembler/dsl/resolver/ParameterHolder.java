/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl.resolver;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.shed.reflect.ReflectUtils;


class ParameterHolder<DtoT> {

  private final Class<DtoT> dtoClass;
  private Map<Integer, SortedMap<Integer, Method>> methodMap = new HashMap<>();

  // for DTO mapped to only one aggregate
  private Method aggregateGetter;
  private Method[] aggregateGetters;

  // for DTO mapped to multiple aggregates
  private Method[] tupleGetter;
  private Method[][] tupleGetters;

  ParameterHolder(Class<DtoT> dtoClass) {
    this.dtoClass = dtoClass;
  }

  void addValue(Class<? extends Annotation> annotation, Method getter) {
    checkNotNull(getter, "Getter cannot be null");
    internalAddParameter(annotation, -1, -1, getter);
  }

  void addParameter(Class<? extends Annotation> annotation, int index, Method getter) {
    checkNotNull(getter, "Getter cannot be null");
    checkArgument(index >= 0, "Parameter index must be greater than or equal to zero");
    internalAddParameter(annotation, -1, index, getter);
  }

  void addTupleValue(Class<? extends Annotation> annotation, int aggregateIndex, Method getter) {
    checkNotNull(getter, "Getter cannot be null");
    checkArgument(aggregateIndex >= 0, "Aggregate index must be greater than or equal to zero");
    internalAddParameter(annotation, aggregateIndex, -1, getter);
  }

  void addTupleParameter(Class<? extends Annotation> annotation, int aggregateIndex, int index,
      Method getter) {
    checkNotNull(getter, "Getter cannot be null");
    checkArgument(aggregateIndex >= 0, "Aggregate index must be greater than or equal to zero");
    checkArgument(index >= 0, "Parameter index must be greater than or equal to zero");
    internalAddParameter(annotation, aggregateIndex, index, getter);
  }

  private void internalAddParameter(Class<? extends Annotation> annotation, int aggregateIndex,
      int index,
      Method getter) {
    checkState(methodMap != null, "Cannot add parameter after having called freeze()");
    if (methodMap.computeIfAbsent(aggregateIndex, k -> new TreeMap<>()).putIfAbsent(index, getter)
        != null) {
      Method conflictingGetter = methodMap.get(aggregateIndex).get(index);
      BusinessException exception;
      if (index > -1) {
        exception = BusinessException.createNew(
            annotation == AggregateId.class ? BusinessErrorCode.CONFLICTING_DTO_ID_INDEX_MATCHING
                : BusinessErrorCode.CONFLICTING_DTO_FACTORY_INDEX_MATCHING);
      } else {
        exception = BusinessException.createNew(
            annotation == AggregateId.class ? BusinessErrorCode.CONFLICTING_DTO_ID_MATCHING
                : BusinessErrorCode.CONFLICTING_DTO_FACTORY_MATCHING);
      }
      exception.put("index", index).put("dtoClass", dtoClass).put("annotation", annotation)
          .put("getter", getter)
          .put("conflictingGetter", conflictingGetter);
      if (aggregateIndex > -1) {
        throw BusinessException.wrap(exception, BusinessErrorCode.CONFLICTING_DTO_TUPLE_MATCHING)
            .put("dtoClass", dtoClass).put("aggregateIndex", aggregateIndex);
      } else {
        throw exception;
      }
    }
  }

  ParameterHolder<DtoT> freeze() {
    if (methodMap.containsKey(-1)) {
      SortedMap<Integer, Method> aggregateGetters = methodMap.get(-1);
      if (aggregateGetters.containsKey(-1)) {
        this.aggregateGetter = aggregateGetters.get(-1);
      } else {
        this.aggregateGetters = aggregateGetters.values()
            .toArray(new Method[aggregateGetters.size()]);
      }
    } else {
      tupleGetters = new Method[methodMap.size()][];
      this.tupleGetter = new Method[methodMap.size()];
      for (Map.Entry<Integer, SortedMap<Integer, Method>> entry : methodMap.entrySet()) {
        int aggregateIndex = entry.getKey();
        SortedMap<Integer, Method> aggregateGetters = entry.getValue();
        if (aggregateGetters.containsKey(-1)) {
          this.tupleGetter[aggregateIndex] = aggregateGetters.get(-1);
        } else {
          this.tupleGetters[aggregateIndex] = aggregateGetters.values()
              .toArray(new Method[aggregateGetters.size()]);
        }
      }
    }
    methodMap = null;
    return this;
  }

  Object[] parameters(DtoT dto) {
    checkNotNull(dto, "DTO cannot be null");

    Object uniqueElement = uniqueElement(dto);
    if (uniqueElement != null) {
      return new Object[]{uniqueElement};
    } else if (aggregateGetters != null) {
      Object[] values = new Object[aggregateGetters.length];
      for (int i = 0,
          length = aggregateGetters.length; i < length; i++) {
        values[i] = ReflectUtils.invoke(aggregateGetters[i], dto);
      }
      return values;
    } else {
      return new Object[0];
    }
  }

  Object[] parametersOfAggregateRoot(DtoT dto, int aggregateIndex) {
    checkNotNull(dto, "DTO cannot be null");
    checkArgument(aggregateIndex >= 0, "Aggregate index must be greater than or equal to zero");

    Object uniqueElementForAggregate = uniqueElementForAggregate(dto, aggregateIndex);
    if (uniqueElementForAggregate != null) {
      return new Object[]{uniqueElementForAggregate};
    } else if (tupleGetters != null && aggregateIndex < tupleGetters.length) {
      Method[] methods = tupleGetters[aggregateIndex];
      if (methods != null && methods.length > 0) {
        Object[] values = new Object[methods.length];
        for (int i = 0,
            length = methods.length; i < length; i++) {
          values[i] = ReflectUtils.invoke(methods[i], dto);
        }
        return values;
      }
    }
    return new Object[0];
  }

  <IdT> IdT uniqueElement(DtoT dto) {
    checkNotNull(dto, "DTO cannot be null");

    if (aggregateGetter != null) {
      return ReflectUtils.invoke(aggregateGetter, dto);
    } else {
      return null;
    }
  }

  <IdT> IdT uniqueElementForAggregate(DtoT dto, int aggregateIndex) {
    checkNotNull(dto, "DTO cannot be null");
    checkArgument(aggregateIndex >= 0, "Aggregate index must be greater than or equal to zero");

    if (aggregateIndex < tupleGetter.length) {
      Method method = tupleGetter[aggregateIndex];
      if (method != null) {
        return ReflectUtils.invoke(method, dto);
      }
    }
    return null;
  }

  boolean isEmpty() {
    return aggregateGetter == null && (aggregateGetters == null || aggregateGetters.length == 0)
        && isEmptyForAggregateRoot(0);
  }

  private boolean isEmptyForAggregateRoot(int aggregateIndex) {
    checkArgument(aggregateIndex >= 0, "Aggregate index must be greater than or equal to zero");
    return (tupleGetter == null || tupleGetter.length == 0) && (tupleGetters == null
        || tupleGetters.length == 0
        || aggregateIndex >= tupleGetters.length || tupleGetters[aggregateIndex] == null
        || tupleGetters[aggregateIndex].length == 0);
  }
}
