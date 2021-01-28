package com.udemy.budgettingapp.repositories;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.budgettingapp.domain.Budget;
import com.udemy.budgettingapp.domain.User;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
   TreeSet<Budget> findByUsersIn(Set<User> users);
   long countByUsersIn(Set<User> users);
}
