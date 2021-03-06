package com.udemy.budgettingapp.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.budgettingapp.domain.Budget;
import com.udemy.budgettingapp.domain.Category;
import com.udemy.budgettingapp.domain.Transaction;
import com.udemy.budgettingapp.dto.CategoryDto;
import com.udemy.budgettingapp.service.BudgetService;
import com.udemy.budgettingapp.service.CategoryService;
import com.udemy.budgettingapp.service.TransactionService;

@Controller
@RequestMapping(value= {"/budgets/{budgetId}/groups/{groupId}/categories/{categoryId}/transactions",
		                  "/budgets/{budgetId}/transactions"})
public class TransactionController {
	@Autowired
	BudgetService budgetService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("")
	public String addTransaction(@PathVariable Long budgetId, @PathVariable(required = false) Long groupId, 
			                       @PathVariable(required = false) Long categoryId) {
		String returnUrl = "redirect:/budgets/" + budgetId;
		Transaction txn = new Transaction();
		Budget budget = budgetService.findOne(budgetId).get();
		
		txn.setBudget(budget);
		budget.getTransactions().add(txn);		
		
		txn.setDate(LocalDate.now());
		
		if (categoryId != null) {			
			Category category = categoryService.findById(categoryId);
			txn.setCategory(category);
			category.getTransactions().add(txn);
			returnUrl += "/groups/" + groupId + "/categories/" + categoryId;
		}
		
		txn = transactionService.save(txn);
		
		return returnUrl + "/transactions/" + txn.getId();
	}
	
	@GetMapping("{transactionId}")
	public String getTransaction(@PathVariable Long transactionId, ModelMap model) {
		Transaction txn = transactionService.findById(transactionId);
		model.put("transaction", txn);
		model.put("budget", txn.getBudget());
		
		List<CategoryDto> categories = txn.getBudget().getGroups()
									                  .stream()
									                  .map(group -> group.getCategories())
									                  .flatMap(Collection::stream)
									                  .map(category -> new CategoryDto(category.getId().toString(), category.getName()))
									                  .collect(Collectors.toList());
	    model.put("categories", categories);		
		return "transaction";
	}
	
	@PostMapping("{transactionId}")
	public String postTransaction(@ModelAttribute Transaction transaction, @PathVariable Long transactionId) {
		transactionService.save(transaction);
		return "redirect:/budgets/" + transaction.getBudget().getId();
	}
}
