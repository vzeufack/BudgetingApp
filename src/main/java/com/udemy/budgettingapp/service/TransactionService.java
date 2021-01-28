package com.udemy.budgettingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.budgettingapp.domain.Transaction;
import com.udemy.budgettingapp.repositories.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepo;
	
   public Transaction save (Transaction txn) {
   	return transactionRepo.save(txn);
   }

	public Transaction findById(Long transactionId) {
		return transactionRepo.findById(transactionId).get();
	}
}
