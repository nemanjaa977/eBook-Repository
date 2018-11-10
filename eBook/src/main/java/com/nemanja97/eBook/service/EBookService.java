package com.nemanja97.eBook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemanja97.eBook.entity.EBook;
import com.nemanja97.eBook.repository.EBookRepository;

@Service
public class EBookService implements EBookServiceInterface {
	
	@Autowired
	EBookRepository ebookRepository;
	
	@Override
	public EBook findOne(int ebookID) {
		return ebookRepository.getOne(ebookID);
	}
	
	@Override
	public List<EBook> findAll(){
		return ebookRepository.findAll();
	}
	
	@Override
	public EBook save(EBook ebook) {
		return ebookRepository.save(ebook);
	}
	
	@Override
	public void remove(int id) {
		ebookRepository.delete(id);
	}
	
	@Override
	public List<EBook> findByCategory_Name(String name){
		return ebookRepository.findByCategory_Name(name);
	}
	
}
