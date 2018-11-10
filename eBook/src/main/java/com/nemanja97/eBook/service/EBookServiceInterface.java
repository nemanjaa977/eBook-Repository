package com.nemanja97.eBook.service;

import java.util.List;

import com.nemanja97.eBook.entity.EBook;

public interface EBookServiceInterface {
	
	EBook findOne(int id);
	List<EBook> findAll();
	EBook save(EBook ebook);
	void remove(int id);
	List<EBook> findByCategory_Name(String name);
	
}
