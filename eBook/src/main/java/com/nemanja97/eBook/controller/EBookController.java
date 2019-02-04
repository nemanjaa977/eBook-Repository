package com.nemanja97.eBook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;

import com.nemanja97.eBook.dto.EBookDTO;
import com.nemanja97.eBook.entity.EBook;
import com.nemanja97.eBook.lucene.IndexUnit;
import com.nemanja97.eBook.lucene.Indexer;
import com.nemanja97.eBook.lucene.handlers.PDFHandler;
import com.nemanja97.eBook.service.CategoryServiceInterface;
import com.nemanja97.eBook.service.EBookServiceInterface;
import com.nemanja97.eBook.service.LanguageServiceInterface;
import com.nemanja97.eBook.service.UserServiceInterface;

@RestController
@RequestMapping(value = "api/ebooks")
@CrossOrigin("*")
public class EBookController {

	@Autowired
	EBookServiceInterface ebookServiceInterface;
	@Autowired
	CategoryServiceInterface categoryService;
	@Autowired
	LanguageServiceInterface languageService;
	@Autowired
	UserServiceInterface userService;

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
	public ResponseEntity<EBookDTO> createEBook(@RequestBody IndexUnit indexUnit, Principal logged) {
		EBook b = new EBook();
		b.setTitle(indexUnit.getTitle());
		b.setAuthor(indexUnit.getAuthor());
		String keyString = "";
		for (String keyword : indexUnit.getKeywords()) {
			keyString += keyword + " ";
		}
		b.setKeywords(keyString);
		b.setPublication_year(Integer.parseInt(indexUnit.getFiledate()));
		b.setFilename(indexUnit.getFilename());
		b.setMime("application/pdf");

		b.setCategory(categoryService.findOne(Integer.parseInt(indexUnit.getCategoryDTO())));
		b.setLanguage(languageService.findByName(indexUnit.getLanguageDTO()));
		b.setUser(userService.findByUsername(logged.getName()));
		

		Indexer.getInstance().add(indexUnit.getLuceneDocument());
		b = ebookServiceInterface.save(b);

		return new ResponseEntity<EBookDTO>(new EBookDTO(b), HttpStatus.CREATED);
	}

	@PutMapping(value = "/update/{id}", consumes = "application/json")
	public ResponseEntity<EBookDTO> updateEBook(@RequestBody IndexUnit indexUnit, @PathVariable("id") Integer id) {
		EBook b = ebookServiceInterface.findOne(id);
		if (b == null) {
			return new ResponseEntity<EBookDTO>(HttpStatus.BAD_REQUEST);
		}
		b.setTitle(indexUnit.getTitle());
		b.setAuthor(indexUnit.getAuthor());
		String keyString = "";
		for (String keyword : indexUnit.getKeywords()) {
			keyString += keyword + " ";
		}
		b.setKeywords(keyString);
		b.setCategory(categoryService.findOne(Integer.parseInt(indexUnit.getCategoryDTO())));
		b.setLanguage(languageService.findByName(indexUnit.getLanguageDTO()));
		PDFHandler handler=new PDFHandler();
		String path="C:\\Users\\nemanja97\\Desktop\\files\\"+b.getFilename();
		String text=handler.getText(new File(path));
		indexUnit.setText(text);
		indexUnit.setFiledate(b.getPublication_year().toString());
		Indexer.getInstance().delete(b.getFilename());
		Indexer.getInstance().add(indexUnit.getLuceneDocument());
		
		b = ebookServiceInterface.save(b);
		return new ResponseEntity<EBookDTO>(new EBookDTO(b), HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteEBook(@PathVariable("id") Integer id) {
		EBook b = ebookServiceInterface.findOne(id);
		if (b != null) {
			Indexer.getInstance().delete(b.getFilename());
			File f=new File("C:\\Users\\nemanja97\\Desktop\\files\\"+b.getFilename());
			f.delete();
			ebookServiceInterface.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<byte []> download(@PathVariable("fileName") String fileName) throws IOException {
		String path = "C:\\Users\\nemanja97\\Desktop\\files\\";
		String finalPath=path+fileName+".pdf";
		File file=new File(finalPath);		
		byte[] bFile = readBytesFromFile(file.toString());
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("filename",fileName+".pdf");

		return ResponseEntity.ok().headers(headers).body(bFile);
	}
	
	private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return bytesArray;

	}

}
