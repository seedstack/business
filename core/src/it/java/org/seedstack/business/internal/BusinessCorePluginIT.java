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

import org.seedstack.business.api.interfaces.assembler.MyCustomerRepresentation;
import org.seedstack.business.sample.application.internal.IndexServiceInternal;
import org.seedstack.business.sample.domain.activation.Activation;
import org.seedstack.business.sample.domain.activation.ActivationException;
import org.seedstack.business.sample.domain.customer.Customer;
import org.seedstack.business.sample.domain.customer.CustomerFactoryDefault;
import org.seedstack.business.sample.domain.customer.CustomerId;
import org.seedstack.business.sample.domain.customer.internal.CustomerSampleServiceInternal;
import org.seedstack.business.sample.domain.order.Order;
import org.seedstack.business.sample.domain.order.OrderFactoryDefault;
import org.seedstack.business.sample.domain.order.OrderId;
import org.seedstack.business.sample.domain.product.InternalProductFactory;
import org.seedstack.business.sample.domain.product.InternalProductNamePolicy;
import org.seedstack.business.sample.domain.product.Product;
import org.seedstack.business.sample.domain.product.ProductId;
import org.seedstack.business.sample.infrastructure.presentation.customer.JpaCustomerFinder;
import org.seedstack.business.sample.interfaces.activation.ActivationRepresentation;
import org.seedstack.business.sample.interfaces.customer.presentation1.CustomerRepresentation;
import org.seedstack.business.sample.interfaces.product.presentation2.ProductWSFacade;
import org.seedstack.business.sample.interfaces.product.presentationsimple1.ProductSimple1Representation;
import org.seedstack.business.sample.interfaces.product.presentationsimple2.ProductSimple2Representation;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.persistence.inmemory.api.Store;
import org.seedstack.seed.security.api.WithUser;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BusinessCorePluginIT extends AbstractSeedIT {
    @Inject
    Holder holder;
	
    
    void ______startTx_______________________________________________________________________________()
    {
//        txManager.doInitialize(null);
//    	currentTransaction = txManager.doCreateTransaction();
//		txManager.doBeginTransaction(currentTransaction);
    }

    void ______stopTx________________________________________________________________________________()
    {
//		txManager.doCommitTransaction(currentTransaction);
//		txManager.doReleaseTransaction(currentTransaction);
//        txManager.doCleanup();
    }
    void _______stopTxNoCommit_______________________________________________________________________()
    {
//    	txManager.doReleaseTransaction(currentTransaction);
//        txManager.doCleanup();
    }
	
	@Test
	@Store("business_core_plugin_it_01")
	@WithUser(id = "Anakin", password = "imsodark" )
	public void all_bindings_should_be_bound_________________________________________________________() throws ActivationException {
		assertThat(holder.property()).isNotNull();
		assertThat(holder.property()).isEqualTo("toto");

		assertThat(holder.activationRepository).isNotNull();
		assertThat(holder.activationFactory).isNotNull();
        assertThat(holder.activationFactory.createNewActivation("id", "pok")).isNotNull();
		
		//
		assertThat(holder.repos).isNotNull();
		assertThat( holder. factories ).isNotNull();
		assertThat( holder. assemblers).isNotNull();
		
		assertThat( holder. customerRepo ).isNotNull   ();
		assertThat( holder. customerFactory).isNotNull ();
		assertThat( CustomerFactoryDefault.class.isAssignableFrom( holder.customerFactory.getClass())).isTrue();
		
		assertThat( holder. orderRepo ).isNotNull      ();
		assertThat( holder. orderFactory).isNotNull    ();
		assertThat( OrderFactoryDefault.class.isAssignableFrom( holder.orderFactory.getClass())).isTrue();
		
		assertThat( holder. productRepo ).isNotNull      ();
		assertThat( holder. productFactory).isNotNull    ();
		assertThat( InternalProductFactory.class.isAssignableFrom( holder.productFactory.getClass())).isTrue();
		assertThat( holder. productNamePolicy ).isNotNull   ();
		assertThat( InternalProductNamePolicy.class.isAssignableFrom( holder.productNamePolicy.getClass())).isTrue();
		

		// Application Service
		assertThat(holder.indexService).isNotNull();
		assertThat( IndexServiceInternal.class.isAssignableFrom( holder.indexService.getClass())).isTrue();

		// Domain Service
		assertThat(holder.customerService).isNotNull();
		assertThat( CustomerSampleServiceInternal.class.isAssignableFrom( holder.customerService.getClass())).isTrue();

		// Finder
		assertThat(holder.customerFinder).isNotNull();
		assertThat( JpaCustomerFinder.class.isAssignableFrom(holder.customerFinder.getClass()) ).isTrue();
		
		// Functional Cases
		
		// Create customers and launch services
		Customer testCusto = holder.customerFactory.createNewCustomer("key1" , "test1", "");
		Customer c2 = holder.customerFactory.createNewCustomer("2", "f1", "l1");
		
		holder.indexService.index(testCusto);
		holder.customerService.transfer(testCusto, c2);
		
		assertThat(holder.customerService.property()).isNotNull();
		assertThat(holder.customerService.property()).isEqualTo("toto");
		
		// Product creation
		Product product1 = holder.productFactory.createProduct( (short)1, (short)1);
		product1.setName("Basket Air Jordan");
		product1.setDescription("Da Pair of basket evaa!");
		
		
		Product product2 = holder.productFactory.createProduct( (short)2, (short)5);
		product2.setName("Tea mandarin tetley");
		product2.setDescription("White tea for digestion.");
		
		______startTx_______________________________________________________________________________ ();
		holder.productRepo.persist(product1);
		holder.productRepo.persist(product2);
		______stopTx________________________________________________________________________________ ();
		
		
		______startTx_______________________________________________________________________________ ();
		Product loadedProduct1 = holder.productRepo.load(new ProductId((short) 1, "ean13-1"));
		Product loadedProduct2 = holder.productRepo.load(new ProductId((short) 2, (short)5));
		______stopTx________________________________________________________________________________ ();

		assertThat(loadedProduct1.getEntityId().getStoreId()).isEqualTo((short) 1);
		assertThat(loadedProduct1.getEntityId().getProductCode()).isEqualTo( "ean13-1");
		assertThat(loadedProduct1.getEntityId()).isEqualTo(new ProductId((short) 1, "ean13-1"));
		assertThat(loadedProduct1.getName()).isEqualTo("Basket Air Jordan");
		assertThat(loadedProduct1.getDescription()).isEqualTo("Da Pair of basket evaa!");

		assertThat(loadedProduct2.getEntityId().getStoreId()).isEqualTo((short) 2);
		assertThat(loadedProduct2.getEntityId().getProductCode()).isEqualTo( "ean13-5");
		assertThat(loadedProduct2.getEntityId()).isEqualTo(new ProductId((short) 2, "ean13-5"));
		assertThat(loadedProduct2.getName()).isEqualTo("Tea mandarin tetley");
		assertThat(loadedProduct2.getDescription()).isEqualTo("White tea for digestion.");
		
		
		ProductSimple1Representation productRepresentationToWeb = holder.assemblers.assembleDtoFromAggregate(ProductSimple1Representation.class, loadedProduct1);
		assertThat(productRepresentationToWeb).isNotNull();
		assertThat(productRepresentationToWeb.getId()).isNotNull();
		assertThat(productRepresentationToWeb.getId()).isEqualTo("1-1");
		
		
//		______startTx_______________________________________________________________________________ ();
//		Product refOnProduct = holder.entityManager.getReference(Product.class, new ProductId((short) 1, "ean13-1"));
//		refOnProduct.setDescription("Da Pair of basket evaa! +++");
//		______stopTx________________________________________________________________________________ ();
		
//		______startTx_______________________________________________________________________________ ();
//		loadedProduct1 = holder.productRepo.load(new ProductId((short) 1, "ean13-1"));
//		______stopTx________________________________________________________________________________ ();
//
//		assertThat(loadedProduct1.getDescription()).isEqualTo("Da Pair of basket evaa! +++");
		
		
		______startTx_______________________________________________________________________________ ();
		{
			// Assembler should modify matching entities from persistence
			// Here we match ProductRepresentation methods to ProductId constructor
			
			ProductWSFacade productWSFacade = new ProductWSFacade((short)2, (short)5, "Tea mandarin tetley. Update from WS.", "White tea for digestion. Update from WS." );
			
			Product mergedProduct = holder.assemblers.retrieveThenMergeAggregateWithDto(productWSFacade, Product.class);
			
			assertThat( mergedProduct).isNotNull();
			assertThat( mergedProduct.getName()).isNotNull();
			assertThat( mergedProduct.getName()).isEqualTo("Tea mandarin tetley. Update from WS.");
			assertThat( mergedProduct.getDescription()).isEqualTo("White tea for digestion. Update from WS.");
		}
		______stopTx________________________________________________________________________________ ();
		
		______startTx_______________________________________________________________________________ ();
		{
			// Assembler should modify matching entities from persistence
			// Here we match ProductRepresentation methods to ProductId constructor
			ProductSimple1Representation productRepresentationFromWeb = new ProductSimple1Representation((short)1, (short) 1, "Basket Air Jordan++", "Da Pair of basket evaa!++" );
			
			Product mergedProduct = holder.assemblers.retrieveThenMergeAggregateWithDto(productRepresentationFromWeb, Product.class);
			
			assertThat( mergedProduct).isNotNull();
			assertThat( mergedProduct.getName()).isNotNull();
			assertThat( mergedProduct.getName()).isEqualTo("Basket Air Jordan++");
			assertThat( mergedProduct.getDescription()).isEqualTo("Da Pair of basket evaa!++");
		}
		______stopTx________________________________________________________________________________ ();
		
		// Assembler should create entities if specified //
		______startTx_______________________________________________________________________________ ();
		{ // Case 1
			ProductSimple1Representation productRepresentationFromWeb = new ProductSimple1Representation((short)10, (short)10, "Mouse Logitech", "The mouse!" );
			Product createdProduct = holder.assemblers.createThenMergeAggregateWithDto(productRepresentationFromWeb, Product.class);
			assertThat( createdProduct).isNotNull();
			assertThat( createdProduct.getName()).isNotNull();
			assertThat( createdProduct.getName()).isEqualTo( "Mouse Logitech" );
			assertThat( createdProduct.getDescription()).isEqualTo( "The mouse!" );
		}
		{ // Case 2
			ProductSimple2Representation productRepresentationFromWeb = new ProductSimple2Representation((short)11, "ean13-11", "Screen ThinkVision", "The screen!" );
			Product createdProduct = holder.assemblers.createThenMergeAggregateWithDto(productRepresentationFromWeb, Product.class);
			assertThat( createdProduct).isNotNull();
			assertThat( createdProduct.getName()).isNotNull();
			assertThat( createdProduct.getName()).isEqualTo( "Screen ThinkVision" );
			assertThat( createdProduct.getDescription()).isEqualTo( "The screen!" );
		}
		______stopTx________________________________________________________________________________ ();
		
		
		assertThat(loadedProduct1).isNotNull();
		assertThat(loadedProduct1.getEntityId().getStoreId()).isEqualTo((short)1);
		assertThat(loadedProduct1.getEntityId().getProductCode()).isEqualTo("ean13-1");
		
		
		Order order1 = holder.orderFactory.createOrder("commande01");
		OrderId oi1 = order1.getEntityId();
		assertThat(oi1).isEqualTo(new OrderId("commande01"));
		order1.setCustomerId(c2.getEntityId());
		
		// Assemblers
		MyCustomerRepresentation customRepresentation = holder.assemblers.assembleDtoFromAggregate(MyCustomerRepresentation.class, c2);
		
		assertThat(customRepresentation.getId()).isEqualTo("2");
		assertThat(customRepresentation.getName()).isEqualTo("l1, f1");
		
		
		
		// persist
		______startTx_______________________________________________________________________________();
		holder.customerRepo.persist(testCusto);
		holder.orderRepo.persist(order1);
		______stopTx________________________________________________________________________________();
		
		// find
		______startTx_______________________________________________________________________________();
		List<CustomerRepresentation> findAll = holder.customerFinder.findAll();
		_______stopTxNoCommit_______________________________________________________________________();
		
		// check
		assertThat(findAll).isNotNull();
		System.err.println("findall => " + findAll);
		assertThat(findAll).hasSize(1);
        // persist
	
		______startTx_______________________________________________________________________________();
		holder.customerRepo.persist(c2);

		Order load2_ = holder.orderRepo.load(new OrderId("commande01"));
		assertThat(load2_).isNotNull();
		
		Customer custLoadedKey1  = holder.customerRepo.load(new CustomerId("key1"));
		assertThat(custLoadedKey1).isNotNull();
		
		assertThat(load2_.getEntityId().getValue()).isEqualTo(new OrderId("commande01").getValue());
		assertThat(load2_.getCustomerId()).isEqualTo(new CustomerId("2"));
		______stopTx________________________________________________________________________________();
		
		// find
		______startTx_______________________________________________________________________________();
		findAll = holder.customerFinder.findAll();
		// check
		assertThat(findAll).isNotNull();
		assertThat(findAll).hasSize(2);
//		assertThat(findAll).
//		assertThat(findAll).onProperty("name").contains("f1 l1" , "test1 "); // TODO : to re set when fest-util 1.2.4 and 1.1.6 is resolved
		_______stopTxNoCommit_______________________________________________________________________();
		
		CustomerId entityId = new CustomerId("key1");
		______startTx_______________________________________________________________________________();
		{
			Customer load = holder.customerRepo.load(entityId);
	    	assertThat( load ).isNotNull();
		}
		_______stopTxNoCommit_______________________________________________________________________();
		______startTx_______________________________________________________________________________();
		{
			entityId = new CustomerId("2");
			Customer load = holder.customerRepo.load(entityId);
			assertThat( load ).isNotNull();
		}
		_______stopTxNoCommit_______________________________________________________________________();
		String uuid;
		______startTx_______________________________________________________________________________();
		{
			uuid = UUID.randomUUID().toString();
			Activation activation;
			try {
				activation = holder.activationFactory.createNewActivation(uuid, "new activation");
				activation.setActivationDate(new Date());
				activation.setCreationDate(new Date());
				
				holder.activationRepository.persist(activation);
			} catch (ActivationException e) {
				e.printStackTrace();
			}
		}
		______stopTx________________________________________________________________________________();
		______startTx_______________________________________________________________________________();
		{
			Activation activation = holder.activationRepository.load(uuid);
			
			assertThat(activation).isNotNull();
			
			ActivationRepresentation rep = new ActivationRepresentation(uuid , "e364467");
			
			activation = holder.assemblers.retrieveThenMergeAggregateWithDto(rep, Activation.class);
			
			Assertions.assertThat(activation.getCustomerId().getCustomerId()).isEqualTo("e364467");

		}
		______stopTx________________________________________________________________________________();


	}

}
