package org.seedstack.business.core.interfaces.assembler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public enum AssemblerTypes {
    MODEL_MAPPER(get(ModelMapper.class));

    private final Annotation annotation;

    AssemblerTypes(Annotation annotation) {
        this.annotation = annotation;
    }

    Annotation get() {
        return annotation;
    }

    @SuppressWarnings("unchecked")
    static <T extends Annotation> T get(Class<T> annotationClass) {
        return (T) Proxy.newProxyInstance(AssemblerTypes.class.getClassLoader(), new Class[]{annotationClass}, new MyInvocationHandler(annotationClass));
    }

    private static class MyInvocationHandler<T extends Annotation> implements InvocationHandler {
        private final Method annotationType;
        private final Class<T> annotationClass;

        public MyInvocationHandler(Class<T> annotationClass) {
            this.annotationClass = annotationClass;

            try {
                this.annotationType = Annotation.class.getMethod("annotationType");
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.equals(annotationType)) {
                return annotationClass;
            }

            throw new UnsupportedOperationException("Only annotationType method is supported");
        }
    }
}
