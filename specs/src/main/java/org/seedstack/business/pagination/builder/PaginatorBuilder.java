package org.seedstack.business.pagination.builder;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

public interface PaginatorBuilder {
    <A extends AggregateRoot<ID>, ID> RepositoryOptionPicker<A, ID> repository(Repository<A, ID> repository);
}



