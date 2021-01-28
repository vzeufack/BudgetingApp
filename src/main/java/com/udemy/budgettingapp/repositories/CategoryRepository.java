package com.udemy.budgettingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.budgettingapp.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
