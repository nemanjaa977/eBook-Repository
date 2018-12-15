package com.nemanja97.eBook.dto;

import java.io.Serializable;

import com.nemanja97.eBook.entity.EBook;

public class EBookDTO implements Serializable {

    private int id;
    private String title;
    private String author;
    private String keywords;
    private int publicationYear;
    private String filename;
    private String mime;

    public EBookDTO() {
        super();
    }

    public EBookDTO(int id, String title, String author, String keywords, int publicationYear, String filename,
                    String mime) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.publicationYear = publicationYear;
        this.filename = filename;
        this.mime = mime;
    }

    public EBookDTO(EBook b) {
        this(b.getId(), b.getTitle(), b.getAuthor(), b.getKeywords(), b.getPublication_year(), b.getFilename(), b.getMime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

}
