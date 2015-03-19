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

import com.google.common.collect.Lists;
import com.google.inject.TypeLiteral;
import org.assertj.core.api.Assertions;
import org.javatuples.Quartet;
import org.junit.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.powermock.reflect.Whitebox;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Repositories;
import org.seedstack.business.api.interfaces.assembler.*;
import org.seedstack.business.sample.domain.activation.Activation;
import org.seedstack.business.sample.domain.activation.ActivationException;
import org.seedstack.business.sample.domain.activation.ActivationFactory;
import org.seedstack.business.sample.domain.activation.ActivationRepository;
import org.seedstack.business.sample.domain.customer.*;
import org.seedstack.business.sample.domain.multi.Multi;
import org.seedstack.business.sample.domain.order.Order;
import org.seedstack.business.sample.domain.order.OrderFactory;
import org.seedstack.business.sample.domain.order.OrderRepository;
import org.seedstack.business.sample.domain.product.Product;
import org.seedstack.business.sample.domain.product.ProductFactory;
import org.seedstack.business.sample.domain.product.ProductNamePolicy;
import org.seedstack.business.sample.domain.product.ProductRepository;
import org.seedstack.business.sample.interfaces.activation.ActivationRepresentation;
import org.seedstack.business.sample.interfaces.multi.MultiRepresentation;
import org.seedstack.business.sample.interfaces.usecase1.UseCase1Assembler;
import org.seedstack.business.sample.interfaces.usecase1.UseCase1Representation;
import org.seedstack.seed.core.api.Ignore;
import org.seedstack.seed.core.api.SeedException;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.persistence.inmemory.api.Store;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class AssemblersIT extends AbstractSeedIT {

    @Inject
	Assemblers assemblers;
	
	@Inject
	Repositories repositories;
    // Activation,Customer,Order,Product

    @Inject
    ActivationFactory activationFactory;

    @Inject
	ActivationRepository activationRepository;

    @Inject
	CustomerFactory customerFactory ;

    @Inject
	CustomerRepository customerRepository;

    @Inject
	OrderFactory orderFactory ;

    @Inject
	OrderRepository orderRepository;

    @Inject
	ProductFactory productFactory ;

    @Inject
    ProductRepository productRepository;

	@Inject
	MyCustomerAssembler assembler;

	@Inject
	UseCase1Assembler uc1Assembler;

	@Test
	public void assert_repositories_factories () {
		assertThat(activationFactory).isNotNull();
		assertThat(activationRepository).isNotNull();
		assertThat(customerFactory).isNotNull();
		assertThat(customerRepository).isNotNull();
		assertThat(orderFactory).isNotNull();
		assertThat(orderRepository).isNotNull();
		assertThat(productFactory).isNotNull();
		assertThat(productRepository).isNotNull();

	}

    @Test
    public void my_default_assembler_should_be_bound ()
    {
    	assertThat(assembler).isNotNull();
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void my_usecase1_assembler_should_be_bound () {
        assertThat(uc1Assembler).isNotNull();

    	Whitebox.getInternalState(assemblers, "assemblers");
    }

    private Quartet<Activation, Customer, Order, Product> initialize_quartet (String suffix) {
        Objenesis objenesis = new ObjenesisStd();

    	Activation activation;
    	Customer customer;
    	Order order;
    	Product product;
    	{
    		 // Activation //
	    	ObjectInstantiator activationInstantiator = objenesis.getInstantiatorOf(Activation.class);
			activation = (Activation) activationInstantiator.newInstance();
			Whitebox.setInternalState(activation, "entityId", suffix);
	    	activation.setDescription("activation"+suffix);
	    	Assertions.assertThat(activation.getEntityId()).isEqualTo(suffix);
    	}
    	{
    		// Customer  //
    		ObjectInstantiator customerInstantiator = objenesis.getInstantiatorOf(Customer.class);
			customer = (Customer) customerInstantiator.newInstance();
    		customer.setFirstName("fistname"+suffix);
    		customer.setLastName("lastname"+suffix);
    	}
    	{
    		// Order
    		ObjectInstantiator orderInstantiator = objenesis.getInstantiatorOf(Order.class);
			order = (Order) orderInstantiator.newInstance();
    		order.setDescription("order"+suffix);
    	}
    	{
    		// Product
    		ObjectInstantiator productInstantiator = objenesis.getInstantiatorOf(Product.class);
			product = (Product) productInstantiator.newInstance();
    		product.setName("name"+suffix);
    		product.setDescription("product"+suffix);
    	}


    	return Quartet.with(activation, customer, order, product);

    }

    @Test
    public void basetupleassembler_should_be_injected_and_work () {

        Quartet<Activation,Customer,Order,Product> aggregateRootsInitialized = initialize_quartet("1");
    	//
    	Activation activation = aggregateRootsInitialized.getValue0();
		assertThat(activation).isNotNull();
    	assertThat(activation.getDescription()).isEqualTo("activation1");

    	UseCase1Representation useCase1Representation = uc1Assembler.assembleDtoFromAggregate(aggregateRootsInitialized);

    	assertThat(useCase1Representation).isNotNull();
    	assertThat(useCase1Representation.getActivationDescription()).isEqualTo("activation1");

    }

    @Test
    public void assemblers_should_work_with_basetupleassemblers () {

        Quartet<Activation,Customer,Order,Product> aggregateRootsInitialized = initialize_quartet("1");
    	UseCase1Representation assembledDtoFromEntities = assemblers.assembleDtoFromAggregates(UseCase1Representation.class, aggregateRootsInitialized);

    	assertThat(assembledDtoFromEntities).isNotNull();
    	assertThat(assembledDtoFromEntities.getActivationDescription()).isEqualTo("activation1");

    }

    @Test
    public void assemblers_should_work_with_basetupleassemblers_list () {
        List<Quartet<Activation,Customer,Order,Product>> aggregateRootsInitializedList = Lists.newArrayListWithCapacity(100);

    	for (int i = 0 ; i < 100 ; i++) {
            Quartet<Activation,Customer,Order,Product> aggregateRootsInitialized = initialize_quartet(""+i);
    		aggregateRootsInitializedList.add(aggregateRootsInitialized);
    	}

    	List<UseCase1Representation> assembledDtoFromEntities =  assemblers.assembleDtoFromAggregates(UseCase1Representation.class,  aggregateRootsInitializedList);
    	assertThat(assembledDtoFromEntities).isNotNull();
    	for (int i = 0 ; i < assembledDtoFromEntities.size() ; i++) {
            assertThat(assembledDtoFromEntities.get(i).getActivationDescription()).isEqualTo("activation"+i);
    	}
    }

    @Test
    @Store("aggregate_creation_with_factories_should_work_with_base_tuple_assemblers_01")
    public void  aggregate_creation_with_factories_should_work_with_base_tuple_assemblers  () {
        UseCase1Representation representation = new UseCase1Representation(
    			"activation",  "new activation description",
    			"customer", "new fname" , "new lname",
    			"order",  "new description" ,
    			(short)1  , productNamePolicy.transform ((short)1)  ,  "Awesome Product 2" );


    	Quartet<Activation,Customer,Order,Product> mergedEntities =
    			assemblers.createThenMergeAggregatesWithDto
    			(representation, new TypeLiteral<Quartet<Activation,Customer,Order,Product>>(){});

    	assertThat(mergedEntities).isNotNull();

     	Assertions.assertThat(mergedEntities.getValue0()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue0().getDescription()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue0().getDescription()).isEqualTo("new activation description");
    	Assertions.assertThat(mergedEntities.getValue1()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue1().getFirstName()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue1().getFirstName()).isEqualTo("new fname");
    	Assertions.assertThat(mergedEntities.getValue1().getLastName()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue1().getLastName()).isEqualTo("new lname");
    	Assertions.assertThat(mergedEntities.getValue2()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue2().getDescription()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue2().getDescription()).isEqualTo("new description");
    	Assertions.assertThat(mergedEntities.getValue3()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue3().getDescription()).isNotNull();
    	Assertions.assertThat(mergedEntities.getValue3().getDescription()).isEqualTo("Awesome Product 2");
    }

    @Test
    @Store("assemblers_should_work_with_dtos_returning_nulls")
    public void assemblers_should_work_with_dtos_returning_nulls () {
		MyCustomerRepresentation custoRep = new MyCustomerRepresentation();
		custoRep.setId(null);
		custoRep.setName(null);

		Customer merged = assemblers.createThenMergeAggregateWithDto(custoRep , Customer.class );

		assertThat(merged.getEntityId()).isNotNull();
		assertThat(merged.getEntityId().getCustomerId()).isEqualTo(null);
    }
    
    @Test
    @Store("assemblers_should_work_with_entities_returning_nulls")
    public void assemblers_should_work_with_entities_returning_nulls () {
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

		Customer merged = assemblers.retrieveThenMergeAggregateWithDto(custoRep , Customer.class );

		assertThat(merged.getEntityId()).isNotNull();
		assertThat(merged.getFullName()).isEqualTo("null, null");
    }
    
    @Test
    @Store("assemblers_should_work_with_inherited_dtos_1")
    public void assemblers_should_work_with_inherited_dtos_1 () {
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

		Customer merged = assemblers.retrieveThenMergeAggregateWithDto(custoRep, Customer.class);

		assertThat(merged.getEntityId()).isNotNull();
		assertThat(merged.getFullName()).isEqualTo("Epo, Jemba");
    }
    
    @Test
    @Store("assemblers_should_work_with_inherited_dtos_2")
    public void assemblers_should_work_with_inherited_dtos_2 () {
    	VipRepresentation custoRep = new VipRepresentation();
    	custoRep.setId("epo.jemba@tata.com");
    	custoRep.setName("na me"); // representation need a space
    	
    	Customer merged = assemblers.createThenMergeAggregateWithDto(custoRep , Customer.class );
    	
    	assertThat(merged.getEntityId()).isNotNull();
    	assertThat(merged.getEntityId().getCustomerId()).isEqualTo("epo.jemba@tata.com");
    }
    
    @Test(expected = SeedException.class)
    @Store("assemblers_should_correctly_handle_no_assembler_found_from_merging")
    public void assemblers_should_correctly_handle_no_assembler_found_from_merging () {
    	LonelyRepresentation custoRep = new LonelyRepresentation();
    	custoRep.setFirstName("epo.jemba@tata.com");
    	custoRep.setLastName("na me"); // representation need a space
    	
    	assemblers.createThenMergeAggregateWithDto(custoRep , Customer.class );
    }
    
    
    @Test(expected = SeedException.class)
    @Store("assemblers_should_work_with_inherited_dtos_1")
    public void assemblers_should_correctly_handle_no_assembler_found_from_assembling () {
    	assertThat(customerFactory).isNotNull();

		Customer client = customerFactory.createNewCustomer("epo@jemba.net", "epo", "jemba");

		assertThat(customerRepository).isNotNull();

		customerRepository.persist(client);

		Customer load = customerRepository.load(new CustomerId("epo@jemba.net"));
		assertThat(load).isNotNull();
		assertThat(load.getFullName()).isNotNull();
		assertThat(load.getFullName()).isEqualTo("jemba, epo");
		
		assemblers.assembleDtoFromAggregate(LonelyRepresentation.class, load);
    }
    
    
    
    
    @Test
    @Store("aggregate_creation_with_factories_should_work_with_base_tuple_assemblers_02")
    public void  aggregate_creation_with_factories_should_work_with_base_tuple_assemblers_list  () {
        List<UseCase1Representation> dtos = Lists.newArrayListWithCapacity(100);

    	for (int i = 0 ; i < 100 ; i++) {
            UseCase1Representation representation = new UseCase1Representation(
    				"activation"+i,  "new activation description"+i,
    				"customer"+i, "new fname"+i , "new lname"+i,
    				"order"+i,  "new description" +i,
    				(short)1  , productNamePolicy.transform ((short)1)  ,  "Awesome Product 2"+i );
    		dtos.add(representation);
    	}

    	List<Quartet<Activation,Customer,Order,Product>> mergedEntities =
    			assemblers.createThenMergeAggregatesWithDto
    			(dtos, new TypeLiteral<Quartet<Activation,Customer,Order,Product>>(){});

    	assertThat(mergedEntities).isNotNull();
    	for (int i = 0 ; i < 100 ; i++) {
            Assertions.assertThat(mergedEntities.get(i).getValue0()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue0().getDescription()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue0().getDescription()).isEqualTo("new activation description"+i);
    		Assertions.assertThat(mergedEntities.get(i).getValue1()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue1().getFirstName()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue1().getFirstName()).isEqualTo("new fname"+i);
    		Assertions.assertThat(mergedEntities.get(i).getValue1().getLastName()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue1().getLastName()).isEqualTo("new lname"+i);
    		Assertions.assertThat(mergedEntities.get(i).getValue2()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue2().getDescription()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue2().getDescription()).isEqualTo("new description"+i);
    		Assertions.assertThat(mergedEntities.get(i).getValue3()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue3().getDescription()).isNotNull();
    		Assertions.assertThat(mergedEntities.get(i).getValue3().getDescription()).isEqualTo("Awesome Product 2"+i);
    	}


    }

    @Inject
    ProductNamePolicy productNamePolicy;


    @Test @Store("store_tuple_01") @Transactional
    public void  aggregate_retrieve_with_repositories_should_work_with_base_tuple_assemblers  () throws ActivationException
    {
    	Activation activation = activationFactory.createNewActivation("activation", "desc activation");
    	Customer customer = customerFactory.createNewCustomer("customer", "fname", "lname" );
    	Order order = orderFactory.createOrder("order", "customer");
    	order.setDescription("description");
    	Product product = productFactory.createProduct( (short)1, (short)1 , "product" , "Awesome Product");

    	activationRepository.persist(activation);
    	customerRepository.persist(customer);
    	orderRepository.persist(order);
    	productRepository.persist(product);

    	UseCase1Representation representation = new UseCase1Representation(
    			"activation",  "new activation description",
    			"customer", "new fname" , "new lname",
    			"order",  "new description" ,
    			(short)1  , productNamePolicy.transform ((short)1)  ,  "Awesome Product 2" );

    	Quartet<Activation, Customer, Order, Product> quartet = assemblers.retrieveThenMergeAggregatesWithDto(
    			representation,
    			new TypeLiteral<Quartet<Activation,Customer,Order,Product>>(){}
    			);


    	Assertions.assertThat(quartet).isNotNull();
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

	@Test @Transactional
	@Store("store01")
	public void assemblers_should_create_aggregate_from_representation() {
		MyCustomerRepresentation custoRep = new MyCustomerRepresentation();
		custoRep.setId("epo.jemba@tata.com");
		custoRep.setName("Epo Jemba");

		Customer merged = assemblers.createThenMergeAggregateWithDto(custoRep , Customer.class );

		assertThat(merged.getEntityId()).isNotNull();
		assertThat(merged.getEntityId().getCustomerId()).isEqualTo("epo.jemba@tata.com");
	}

	@Test @Transactional
	@Store("store02")
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

		Customer merged = assemblers.retrieveThenMergeAggregateWithDto(custoRep , Customer.class );

		assertThat(merged.getEntityId()).isNotNull();
		assertThat(merged.getFullName()).isEqualTo("Epo, Jemba");
	}

	@Test(expected=ActivationException.class)
	public void assemblers_should_rethrow_the_good_functional_exception () {
		ActivationRepresentation ar = new ActivationRepresentation("42", "voyageur intergalactique");
		// this will create an exception inside the ActivationFactoryDefault
		Activation activation = assemblers.createThenMergeAggregateWithDto(ar, Activation.class);
		activation.getEntityId();
	}
	
	@Test(expected=SeedException.class)
	@Transactional
	@Store("MultiRepository2InMemory")
	public void multiple_repository_implementation_for_aggregate(){
		assemblers.retrieveThenMergeAggregateWithDto(new MultiRepresentation("test"), Multi.class);
	}
	
	@Test(expected=SeedException.class)
	public void multiple_repository_implementation_for_aggregate2() {
		repositories.get(Multi.class).getClass();
	}
}
