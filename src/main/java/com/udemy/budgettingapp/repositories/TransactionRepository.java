package com.udemy.budgettingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.budgettingapp.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
