package com.nemanja97.eBook.dto;

import java.io.Serializable;

import com.nemanja97.eBook.entity.Category;

public class CategoryDTO implements Serializable {

    private int id;
    private String name;

    public CategoryDTO() {
        super();
    }

    public CategoryDTO(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category category) {
        this(category.getId(), category.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
