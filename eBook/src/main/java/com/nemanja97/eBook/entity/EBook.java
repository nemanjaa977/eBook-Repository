package com.nemanja97.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ebooks")
public class EBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "title", columnDefinition = "varchar(80)", nullable = false)
    private String title;

    @Column(name = "author", columnDefinition = "varchar(120)", nullable = false)
    private String author;

    @Column(name = "keywords", columnDefinition = "varchar(120)", nullable = false)
    private String keywords;

    @Column(name = "publication_year", nullable = false)
    private Integer publication_year;

    @Column(name = "filename", columnDefinition = "varchar(200)", nullable = false)
    private String filename;

    @Column(name = "mime", columnDefinition = "varchar(100)", nullable = false)
    private String mime;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public EBook() {

    }

    public EBook(Integer id, String title, String author, String keywords, Integer publication_year, String filename,
                 String mime, Language language, Category category, User user) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.publication_year = publication_year;
        this.filename = filename;
        this.mime = mime;
        this.language = language;
        this.category = category;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
