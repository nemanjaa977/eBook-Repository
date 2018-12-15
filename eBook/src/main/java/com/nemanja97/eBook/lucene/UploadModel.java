package com.nemanja97.eBook.lucene;

import org.springframework.web.multipart.MultipartFile;

import com.nemanja97.eBook.dto.LanguageDTO;

public class UploadModel {

    private String title;
    private String keywords;
    private String author;
    private LanguageDTO languageDTO;
    private MultipartFile[] files;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LanguageDTO getLanguageDTO() {
        return languageDTO;
    }

    public void setLanguageDTO(LanguageDTO languageDTO) {
        this.languageDTO = languageDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

}
