package com.udemy.budgettingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.budgettingapp.domain.Category;
import com.udemy.budgettingapp.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepo;
	
   public Category saveCategory(Category category) {
   	return categoryRepo.save(category);
   }
   
   public Category findById(Long categoryId) {
   	return categoryRepo.findById(categoryId).get();
   }
}
