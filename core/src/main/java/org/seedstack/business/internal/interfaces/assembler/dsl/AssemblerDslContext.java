/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl;

import java.lang.annotation.Annotation;

/**
 * Context used by the DSL to carry the internal registry and the qualifier it uses.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AssemblerDslContext {

    private InternalRegistry registry;

    private Annotation assemblerAnnotationQualifier;

    private Class<? extends Annotation> assemblerAnnotationClassQualifier;

    public Annotation getAssemblerAnnotationQualifier() {
        return assemblerAnnotationQualifier;
    }

    public void setAssemblerAnnotationQualifier(Annotation assemblerAnnotationQualifier) {
        this.assemblerAnnotationQualifier = assemblerAnnotationQualifier;
    }

    public Class<? extends Annotation> getAssemblerAnnotationClassQualifier() {
        return assemblerAnnotationClassQualifier;
    }

    public void setAssemblerAnnotationClassQualifier(Class<? extends Annotation> assemblerAnnotationClassQualifier) {
        this.assemblerAnnotationClassQualifier = assemblerAnnotationClassQualifier;
    }

    public InternalRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(InternalRegistry registry) {
        this.registry = registry;
    }
}
