/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static javassist.bytecode.annotation.Annotation.createMemberValue;
import static org.seedstack.business.internal.utils.BusinessUtils.getQualifier;
import static org.seedstack.shed.reflect.ReflectUtils.invoke;

import com.google.inject.assistedinject.Assisted;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.MemberValue;
import javax.inject.Inject;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.shed.ClassLoaders;
import org.seedstack.shed.reflect.Classes;

class DefaultRepositoryGenerator<T extends Repository> {
    private static final String GENERATED_PACKAGE_NAME = "org.seedstack.__generated.business.repository";
    private static final ConcurrentMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    private final ClassPool classPool;
    private final Class<T> repositoryInterface;
    private final ClassLoader classLoader;

    DefaultRepositoryGenerator(Class<T> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        this.classLoader = ClassLoaders.findMostCompleteClassLoader(DefaultRepositoryCollector.class);
        this.classPool = new ClassPool(false);
        this.classPool.appendClassPath(new LoaderClassPath(this.classLoader));
    }

    @SuppressWarnings("unchecked")
    Class<? extends T> generate(Class<? extends Repository> baseImpl) {
        try {
            return (Class<? extends T>) classLoader.loadClass(getClassName(
                    baseImpl,
                    getCounter(repositoryInterface).get()
            ));
        } catch (ClassNotFoundException ignore) {
            try {
                CtClass cc = createClass(
                        getClassName(baseImpl, getCounter(repositoryInterface).incrementAndGet()),
                        baseImpl
                );
                ClassFile cf = cc.getClassFile();
                ConstPool constPool = cf.getConstPool();

                cc.setModifiers(Modifier.PUBLIC);

                cc.setInterfaces(new CtClass[]{classPool.getCtClass(repositoryInterface.getName())});

                if (hasGenericConstructor(baseImpl)) {
                    cc.addConstructor(createConstructor(constPool, cc));
                } else {
                    cc.addConstructor(createDefaultConstructor(cc));
                }

                cf.addAttribute(createQualifierAttribute(constPool, getQualifier(baseImpl)
                        .orElseThrow(() -> new NotFoundException("Qualifier annotation not found"))));

                return cc.toClass(
                        classLoader,
                        DefaultRepositoryCollector.class.getProtectionDomain()
                );
            } catch (NoSuchMethodException | CannotCompileException | NotFoundException e) {
                throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_CREATE_DEFAULT_IMPLEMENTATION)
                        .put("interface", repositoryInterface)
                        .put("base", baseImpl);
            }
        }
    }

    private AnnotationsAttribute createQualifierAttribute(ConstPool constPool,
            java.lang.annotation.Annotation qualifier) throws NotFoundException, NoSuchMethodException {
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        attr.setAnnotation(copyAnnotation(constPool, qualifier));
        return attr;
    }

    private CtClass createClass(String className, Class<? extends Repository> baseClassName) throws NotFoundException,
            CannotCompileException {
        CtClass cc = classPool.makeClass(className, classPool.getCtClass(baseClassName.getName()));
        classPool.makePackage(classPool.getClassLoader(), GENERATED_PACKAGE_NAME);
        return cc;
    }

    private String getClassName(Class<? extends Repository> defaultRepositoryImplementation, int generation) {
        return GENERATED_PACKAGE_NAME
                + "."
                + repositoryInterface.getSimpleName()
                + "_"
                + defaultRepositoryImplementation.getSimpleName()
                + "_"
                + generation;
    }

    private Annotation copyAnnotation(ConstPool constPool,
            java.lang.annotation.Annotation annotation) throws NotFoundException {
        // Create annotation from specified type
        Annotation byteCodeAnnotation = createAnnotation(
                constPool,
                annotation.annotationType()
        );

        // Copy annotation methods
        for (Method m : annotation.annotationType().getDeclaredMethods()) {
            Object value = invoke(m, annotation);
            MemberValue memberValue = createMemberValue(
                    constPool,
                    classPool.get(value.getClass().getName())
            );

            invoke(Classes.from(memberValue.getClass())
                            .method("setValue", value.getClass())
                            .orElseThrow(() -> new NotFoundException("Cannot copy value of qualifier parameter "
                                    + m.getName())),
                    memberValue,
                    value);

            byteCodeAnnotation.addMemberValue(
                    m.getName(),
                    memberValue
            );
        }

        return byteCodeAnnotation;
    }

    private Annotation createAnnotation(ConstPool constPool,
            Class<? extends java.lang.annotation.Annotation> annotationType) {
        return new Annotation(
                annotationType.getName(),
                constPool
        );
    }

    private boolean hasGenericConstructor(Class<? extends Repository> baseImpl) {
        try {
            baseImpl.getDeclaredConstructor(Object[].class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private CtConstructor createDefaultConstructor(CtClass declaringClass) throws CannotCompileException {
        return CtNewConstructor.defaultConstructor(declaringClass);
    }

    private CtConstructor createConstructor(ConstPool constPool, CtClass declaringClass) throws NotFoundException,
            CannotCompileException {
        CtConstructor cc = new CtConstructor(new CtClass[]{
                declaringClass.getClassPool().getCtClass(Object.class.getName() + "[]")
        }, declaringClass);

        // Define the constructor behavior
        cc.setBody("super($1);");
        cc.setModifiers(Modifier.PUBLIC);

        // Add the @Inject annotation to the constructor
        addInjectAnnotation(constPool, cc);

        // Add the @Assisted annotation to the constructor parameter
        addAssistedAnnotation(constPool, cc);

        return cc;
    }

    private void addInjectAnnotation(ConstPool constPool, CtConstructor cc) {
        AnnotationsAttribute attribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        attribute.setAnnotation(createAnnotation(constPool, Inject.class));
        cc.getMethodInfo().addAttribute(attribute);
    }

    private void addAssistedAnnotation(ConstPool constPool, CtConstructor cc) {
        ParameterAnnotationsAttribute attribute = new ParameterAnnotationsAttribute(constPool,
                ParameterAnnotationsAttribute.visibleTag);
        attribute.setAnnotations(new Annotation[][]{{createAnnotation(constPool, Assisted.class)}});
        cc.getMethodInfo().addAttribute(attribute);
    }

    private static AtomicInteger getCounter(Class<?> repositoryInterface) {
        return counters.computeIfAbsent(repositoryInterface.getName(), key -> new AtomicInteger());
    }
}
