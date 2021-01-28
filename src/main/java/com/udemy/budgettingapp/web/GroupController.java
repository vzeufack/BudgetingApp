package com.udemy.budgettingapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.budgettingapp.domain.Group;
import com.udemy.budgettingapp.service.GroupService;

@Controller
@RequestMapping("/budgets/{budgetId}/groups")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@PostMapping("")
   public String postGroups(@PathVariable Long budgetId) {
   	Group group = groupService.createGroup(budgetId);   	
   	return "redirect:/budgets/" + budgetId + "/groups/" + group.getId();
   }
	
	@GetMapping("{groupId}")
	public String getMapping(@PathVariable Long groupId, ModelMap model) {
		Group group = groupService.findById(groupId).get();
		model.put("group", group);
		return "group";
	}
	
	@PostMapping("{groupId}")
	public String getMapping(@ModelAttribute Group group, @PathVariable Long budgetId, @PathVariable Long groupId, ModelMap model) {
		Group dbGroup = groupService.findById(groupId).get();
		dbGroup.setName(group.getName());
		groupService.save(dbGroup);
		return "redirect:/budgets/" + budgetId;
	}
}
