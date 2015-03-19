/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
///*
// * Copyright (c) 2013 by PSA Peugeot Citroën. All rights reserved
// */
//package org.seedstack.seed.business.pagination;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//
//import org.seedstack.seed.business.internal.Holder;
//import org.seedstack.seed.business.sample.domain.activation.Activation;
//
//public class EndToEndPaginationIT {
//
//	Kernel underTest;
//
//	@Before
//	public void beforeClass() {
//		underTest = Kernel.createKernel() //
//				.build(); //
//		underTest.init(); //
//		underTest.start(); //
//		holder = new Holder();
//		underTest.getMainInjector().injectMembers(holder);
//		
////		txManager = underTest.getMainInjector().getInstance(TransactionManager.class).getTransactionHandler(JpaTransactionHandler.class);
//		
//		initData4Scenarios();
//
//	}
//	
//
////	private EntityTransaction currentTransaction;
////
////	private JpaTransactionHandler txManager;
//	
//    void ______startTx____________________________()
//    {
////        txManager.doInitialize();
////    	currentTransaction = txManager.doCreateTransaction();
////		txManager.doBeginTransaction(currentTransaction);
//    }
//
//    void ______stopTx_______________________________()
//    {
////		txManager.doCommitTransaction(currentTransaction);		
////		txManager.doReleaseTransaction(currentTransaction);
////        txManager.doCleanup();
//		
//    }
//    void _______stopTxNoCommit__________________________()
//    {
////    	txManager.doReleaseTransaction(currentTransaction);
////        txManager.doCleanup();
//    }
//	
//    private void initData4Scenarios() {
//    	
//		// fill the database with 1111 Activations
//    	______startTx____________________________();
//    	
//    	for (int i = 0; i < 1111; i++) {
//    		Activation activation = holder.activationFactory.createNewActivation("activation-" + i, "description-"+i);
//    		holder.activationRepository.persist(activation);
//		}   					
//    	______stopTx_______________________________();
//    	
//    }
//	
//	@After
//    public  void afterClass() {
//        underTest.stop();
//    }
//	
//	Holder holder;
//	
//	class PaginationRequest
//	{
//		int pageSize = 10;
//		/** starting from 1 */
//		int pageNumber = 1;
//	}
//	
//	@Test
//	public void scenario1_nominal_pagination ()
//	{
//		
//		PaginationRequest pRequest = new PaginationRequest();
//		
//		
//		
//		
//	}
//
//	
//	
//}
