package com.nemanja97.eBook.service;

import java.util.List;

import com.nemanja97.eBook.entity.Language;

public interface LanguageServiceInterface {

    Language findOne(int id);

    List<Language> findAll();

    Language save(Language language);

    void remove(int id);
    
    Language findByName(String name);

}
