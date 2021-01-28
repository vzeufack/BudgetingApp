package com.udemy.budgettingapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.budgettingapp.domain.Budget;
import com.udemy.budgettingapp.domain.Group;
import com.udemy.budgettingapp.repositories.GroupRepository;

@Service
public class GroupService {
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private GroupRepository groupRepo;
	
   public Group createGroup(Long budgetId) {
   	Group group = new Group();
   	Budget budget = budgetService.findOne(budgetId).get();
   	group.setBudget(budget);
   	budget.getGroups().add(group);
   	
   	return groupRepo.save(group);
   }
   
   public Optional<Group> findById(Long groupId) {
   	return groupRepo.findById(groupId);
   }

	public Group save(Group group) {
		return groupRepo.save(group);	
	}
}
