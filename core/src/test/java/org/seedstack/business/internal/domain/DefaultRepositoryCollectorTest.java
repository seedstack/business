/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.repositories.DummyRepository;
import org.seedstack.business.fixtures.repositories.MyQualifier;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.seed.core.internal.guice.BindingStrategy;

public class DefaultRepositoryCollectorTest {

    private DefaultRepositoryCollector underTest;
    private Application application;
    private TypeLiteral<?> genericInterface = TypeLiteral.get(
            Types.newParameterizedType(Repository.class, MyAgg.class));

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        application = mock(Application.class);
        Map<Key<?>, Class<?>> bindings = new HashMap<>();
        underTest = new DefaultRepositoryCollector(
                application,
                bindings,
                Lists.newArrayList(MyDefaultRepo.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCollectSuperclasses() throws Exception {
        Collection<BindingStrategy> bindingStrategies = underTest
                .collectFromAggregates(Lists.newArrayList(MySubAgg1.class, MySubAgg2.class));
        assertThat(((Map<?, ?>) Whitebox.getInternalState(bindingStrategies.iterator()
                .next(), "constructorParamsMap")).size()).isEqualTo(3);
    }

    @Test
    public void testGetDefaultWithQualifierString() {
        when(application.getConfiguration(MyAgg.class)).thenReturn(
                ClassConfiguration.of(MyAgg.class, "defaultRepository", "my-qualifier"));
        Key<?> key = BusinessUtils
                .resolveDefaultQualifier(application, "defaultRepository", MyAgg.class, genericInterface);
        assertThat(key.getAnnotation()).isEqualTo(Names.named("my-qualifier"));
    }

    @Test
    public void testGetDefaultWithQualifierAnnotation() {
        when(application.getConfiguration(MyAgg.class)).thenReturn(
                ClassConfiguration.of(MyAgg.class, "defaultRepository",
                        "org.seedstack.business.fixtures.repositories.MyQualifier"));
        Key<?> key = BusinessUtils
                .resolveDefaultQualifier(application, "defaultRepository", MyAgg.class, genericInterface);
        assertThat(key.getAnnotationType()).isEqualTo(MyQualifier.class);
    }

    private static class MyAgg extends BaseAggregateRoot<Long> {

    }

    private static class MySubAgg1 extends MyAgg {

    }

    private static class MySubAgg2 extends MyAgg {

    }

    private static class MyDefaultRepo<A extends AggregateRoot<K>, K> extends DummyRepository<A, K> {

    }
}
