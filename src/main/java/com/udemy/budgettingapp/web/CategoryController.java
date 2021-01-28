package com.udemy.budgettingapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.budgettingapp.domain.Category;
import com.udemy.budgettingapp.domain.Group;
import com.udemy.budgettingapp.service.CategoryService;
import com.udemy.budgettingapp.service.GroupService;

@Controller
@RequestMapping("/budgets/{budgetId}/groups/{groupId}/categories")
public class CategoryController {
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	CategoryService categoryService;
	
   @PostMapping("")
   public String postCategory(@PathVariable Long groupId, @PathVariable Long budgetId) {
   	Category category = new Category();
   	Group group = groupService.findById(groupId).get();
   	category.setGroup(group);
   	group.getCategories().add(category);
   	
   	category.setName("Test Category");
   	category = categoryService.saveCategory(category);
   	
   	return "redirect:/budgets/" + budgetId + "/groups/" + group.getId() + "/categories/" + category.getId();
   }
   
   @GetMapping("{categoryId}")
   public String getCategory(@PathVariable Long categoryId, ModelMap model) {
   	Category category = categoryService.findById(categoryId);
   	model.put("category", category);
   	model.put("group", category.getGroup());
   	return "category";
   }
   
   @PostMapping("{categoryId}")
   public String saveCategory(@PathVariable Long categoryId, @ModelAttribute Category category) {
   	Category dbCategory = categoryService.findById(categoryId);
   	dbCategory.setName(category.getName());
   	dbCategory.setBudget(category.getBudget());
   	
   	categoryService.saveCategory(dbCategory);
   	Long budgetId = dbCategory.getGroup().getBudget().getId();
		return "redirect:/budgets/" + budgetId;
   }
}
