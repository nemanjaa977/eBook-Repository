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

import com.nemanja97.eBook.dto.EBookDTO;
import com.nemanja97.eBook.entity.EBook;
import com.nemanja97.eBook.service.EBookServiceInterface;

@RestController
@RequestMapping(value = "api/ebooks")
@CrossOrigin("*")
public class EBookController {

    @Autowired
    EBookServiceInterface ebookServiceInterface;

    @GetMapping
    public ResponseEntity<List<EBookDTO>> getEBooks() {
        List<EBook> ebooks = ebookServiceInterface.findAll();
        List<EBookDTO> ebooksDTO = new ArrayList<EBookDTO>();
        for (EBook b : ebooks) {
            ebooksDTO.add(new EBookDTO(b));
        }
        return new ResponseEntity<List<EBookDTO>>(ebooksDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EBookDTO> getEBook(@PathVariable("id") Integer id) {
        EBook ebook = ebookServiceInterface.findOne(id);
        if (ebook == null) {
            return new ResponseEntity<EBookDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<EBookDTO>(new EBookDTO(ebook), HttpStatus.OK);
    }

    @GetMapping(value = "/categories/{name}")
    public ResponseEntity<List<EBookDTO>> getEbookByName(@PathVariable("name") String name) {
        List<EBook> ebooks = ebookServiceInterface.findByCategory_Name(name);
        List<EBookDTO> ebooksDTO = new ArrayList<EBookDTO>();
        for (EBook b : ebooks) {
            ebooksDTO.add(new EBookDTO(b));
        }
        return new ResponseEntity<List<EBookDTO>>(ebooksDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<EBookDTO> createEBook(@RequestBody EBookDTO eBookDTO) {
        EBook b = new EBook();
        b.setTitle(eBookDTO.getTitle());
        b.setAuthor(eBookDTO.getAuthor());
        b.setKeywords(eBookDTO.getKeywords());
        b.setPublication_year(eBookDTO.getPublicationYear());
        b.setFilename(eBookDTO.getFilename());
        b.setMime(eBookDTO.getMime());

        b = ebookServiceInterface.save(b);
        return new ResponseEntity<EBookDTO>(new EBookDTO(b), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<EBookDTO> updateEBook(@RequestBody EBookDTO eBookDTO, @PathVariable("id") Integer id) {
        EBook b = ebookServiceInterface.findOne(id);
        if (b == null) {
            return new ResponseEntity<EBookDTO>(HttpStatus.BAD_REQUEST);
        }
        b.setTitle(eBookDTO.getTitle());
        b.setAuthor(eBookDTO.getAuthor());
        b.setKeywords(eBookDTO.getKeywords());
        b.setPublication_year(eBookDTO.getPublicationYear());
        b.setFilename(eBookDTO.getFilename());
        b.setMime(eBookDTO.getMime());

        b = ebookServiceInterface.save(b);
        return new ResponseEntity<EBookDTO>(new EBookDTO(b), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteEBook(@PathVariable("id") Integer id) {
        EBook b = ebookServiceInterface.findOne(id);
        if (b != null) {
            ebookServiceInterface.remove(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}
