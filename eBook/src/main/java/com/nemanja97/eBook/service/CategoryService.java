package com.nemanja97.eBook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemanja97.eBook.entity.Category;
import com.nemanja97.eBook.repository.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceInterface {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Category findOne(int categoryID) {
		return categoryRepository.getOne(categoryID);
	}
	
	@Override
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	@Override
	public void remove(int id) {
		categoryRepository.delete(id);
	}
	
}
