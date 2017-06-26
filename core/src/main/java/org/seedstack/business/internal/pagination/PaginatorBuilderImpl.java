package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.builder.PaginatorBuilder;
import org.seedstack.business.pagination.builder.RepositoryOptionPicker;

class PaginatorBuilderImpl implements PaginatorBuilder {
    @Override
    public <A extends AggregateRoot<ID>, ID> RepositoryOptionPicker<A, ID> repository(Repository<A, ID> repository){
        return new RepositoryOptionPickerImpl<>(repository);
    }
}