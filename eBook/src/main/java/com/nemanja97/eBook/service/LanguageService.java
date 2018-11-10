package com.nemanja97.eBook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemanja97.eBook.entity.Language;
import com.nemanja97.eBook.repository.LanguageRepository;

@Service
public class LanguageService implements LanguageServiceInterface {
	
	@Autowired
	LanguageRepository languageRepository;
	
	@Override
	public Language findOne(int languageID) {
		return languageRepository.getOne(languageID);
	}
	
	@Override
	public List<Language> findAll(){
		return languageRepository.findAll();
	}
	
	@Override
	public Language save(Language language) {
		return languageRepository.save(language);
	}
	
	@Override
	public void remove(int id) {
		languageRepository.delete(id);
	}
	
}
