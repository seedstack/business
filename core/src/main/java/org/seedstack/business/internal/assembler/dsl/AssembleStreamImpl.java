package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.AssembleStream;
import org.seedstack.business.assembler.dsl.AssembleStreamWithQualifier;
import org.seedstack.business.domain.AggregateRoot;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public class AssembleStreamImpl implements AssembleStreamWithQualifier {
    private final AssemblerDslContext context;
    private Stream<?> aggregates;
    private Assembler assembler;

    AssembleStreamImpl(AssemblerDslContext context, Stream<?> aggregates) {
        this.context = context;
        this.aggregates = aggregates;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> Stream<D> to(Class<D> dtoClass) {
        if (aggregates != null) {
            aggregates = aggregates
                    .peek(aggregate -> {
                        if (assembler == null) {
                            assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregate.getClass(), dtoClass);
                        }
                    })
                    .map(aggregate -> (D) assembler.assembleDtoFromAggregate(aggregate));
        }
        return (Stream<D>) aggregates;
    }

    AssemblerDslContext getContext() {
        return context;
    }

    @Override
    public AssembleStream with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssembleStream with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }
}