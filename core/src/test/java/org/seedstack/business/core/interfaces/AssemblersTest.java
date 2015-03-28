/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces;

import com.google.common.collect.*;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import org.assertj.core.api.Assertions;
import org.fest.reflect.exception.ReflectionError;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.powermock.reflect.Whitebox;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.domain.identity.MyAggregate;
import org.seedstack.business.api.domain.identity.MyAggregateFactory;
import org.seedstack.business.api.domain.identity.MyAggregateFactoryDefault;
import org.seedstack.business.api.interfaces.assembler.*;
import org.seedstack.business.core.domain.FactoriesInternal;
import org.seedstack.business.core.domain.InMemoryRepository;
import org.seedstack.business.core.domain.RepositoriesInternal;
import org.seedstack.business.sample.domain.activation.*;
import org.seedstack.business.sample.domain.customer.*;
import org.seedstack.business.sample.domain.discount.Discount;
import org.seedstack.business.sample.domain.discount.DiscountFactory;
import org.seedstack.business.sample.domain.discount.DiscountFactoryBase;
import org.seedstack.business.sample.domain.export.Export;
import org.seedstack.business.sample.domain.export.ExportFactory;
import org.seedstack.business.sample.domain.export.ExportFactoryBase;
import org.seedstack.business.sample.domain.order.Order;
import org.seedstack.business.sample.domain.order.OrderFactory;
import org.seedstack.business.sample.domain.order.OrderFactoryDefault;
import org.seedstack.business.sample.domain.order.OrderRepository;
import org.seedstack.business.sample.domain.product.*;
import org.seedstack.business.sample.domain.reporting.Report;
import org.seedstack.business.sample.domain.reporting.ReportFactory;
import org.seedstack.business.sample.domain.reporting.ReportFactoryBase;
import org.seedstack.business.sample.infrastructure.persistence.activation.ActivationInMemoryRepository;
import org.seedstack.business.sample.infrastructure.persistence.customer.CustomerInMemoryRepository;
import org.seedstack.business.sample.infrastructure.persistence.order.OrderInMemoryRepository;
import org.seedstack.business.sample.infrastructure.persistence.product.ProductInMemoryRepository;
import org.seedstack.business.sample.interfaces.activation.ActivationAssembler;
import org.seedstack.business.sample.interfaces.activation.ActivationRepresentation;
import org.seedstack.business.sample.interfaces.product.presentation2.ProductWSAssembler;
import org.seedstack.business.sample.interfaces.product.presentationsimple1.ProductSimple1Assembler;
import org.seedstack.business.sample.interfaces.product.presentationsimple2.ProductSimple2Assembler;
import org.seedstack.business.sample.interfaces.usecase1.UseCase1Assembler;
import org.seedstack.business.sample.interfaces.usecase1.UseCase1Representation;
import org.seedstack.seed.core.api.Ignore;
import org.seedstack.seed.core.api.SeedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests the Assemblers helper class.
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class AssemblersTest {

    private Assemblers underTest;

    private ActivationFactory activationFactory;

    private ActivationRepository activationRepository;

    private CustomerFactory customerFactory;

    private CustomerRepository customerRepository;

    private OrderFactory orderFactory;

    private OrderRepository orderRepository;

    private ProductFactory productFactory;

    private ProductRepository productRepository;

    private UseCase1Assembler uc1Assembler;

    private ProductNamePolicy productNamePolicy;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        // Prepare the domain used by the tests
        activationFactory = new ActivationFactoryDefault();
        activationRepository = mockRepo(new ActivationInMemoryRepository());
        customerFactory = new CustomerFactoryDefault();
        customerRepository = mockRepo(new CustomerInMemoryRepository());
        orderFactory = new OrderFactoryDefault();
        orderRepository = mockRepo(new OrderInMemoryRepository());
        productFactory = new InternalProductFactory();
        productNamePolicy = new InternalProductNamePolicy();
        Whitebox.setInternalState(productFactory, "productNamePolicy", productNamePolicy);
        productRepository = mockRepo(new ProductInMemoryRepository());
        MyCustomerAssembler assembler = new MyCustomerAssembler();
        uc1Assembler = new UseCase1Assembler();

        // Initialize the assemblers helper
        underTest = new AssemblersInternal();
        Set assemblersClasses = Sets.newHashSet(ProductSimple2Assembler.class, ActivationAssembler.class,
                MyAssembler.class, UseCase1Assembler.class, ProductSimple1Assembler.class, MySecuredAssembler.class,
                ProductWSAssembler.class, VipAssembler.class, MyCustomerAssembler.class);
        Map<Class, Assembler> assemblerMap = Maps.newHashMap();
        assemblerMap.put(ProductSimple2Assembler.class, new ProductSimple2Assembler());
        assemblerMap.put(ActivationAssembler.class, new ActivationAssembler());
        assemblerMap.put(MyAssembler.class, new MyAssembler());
        assemblerMap.put(UseCase1Assembler.class, uc1Assembler);
        assemblerMap.put(ProductSimple1Assembler.class, new ProductSimple1Assembler());
        assemblerMap.put(MySecuredAssembler.class, new MySecuredAssembler());
        assemblerMap.put(ProductWSAssembler.class, new ProductWSAssembler());
        assemblerMap.put(VipAssembler.class, new VipAssembler());
        assemblerMap.put(MyCustomerAssembler.class, assembler);

        // Mock factories
        Factories factories = new FactoriesInternal();
        Multimap<Class<?>, GenericFactory<?>> factoriesMap = ArrayListMultimap.create();
        factoriesMap.put(Customer.class, customerFactory);
        factoriesMap.put(Product.class, productFactory);
        factoriesMap.put(MyAggregate.class, new MyAggregateFactoryDefault());
        factoriesMap.put(Discount.class, new DiscountFactoryBase());
        factoriesMap.put(Report.class, new ReportFactoryBase());
        factoriesMap.put(Activation.class, activationFactory);
        factoriesMap.put(Order.class, orderFactory);
        factoriesMap.put(Export.class, new ExportFactoryBase());
        Whitebox.setInternalState(factories, "factories", factoriesMap);
        Whitebox.setInternalState(factories, "factoriesClasses",
                Sets.newHashSet(CustomerFactory.class, OrderFactory.class, ActivationFactory.class, ExportFactory.class,
                        DiscountFactory.class, MyAggregateFactory.class, ProductFactory.class, ReportFactory.class));

        // Mock repositories
        Repositories repositories = new RepositoriesInternal();
        Multimap<Class, Repository> repositoryMap = ArrayListMultimap.create();
        repositoryMap.put(Order.class, orderRepository);
        repositoryMap.put(Customer.class, customerRepository);
        repositoryMap.put(Activation.class, activationRepository);
        repositoryMap.put(Product.class, productRepository);
        Whitebox.setInternalState(repositories, "repositories", repositoryMap);
        Whitebox.setInternalState(repositories, "repositoriesKeys",
                Sets.newHashSet(ActivationRepository.class, CustomerRepository.class, ProductRepository.class, OrderRepository.class));

        // prepare the class under test
        Whitebox.setInternalState(underTest, "assemblers", assemblerMap);
        Whitebox.setInternalState(underTest, "assemblersClasses", assemblersClasses);
        Whitebox.setInternalState(underTest, "repositories", repositories);
        Whitebox.setInternalState(underTest, "factories", factories);
        Whitebox.setInternalState(underTest, "injector", mock(Injector.class));

    }

    private <T extends InMemoryRepository> T mockRepo(T inMemoryRepository) {
        Whitebox.setInternalState(inMemoryRepository, "inMemorySortedMap", new HashMap<Object, Object>());
        return inMemoryRepository;
    }

    private Quartet<Activation, Customer, Order, Product> initialize_quartet(String suffix) {
        Objenesis objenesis = new ObjenesisStd();

        Activation activation;
        Customer customer;
        Order order;
        Product product;
        {
            // Activation
            ObjectInstantiator activationInstantiator = objenesis.getInstantiatorOf(Activation.class);
            activation = (Activation) activationInstantiator.newInstance();
            Whitebox.setInternalState(activation, "entityId", suffix);
            activation.setDescription("activation" + suffix);
            Assertions.assertThat(activation.getEntityId()).isEqualTo(suffix);
        }
        {
            // Customer
            ObjectInstantiator customerInstantiator = objenesis.getInstantiatorOf(Customer.class);
            customer = (Customer) customerInstantiator.newInstance();
            customer.setFirstName("fistname" + suffix);
            customer.setLastName("lastname" + suffix);
        }
        {
            // Order
            ObjectInstantiator orderInstantiator = objenesis.getInstantiatorOf(Order.class);
            order = (Order) orderInstantiator.newInstance();
            order.setDescription("order" + suffix);
        }
        {
            // Product
            ObjectInstantiator productInstantiator = objenesis.getInstantiatorOf(Product.class);
            product = (Product) productInstantiator.newInstance();
            product.setName("name" + suffix);
            product.setDescription("product" + suffix);
        }


        return Quartet.with(activation, customer, order, product);

    }

    @Test
    public void baseTupleAssembler_should_be_injected_and_work() {

        Quartet<Activation, Customer, Order, Product> aggregateRootsInitialized = initialize_quartet("1");

        Activation activation = aggregateRootsInitialized.getValue0();
        assertThat(activation).isNotNull();
        assertThat(activation.getDescription()).isEqualTo("activation1");

        UseCase1Representation useCase1Representation = uc1Assembler.assembleDtoFromAggregate(aggregateRootsInitialized);

        assertThat(useCase1Representation).isNotNull();
        assertThat(useCase1Representation.getActivationDescription()).isEqualTo("activation1");
    }

    @Test
    public void assembleDtoFromEntity_should_work_with_list() {
        Customer client1 = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");
        Customer client2 = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");

        List<MyCustomerRepresentation> representations = underTest.assembleDtoFromAggregate(MyCustomerRepresentation.class, Lists.newArrayList(client1, client2));

        assertThat(representations).isNotEmpty();
        assertThat(representations.get(0)).isNotNull();
        assertThat(representations.get(0).getId()).isEqualTo("epo@jemba.net");
        assertThat(representations.get(0).getName()).isEqualTo("jemba, epo");
    }

    @Test
    public void mergeEntitiesWithDto_should_work() {
        // init the representation
        Quartet<Activation, Customer, Order, Product> expectedAggregateRoots = initialize_quartet("1");
        UseCase1Representation useCase1Representation = uc1Assembler.assembleDtoFromAggregate(expectedAggregateRoots);

        // Get new entities
        Quartet<Activation, Customer, Order, Product> aggregateRoots = initialize_quartet("2");
        assertThat(aggregateRoots.getValue0()).isNotNull();
        assertThat(aggregateRoots.getValue0().getDescription()).isEqualTo("activation2");

        underTest.mergeEntitiesWithDto(useCase1Representation, aggregateRoots);

        assertThat(aggregateRoots.getValue0()).isNotNull();
        assertThat(aggregateRoots.getValue0().getDescription()).isEqualTo("activation1");
    }

    @Test
    public void createThenMergeEntityWithDto_should_work() {
        Customer client1 = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");
        Customer client2 = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");
        List<MyCustomerRepresentation> representations = underTest.assembleDtoFromAggregate(MyCustomerRepresentation.class, Lists.newArrayList(client1, client2));

        List<Customer> mergedAggregates = underTest.createThenMergeAggregateWithDto(representations, Customer.class);
        assertThat(mergedAggregates.size()).isEqualTo(2);
        assertThat(mergedAggregates.get(0)).isEqualTo(client1);
        assertThat(mergedAggregates.get(1)).isEqualTo(client2);
    }

    @Test
    public void assemblers_should_work_with_baseTupleAssemblers() {
        Quartet<Activation, Customer, Order, Product> aggregateRootsInitialized = initialize_quartet("1");
        UseCase1Representation assembledDtoFromEntities = underTest.assembleDtoFromAggregates(UseCase1Representation.class, aggregateRootsInitialized);

        assertThat(assembledDtoFromEntities).isNotNull();
        assertThat(assembledDtoFromEntities.getActivationDescription()).isEqualTo("activation1");
    }

    @Test
    public void assemblers_should_work_with_list() {
        List<Quartet<Activation, Customer, Order, Product>> aggregateRootsInitializedList = Lists.newArrayListWithCapacity(100);

        for (int i = 0; i < 100; i++) {
            Quartet<Activation, Customer, Order, Product> aggregateRootsInitialized = initialize_quartet(String.valueOf(i));
            aggregateRootsInitializedList.add(aggregateRootsInitialized);
        }

        List<UseCase1Representation> assembledDtoFromEntities = underTest.assembleDtoFromAggregates(UseCase1Representation.class, aggregateRootsInitializedList);
        assertThat(assembledDtoFromEntities).isNotNull();
        for (int i = 0; i < assembledDtoFromEntities.size(); i++) {
            assertThat(assembledDtoFromEntities.get(i).getActivationDescription()).isEqualTo("activation" + i);
        }
    }

    @Test
    public void aggregate_creation_with_factories_should_work_with_base_tuple_assemblers() {
        UseCase1Representation representation = new UseCase1Representation(
                "activation", "new activation description",
                "customer", "new fname", "new lname",
                "order", "new description",
                (short) 1, productNamePolicy.transform((short) 1), "Awesome Product 2");

        Quartet<Activation, Customer, Order, Product> mergedEntities =
                underTest.createThenMergeAggregatesWithDto(representation, new TypeLiteral<Quartet<Activation,Customer,Order,Product>>() {});

        assertThat((Object)mergedEntities).isNotNull();

        assertThat(mergedEntities.getValue0()).isNotNull();
        assertThat(mergedEntities.getValue0().getDescription()).isNotNull();
        assertThat(mergedEntities.getValue0().getDescription()).isEqualTo("new activation description");
        assertThat(mergedEntities.getValue1()).isNotNull();
        assertThat(mergedEntities.getValue1().getFirstName()).isNotNull();
        assertThat(mergedEntities.getValue1().getFirstName()).isEqualTo("new fname");
        assertThat(mergedEntities.getValue1().getLastName()).isNotNull();
        assertThat(mergedEntities.getValue1().getLastName()).isEqualTo("new lname");
        assertThat(mergedEntities.getValue2()).isNotNull();
        assertThat(mergedEntities.getValue2().getDescription()).isNotNull();
        assertThat(mergedEntities.getValue2().getDescription()).isEqualTo("new description");
        assertThat(mergedEntities.getValue3()).isNotNull();
        assertThat(mergedEntities.getValue3().getDescription()).isNotNull();
        assertThat(mergedEntities.getValue3().getDescription()).isEqualTo("Awesome Product 2");
    }

    @Test
     public void assemblers_should_work_with_dtos_returning_nulls() {
        MyCustomerRepresentation customerRepresentation = new MyCustomerRepresentation();
        customerRepresentation.setId(null);
        customerRepresentation.setName(null);

        Customer merged = underTest.createThenMergeAggregateWithDto(customerRepresentation, Customer.class);

        assertThat(merged.getEntityId()).isNotNull();
        assertThat(merged.getEntityId().getCustomerId()).isEqualTo(null);
    }

    @Test
     public void assemblers_should_work_with_entities_returning_nulls() {
        assertThat(customerFactory).isNotNull();
        Customer client = customerFactory.createNewCustomer("epo@jemba.net", null, null);
        assertThat(customerRepository).isNotNull();

        customerRepository.persist(client);
        Customer load = customerRepository.load(new CustomerId("epo@jemba.net"));
        assertThat(load).isNotNull();
        assertThat(load.getFullName()).isNotNull();
        assertThat(load.getFullName()).isEqualTo("null, null");

        MyCustomerRepresentation custoRep = new MyCustomerRepresentation();
        custoRep.setId("epo@jemba.net");
        custoRep.setName(null);

        Customer merged = underTest.retrieveThenMergeAggregateWithDto(custoRep, Customer.class);
        assertThat(merged.getEntityId()).isNotNull();
        assertThat(merged.getFullName()).isEqualTo("null, null");
    }

    @Test
    public void assemblers_should_work_with_inherited_dtos_1() {
        assertThat(customerFactory).isNotNull();

        Customer client = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");
        assertThat(customerRepository).isNotNull();

        customerRepository.persist(client);

        Customer load = customerRepository.load(new CustomerId("epo@jemba.net"));
        assertThat(load).isNotNull();
        assertThat(load.getFullName()).isNotNull();
        assertThat(load.getFullName()).isEqualTo("jemba, epo");

        VipRepresentation custoRep = new VipRepresentation();
        custoRep.setId("epo@jemba.net");
        custoRep.setName("Epo Jemba");

        Customer merged = underTest.retrieveThenMergeAggregateWithDto(custoRep, Customer.class);

        assertThat(merged.getEntityId()).isNotNull();
        assertThat(merged.getFullName()).isEqualTo("Epo, Jemba");

    }

    @Test
    public void assemblers_should_work_with_inherited_dtos_2() {
        VipRepresentation custoRep = new VipRepresentation();
        custoRep.setId("epo.jemba@tata.com");
        custoRep.setName("na me"); // representation need a space

        Customer merged = underTest.createThenMergeAggregateWithDto(custoRep, Customer.class);

        assertThat(merged.getEntityId()).isNotNull();
        assertThat(merged.getEntityId().getCustomerId()).isEqualTo("epo.jemba@tata.com");
    }

    @Test(expected = SeedException.class)
    public void assemblers_should_correctly_handle_no_assembler_found_from_merging() {
        LonelyRepresentation custoRep = new LonelyRepresentation();
        custoRep.setFirstName("epo.jemba@tata.com");
        custoRep.setLastName("na me"); // representation need a space

        underTest.createThenMergeAggregateWithDto(custoRep, Customer.class);
    }


    @Test(expected = SeedException.class)
    public void assemblers_should_correctly_handle_no_assembler_found_from_assembling() {


        assertThat(customerFactory).isNotNull();

        Customer client = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");

        assertThat(customerRepository).isNotNull();

        customerRepository.persist(client);

        Customer load = customerRepository.load(new CustomerId("epo@jemba.net"));
        assertThat(load).isNotNull();
        assertThat(load.getFullName()).isNotNull();
        assertThat(load.getFullName()).isEqualTo("jemba, epo");

        underTest.assembleDtoFromAggregate(LonelyRepresentation.class, load);
    }


    @Test
    public void aggregate_creation_with_factories_should_work_with_base_tuple_assemblers_list() {
        List<UseCase1Representation> dtos = Lists.newArrayListWithCapacity(100);

        for (int i = 0; i < 100; i++) {
            UseCase1Representation representation = new UseCase1Representation(
                    "activation" + i, "new activation description" + i,
                    "customer" + i, "new fname" + i, "new lname" + i,
                    "order" + i, "new description" + i,
                    (short) 1, productNamePolicy.transform((short) 1), "Awesome Product 2" + i);
            dtos.add(representation);
        }


        List<Quartet<Activation, Customer, Order, Product>> mergedEntities =
                underTest.createThenMergeAggregatesWithDto
                        (dtos, new TypeLiteral<Quartet<Activation,Customer,Order,Product>>() {
                        });

        assertThat(mergedEntities).isNotNull();
        for (int i = 0; i < 100; i++) {

            Assertions.assertThat(mergedEntities.get(i).getValue0()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue0().getDescription()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue0().getDescription()).isEqualTo("new activation description" + i);
            Assertions.assertThat(mergedEntities.get(i).getValue1()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue1().getFirstName()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue1().getFirstName()).isEqualTo("new fname" + i);
            Assertions.assertThat(mergedEntities.get(i).getValue1().getLastName()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue1().getLastName()).isEqualTo("new lname" + i);
            Assertions.assertThat(mergedEntities.get(i).getValue2()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue2().getDescription()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue2().getDescription()).isEqualTo("new description" + i);
            Assertions.assertThat(mergedEntities.get(i).getValue3()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue3().getDescription()).isNotNull();
            Assertions.assertThat(mergedEntities.get(i).getValue3().getDescription()).isEqualTo("Awesome Product 2" + i);
        }
    }


    @Test
    public void aggregate_retrieve_with_repositories_should_work_with_base_tuple_assemblers() throws ActivationException {
        Activation activation = activationFactory.createNewActivation("activation", "desc activation");
        Customer customer = customerFactory.createNewCustomer("customer", "fname", "lname");
        Order order = orderFactory.createOrder("order", "customer");
        order.setDescription("description");
        Product product = productFactory.createProduct((short) 1, (short) 1, "product", "Awesome Product");

        activationRepository.persist(activation);
        customerRepository.persist(customer);
        orderRepository.persist(order);
        productRepository.persist(product);

        UseCase1Representation representation = new UseCase1Representation(
                "activation", "new activation description",
                "customer", "new fname", "new lname",
                "order", "new description",
                (short) 1, productNamePolicy.transform((short) 1), "Awesome Product 2");

        Quartet<Activation, Customer, Order, Product> quartet = underTest.retrieveThenMergeAggregatesWithDto(
                representation,
                new TypeLiteral<Quartet<Activation,Customer,Order,Product>>() {
                }
        );


        Assertions.assertThat((Object)quartet).isNotNull();
        Assertions.assertThat(quartet.getValue0()).isNotNull();
        Assertions.assertThat(quartet.getValue0().getDescription()).isNotNull();
        Assertions.assertThat(quartet.getValue0().getDescription()).isEqualTo("new activation description");
        Assertions.assertThat(quartet.getValue1()).isNotNull();
        Assertions.assertThat(quartet.getValue1().getFirstName()).isNotNull();
        Assertions.assertThat(quartet.getValue1().getFirstName()).isEqualTo("new fname");
        Assertions.assertThat(quartet.getValue1().getLastName()).isNotNull();
        Assertions.assertThat(quartet.getValue1().getLastName()).isEqualTo("new lname");
        Assertions.assertThat(quartet.getValue2()).isNotNull();
        Assertions.assertThat(quartet.getValue2().getDescription()).isNotNull();
        Assertions.assertThat(quartet.getValue2().getDescription()).isEqualTo("new description");
        Assertions.assertThat(quartet.getValue3()).isNotNull();
        Assertions.assertThat(quartet.getValue3().getDescription()).isNotNull();
        Assertions.assertThat(quartet.getValue3().getDescription()).isEqualTo("Awesome Product 2");
    }

    @Ignore
    public static class RecursiveEntity implements AggregateRoot<String> {

        private String name;
        private Short age;
        private HolderForShort shortHolder;
        private RecursiveEntity entity;
        private String entityId;

        public RecursiveEntity(String entityId) {
            this.entityId = entityId;
        }

        @Override
        public String getEntityId() {
            return entityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Short getAge() {
            return age;
        }

        public void setAge(Short age) {
            this.age = age;
        }

        public RecursiveEntity getEntity() {
            return entity;
        }

        public void setEntity(RecursiveEntity entity) {
            this.entity = entity;
        }

        public HolderForShort getShortHolder() {
            return shortHolder;
        }

        public void setShortHolder(HolderForShort shortHolder) {
            this.shortHolder = shortHolder;
        }

    }

    public static class HolderForShort {
        private Short value;

        public Short getValue() {
            return value;
        }

        public void setValue(Short value) {
            this.value = value;
        }

    }


    @Test
    public void assemblers_should_create_aggregate_from_representation() {
        MyCustomerRepresentation custoRep = new MyCustomerRepresentation();
        custoRep.setId("epo.jemba@tata.com");
        custoRep.setName("Epo Jemba");

        Customer merged = underTest.createThenMergeAggregateWithDto(custoRep, Customer.class);

        assertThat(merged.getEntityId()).isNotNull();
        assertThat(merged.getEntityId().getCustomerId()).isEqualTo("epo.jemba@tata.com");
    }

    @Test
    public void assemblers_should_load_and_update_aggregates_from_representation() {

        assertThat(customerFactory).isNotNull();

        Customer client = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");

        assertThat(customerRepository).isNotNull();

        customerRepository.persist(client);

        Customer load = customerRepository.load(new CustomerId("epo@jemba.net"));
        assertThat(load).isNotNull();
        assertThat(load.getFullName()).isNotNull();
        assertThat(load.getFullName()).isEqualTo("jemba, epo");

        MyCustomerRepresentation custoRep = new MyCustomerRepresentation();
        custoRep.setId("epo@jemba.net");
        custoRep.setName("Epo Jemba");

        Customer merged = underTest.retrieveThenMergeAggregateWithDto(custoRep, Customer.class);

        assertThat(merged.getEntityId()).isNotNull();
        assertThat(merged.getFullName()).isEqualTo("Epo, Jemba");
    }


    @Test(expected = ReflectionError.class)
    public void assemblers_should_rethrow_the_good_functional_exception() {
        ActivationRepresentation ar = new ActivationRepresentation("42", "voyageur intergalactique");
        // this will create an exception inside the ActivationFactoryDefault
        Activation activation = underTest.createThenMergeAggregateWithDto(ar, Activation.class);
        activation.getEntityId();
    }

    @Test
    public void test_tuple() {
        Tuple genericTuple = Triplet.with("one", Integer.valueOf(2), Long.valueOf(3));

        assertThat(genericTuple.getSize()).isEqualTo(3);

        assertThat(genericTuple.getValue(0)).isExactlyInstanceOf(String.class);
        assertThat(genericTuple.getValue(1)).isExactlyInstanceOf(Integer.class);
        assertThat(genericTuple.getValue(2)).isExactlyInstanceOf(Long.class);
    }


}
