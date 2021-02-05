package com.udemy.budgettingapp.web;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udemy.budgettingapp.domain.Budget;
import com.udemy.budgettingapp.domain.User;
import com.udemy.budgettingapp.service.BudgetService;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
	@Autowired
	BudgetService budgetService;
	
   @RequestMapping(value = "", method = RequestMethod.GET)
   public String getBudgets(@AuthenticationPrincipal User user, ModelMap model) {
   	
   	putsBudgetsOnModel(user, model);
   	return "budgets";
   }
   
   @GetMapping("{budgetId}")
   public String getBudget(@PathVariable Long budgetId, ModelMap model) {
   	Budget budget = budgetService.findOne(budgetId).get();
   	model.put("budget", budget);
   	
   	Boolean hasCategories = budget.getGroups().stream()
   			                  .filter(group -> group.getCategories().size() > 0)
   			                  .count() > 0;
   	model.put("hasCategories", hasCategories);
   	
   	return "budget";
   }

	private void putsBudgetsOnModel(User user, ModelMap model) {
		TreeSet<Budget> budgets = budgetService.getBudgets(user);
   	model.put("budgets", budgets);
	}
   
   @RequestMapping(value = "", method = RequestMethod.POST)
   public @ResponseBody Budget postBudget(@AuthenticationPrincipal User user, ModelMap model) {
   	  Budget budget = new Budget();   	
   	
      LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
   	  LocalDate lastOfMonth = LocalDate.now().withDayOfMonth(firstOfMonth.lengthOfMonth());   	
   	
   	  budget.setStartDate(firstOfMonth);
   	  budget.setEndDate(lastOfMonth);
   	  budget = budgetService.saveBudget(user, budget);
   	
      putsBudgetsOnModel(user, model);
   	
   	  return budget;
   }
   
   @PutMapping("{budgetId}")
   public @ResponseBody void putBudget(@AuthenticationPrincipal User user, @PathVariable Long budgetId,
   		@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
   	
   	Budget budget = budgetService.findOne(budgetId).get();
   	budget.setStartDate(budgetService.convertToDate(startDate));
   	budget.setEndDate(budgetService.convertToDate(endDate));
   	budgetService.saveBudget(user, budget);
   }
}
