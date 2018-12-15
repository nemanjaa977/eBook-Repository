package com.nemanja97.eBook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nemanja97.eBook.dto.CategoryDTO;
import com.nemanja97.eBook.entity.Category;
import com.nemanja97.eBook.service.CategoryServiceInterface;

@RestController
@RequestMapping(value="api/categories")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired CategoryServiceInterface categoryServiceInterface;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getCategories(){
		List<Category> categories = categoryServiceInterface.findAll();
		List<CategoryDTO> categoriesDTO = new ArrayList<>();
		for(Category c: categories) {
			categoriesDTO.add(new CategoryDTO(c));
		}
		return new ResponseEntity<List<CategoryDTO>>(categoriesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Integer id){
		Category category = categoryServiceInterface.findOne(id);
		if(category == null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(category), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
		Category c = new Category();
		c.setName(categoryDTO.getName());
		
		c = categoryServiceInterface.save(c);
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(c), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/update/{id}", consumes="application/json")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Integer id){
		Category c = categoryServiceInterface.findOne(id);
		if(c == null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.BAD_REQUEST);
		}
		c.setName(categoryDTO.getName());
		
		c = categoryServiceInterface.save(c);
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(c), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id){
		Category c = categoryServiceInterface.findOne(id);
		if(c != null){
			categoryServiceInterface.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
}
