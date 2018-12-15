package com.nemanja97.eBook.service;

import java.util.List;

import com.nemanja97.eBook.entity.Category;

public interface CategoryServiceInterface {

    Category findOne(int id);

    List<Category> findAll();

    Category save(Category category);

    void remove(int id);

}
