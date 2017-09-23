/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.internal.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import org.seedstack.business.domain.BaseFactory;
import org.seedstack.business.domain.Producible;

/**
 * DefaultFactory allows the creations of {@link org.seedstack.business.domain.Producible} objects using their
 * constructors. <p> The {@link #create(Object...)} method will look for a constructor matching the given parameters. If
 * a constructor is found the method will use it to create a new instance. If ambiguous constructors are found, it
 * throws an exception. </p> Ambiguous constructor could be found in the following cases:
 *
 * <p>1. If a parameter is null and multiple constructors accept null.</p>
 * <pre>
 * MyObject(String name)
 * MyObject(Integer age)
 *
 * factory.create(null);
 * </pre>
 *
 * <p>2. If two constructors are found, one with primitive, and another with boxed type.</p>
 * <pre>
 * MyObject(int age)
 * MyObject(Integer age)
 * </pre>
 *
 * @param <T> the producible type.
 */
public class DefaultFactory<T extends Producible> extends BaseFactory<T> {

  private final Class<T> domainObjectClass;

  @SuppressWarnings("unchecked")
  @Inject
  DefaultFactory(@Assisted Object[] domainObjectClass) {
    checkNotNull(domainObjectClass);
    checkArgument(domainObjectClass.length == 1);
    this.domainObjectClass = (Class<T>) domainObjectClass[0];
  }

  public Class<T> getProducedClass() {
    return domainObjectClass;
  }
}
