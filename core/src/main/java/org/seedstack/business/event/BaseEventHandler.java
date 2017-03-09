package org.seedstack.business.event;

import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;

import net.jodah.typetools.TypeResolver;

public abstract class BaseEventHandler<E extends Event> implements EventHandler<E> {

    private static final int EVENT_INDEX = 0;

    protected final Class<E> eventClass;

    @SuppressWarnings("unchecked")
    public BaseEventHandler() {
        Class<?> subType = getClass();
        Class<?>[] rawArguments = TypeResolver
                .resolveRawArguments(TypeResolver.resolveGenericType(BaseEventHandler.class, subType), subType);
        this.eventClass = (Class<E>) rawArguments[EVENT_INDEX];
    }

    @Override
    public Class<E> getEventClass() {
        return this.eventClass;
    }
}
