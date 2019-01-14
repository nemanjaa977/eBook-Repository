package com.nemanja97.eBook.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nemanja97.eBook.dto.EBookDTO;
import com.nemanja97.eBook.entity.EBook;
import com.nemanja97.eBook.lucene.IndexUnit;
import com.nemanja97.eBook.lucene.Indexer;
import com.nemanja97.eBook.lucene.UploadModel;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/lucene")
@CrossOrigin("*")
public class IndexerController {
	
	private static String DATA_DIR_PATH;
	
	static {
		ResourceBundle rb=ResourceBundle.getBundle("application");
		DATA_DIR_PATH=rb.getString("dataDir");
	}
	
	@GetMapping("/reindex")
	public ResponseEntity<String> index() throws IOException {
		File dataDir = getResourceFilePath(DATA_DIR_PATH);
		long start = new Date().getTime();
		int numIndexed = Indexer.getInstance().index(dataDir);
		long end = new Date().getTime();
		String text = "Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds";
		return new ResponseEntity<String>(text, HttpStatus.OK);
	}
	
	private File getResourceFilePath(String path) {
	    URL url = this.getClass().getClassLoader().getResource(path);
	    File file = null;
	    try {
	        file = new File(url.toURI());
	    } catch (URISyntaxException e) {
	        file = new File(url.getPath());
	    }   
	    return file;
	}

    @PostMapping("/index/add")
    public ResponseEntity<IndexUnit> multiUploadFileModel(@RequestParam("file") MultipartFile file) {
        try {

        	IndexUnit unit=indexUploadedFile(file);
        	return new ResponseEntity<IndexUnit>(unit, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<IndexUnit>(HttpStatus.BAD_REQUEST);
        }
    }
	    
    //save file
    private String saveUploadedFile(MultipartFile file) throws IOException {
    	String retVal = null;
        if (! file.isEmpty()) {
        	System.out.println(DATA_DIR_PATH);
            byte[] bytes = file.getBytes();
            Path path = Paths.get(DATA_DIR_PATH + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }
    
    private IndexUnit indexUploadedFile(MultipartFile files) throws IOException{		
            String fileName = saveUploadedFile(files);
            if(fileName != null){
            	IndexUnit indexUnit = Indexer.getInstance().getHandler().getIndexUnit(new File(fileName));
            	return indexUnit;
            }
            return null;	
    }
	
}
