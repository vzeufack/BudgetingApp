package com.udemy.budgettingapp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.udemy.budgettingapp.domain.Budget;
import com.udemy.budgettingapp.domain.Group;
import com.udemy.budgettingapp.domain.User;
import com.udemy.budgettingapp.repositories.BudgetRepository;

@Service
public class BudgetService {
	@Autowired
	private BudgetRepository budgetRepo;
	
   public TreeSet<Budget> getBudgets(@AuthenticationPrincipal User user){
   	Set<User> users = new HashSet<>();
   	users.add(user);
   	
   	return budgetRepo.findByUsersIn(users);
   }
   
   public Budget saveBudget(User user, Budget budget) {   	
   	Set<User> users = new HashSet<>();
   	users.add(user);
   	budget.setUsers(users);
   	
   	long count = getBudgetCount(users);
   	budget.setName("new budget #" + ++count);
   	
   	Group grp = new Group();
   	grp.setName("Savings");
   	grp.setBudget(budget);
   	budget.getGroups().add(grp);
   	
   	Set<Budget> budgets = new HashSet<>();
   	budgets.add(budget);
   	user.setBudgets(budgets);
   	
   	return budgetRepo.save(budget);
   }

	private long getBudgetCount(Set<User> users) {
		return budgetRepo.countByUsersIn(users);
	}

	public Optional<Budget> findOne(Long budgetId) {
		return budgetRepo.findById(budgetId);
	}

	public LocalDate convertToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}
}
