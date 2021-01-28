package com.udemy.budgettingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.budgettingapp.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String username);
}
