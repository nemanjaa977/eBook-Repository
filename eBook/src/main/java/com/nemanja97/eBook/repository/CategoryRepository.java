package com.nemanja97.eBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemanja97.eBook.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Category findByName(String name);

}
