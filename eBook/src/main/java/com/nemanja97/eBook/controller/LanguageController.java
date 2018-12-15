package com.nemanja97.eBook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nemanja97.eBook.dto.LanguageDTO;
import com.nemanja97.eBook.entity.Language;
import com.nemanja97.eBook.service.LanguageServiceInterface;

@RestController
@RequestMapping(value = "api/languages")
@CrossOrigin("*")
public class LanguageController {

    @Autowired
    LanguageServiceInterface languageServiceInterface;

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getLanguages() {
        List<Language> languages = languageServiceInterface.findAll();
        List<LanguageDTO> languagesDTO = new ArrayList<LanguageDTO>();
        for (Language l : languages) {
            languagesDTO.add(new LanguageDTO(l));
        }
        return new ResponseEntity<List<LanguageDTO>>(languagesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LanguageDTO> getLanguage(@PathVariable("id") Integer id) {
        Language l = languageServiceInterface.findOne(id);
        if (l == null) {
            return new ResponseEntity<LanguageDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<LanguageDTO>(new LanguageDTO(l), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<LanguageDTO> createLanguage(@RequestBody LanguageDTO languageDTO) {
        Language l = new Language();
        l.setName(languageDTO.getName());

        l = languageServiceInterface.save(l);
        return new ResponseEntity<LanguageDTO>(new LanguageDTO(l), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<LanguageDTO> updateLanguage(@RequestBody LanguageDTO languageDTO, @PathVariable("id") Integer id) {
        Language l = languageServiceInterface.findOne(id);
        if (l == null) {
            return new ResponseEntity<LanguageDTO>(HttpStatus.BAD_REQUEST);
        }
        l.setName(languageDTO.getName());

        l = languageServiceInterface.save(l);
        return new ResponseEntity<LanguageDTO>(new LanguageDTO(l), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable("id") Integer id) {
        Language l = languageServiceInterface.findOne(id);
        if (l != null) {
            languageServiceInterface.remove(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}
