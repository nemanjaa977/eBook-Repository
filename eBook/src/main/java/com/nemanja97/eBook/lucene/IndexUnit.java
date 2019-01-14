package com.nemanja97.eBook.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nemanja97.eBook.dto.CategoryDTO;
import com.nemanja97.eBook.dto.LanguageDTO;

public class IndexUnit {

    private String text;
    private String title;
    private List<String> keywords = new ArrayList<String>();
    private String filename;
    private String filedate;
    private String author;
    private String languageDTO;
    private String categoryDTO;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguageDTO() {
        return languageDTO;
    }

    public void setLanguageDTO(String languageDTO) {
        this.languageDTO = languageDTO;
    }

    public String getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(String categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiledate() {
        return filedate;
    }

    public void setFiledate(String filedate) {
        this.filedate = filedate;
    }

    @JsonIgnore
    public Document getLuceneDocument() {
        Document retVal = new Document();
        retVal.add(new TextField("text", text, Store.NO));
        retVal.add(new TextField("title", title, Store.YES));
        for (String keyword : keywords) {
            retVal.add(new TextField("keyword", keyword, Store.YES));
        }
        retVal.add(new StringField("filename", filename, Store.YES));
        retVal.add(new TextField("filedate", filedate, Store.YES));
        retVal.add(new StringField("author", author, Store.YES));
        retVal.add(new StringField("language", languageDTO, Store.YES));
        retVal.add(new StringField("category", categoryDTO, Store.YES));
        return retVal;
    }

}
