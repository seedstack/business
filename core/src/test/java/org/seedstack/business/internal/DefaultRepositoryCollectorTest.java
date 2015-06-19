/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.apache.commons.configuration.Configuration;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.repositories.fixtures.MyQualifier;
import org.seedstack.seed.core.api.Application;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class DefaultRepositoryCollectorTest {

    private DefaultRepositoryCollector underTest;
    private Application application;

    private TypeLiteral<?> genericInterface = TypeLiteral.get(Types.newParameterizedType(Repository.class, new Type[]{MyAgg.class}));

    @Before
    public void before() {
        application = mock(Application.class);
        underTest = new DefaultRepositoryCollector(new ArrayList<Class<?>>(), new ArrayList<Class<?>>(), application);
    }

    private static class MyAgg {}

    @Test
    public void testGetDefaultWithQualifierString() {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString("default.repository.qualifier")).thenReturn("my-qualifier");

        when(application.getConfiguration(MyAgg.class)).thenReturn(configuration);
        Key<?> key = underTest.defaultRepositoryQualifier(MyAgg.class, genericInterface);

        Assertions.assertThat(key.getAnnotation()).isEqualTo(Names.named("my-qualifier"));
    }

    @Test
    public void testGetDefaultWithQualifierAnnotation() {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString("default.repository.qualifier")).thenReturn("org.seedstack.business.repositories.fixtures.MyQualifier");

        when(application.getConfiguration(MyAgg.class)).thenReturn(configuration);
        Key<?> key = underTest.defaultRepositoryQualifier(MyAgg.class, genericInterface);

        Assertions.assertThat(key.getAnnotationType()).isEqualTo(MyQualifier.class);
    }
}
