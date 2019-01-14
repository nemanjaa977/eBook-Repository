package com.nemanja97.eBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemanja97.eBook.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
	
	Language findByName(String name);

}
