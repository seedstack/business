/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;


import net.jodah.typetools.TypeResolver;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

import java.lang.reflect.Type;

/**
 * This assembler is intended to be extended by the base assemblers not directly by the users.
 *
 * @param <A> the aggregate root
 * @param <D> the dto type
 * @author epo.jemba@ext.mpsa.com
 * @see org.seedstack.business.assembler.BaseAssembler
 * @see org.seedstack.business.assembler.BaseTupleAssembler
 */
public abstract class AbstractBaseAssembler<A, D> implements Assembler<A, D> {
    protected Class<D> dtoClass;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public AbstractBaseAssembler() {
        Class<? extends AbstractBaseAssembler> class1 = (Class<? extends AbstractBaseAssembler>) SeedReflectionUtils.cleanProxy(getClass());
        dtoClass = (Class<D>) TypeResolver.resolveRawArguments(getGenericType(), class1)[1];
    }

    @Override
    public Class<D> getDtoClass() {
        return this.dtoClass;
    }

    /**
     * This protected method is in charge of creating a new instance of the DTO.
     * <p>
     * The actual implementation is fine for simple POJO, but it can be
     * extended. The developers will then use {@link #getDtoClass()} to retrieve
     * the destination class.
     * </p>
     *
     * @return the DTO instance.
     */
    protected D newDto() {
        D newInstance;
        try {
            newInstance = dtoClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Error when creating new instance of " + dtoClass.getName(), e);
        }

        return newInstance;
    }

    private Type getGenericType() {
        Class<?> superclass = SeedReflectionUtils.cleanProxy(getClass());
        while (!AbstractBaseAssembler.class.equals(superclass.getSuperclass())) {
            superclass = superclass.getSuperclass();
        }

        return superclass.getGenericSuperclass();
    }
}
