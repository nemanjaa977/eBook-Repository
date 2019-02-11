package com.nemanja97.eBook.lucene.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import com.nemanja97.eBook.dto.LanguageDTO;
import com.nemanja97.eBook.lucene.IndexUnit;


public class PDFHandler extends DocumentHandler {


    @Override
    public IndexUnit getIndexUnit(File file) {
        IndexUnit retVal = new IndexUnit();
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            String text = getText(parser);
            retVal.setText(text);

            // metadata extraction
            PDDocument pdf = parser.getPDDocument();
            PDDocumentInformation info = pdf.getDocumentInformation();

            String title = "" + info.getTitle();
            retVal.setTitle(title);

            String keywords = "" + info.getKeywords();
            if (keywords != null) {
                String[] splittedKeywords = keywords.split(" ");
                if(splittedKeywords.length>=1)
                	//retVal.setKeywords(new ArrayList<String>(Arrays.asList(splittedKeywords)));
                {
                	ArrayList<String> keywordsList=new ArrayList<String>();
                	for (String item : splittedKeywords) {
                		System.out.println(item);
						keywordsList.add(item);
					}
                	retVal.setKeywords(keywordsList);
                }
                else
                	retVal.setKeywords(new ArrayList<String>());
            }

            retVal.setFilename(file.getName());
            
            String author="";
            if(info.getAuthor() != null) {
            	author=info.getAuthor();
            }
            retVal.setAuthor(author);
            
            String langDto = "";
            retVal.setLanguageDTO(langDto);
            
            String cateString = "";
            retVal.setCategoryDTO(cateString);
            

            String modificationDate = DateTools.dateToString(new Date(file.lastModified()), DateTools.Resolution.DAY);
            retVal.setFiledate(modificationDate);

            pdf.close();
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }

        return retVal;
    }

    @Override
    public String getText(File file) {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper textStripper = new PDFTextStripper(); //-uzima se tekst iz dokumenta
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }
        return null;
    }

    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }
        return null;
    }

}
