/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import com.google.common.collect.Lists;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.DummyRepository;
import org.seedstack.business.fixtures.repositories.MyQualifier;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.seed.core.internal.guice.BindingStrategy;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DefaultRepositoryCollectorTest {
    private DefaultRepositoryCollector underTest;
    private Application application;
    private TypeLiteral<?> genericInterface = TypeLiteral.get(Types.newParameterizedType(Repository.class, new Type[]{MyAgg.class}));

    @Before
    public void before() {
        application = mock(Application.class);
        underTest = new DefaultRepositoryCollector(
                Lists.newArrayList(MyDefaultRepo.class),
                application
        );
    }

    @Test
    public void testCollectSuperclasses() throws Exception {
        Collection<BindingStrategy> bindingStrategies = underTest.collect(Lists.newArrayList(MySubAgg1.class, MySubAgg2.class));
        assertThat(((Map<?, ?>) Whitebox.getInternalState(bindingStrategies.iterator().next(), "constructorParamsMap")).size()).isEqualTo(3);
    }

    @Test
    public void testGetDefaultWithQualifierString() {
        when(application.getConfiguration(MyAgg.class)).thenReturn(ClassConfiguration.of(MyAgg.class, "defaultRepository", "my-qualifier"));
        Key<?> key = BusinessUtils.defaultQualifier(application, "defaultRepository", MyAgg.class, genericInterface);
        assertThat(key.getAnnotation()).isEqualTo(Names.named("my-qualifier"));
    }

    @Test
    public void testGetDefaultWithQualifierAnnotation() {
        when(application.getConfiguration(MyAgg.class)).thenReturn(ClassConfiguration.of(MyAgg.class, "defaultRepository", "org.seedstack.business.fixtures.repositories.MyQualifier"));
        Key<?> key = BusinessUtils.defaultQualifier(application, "defaultRepository", MyAgg.class, genericInterface);
        assertThat(key.getAnnotationType()).isEqualTo(MyQualifier.class);
    }

    private static class MyAgg extends BaseAggregateRoot<Long> {
    }

    private static class MySubAgg1 extends MyAgg {
    }

    private static class MySubAgg2 extends MyAgg {
    }

    public class MyDefaultRepo<A extends AggregateRoot<K>, K> extends DummyRepository<A, K> {
    }
}
