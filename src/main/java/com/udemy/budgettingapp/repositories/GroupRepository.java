package com.udemy.budgettingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.budgettingapp.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

}
