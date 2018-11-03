package com.nemanja97.eBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemanja97.eBook.entity.EBook;

public interface EBookRepository extends JpaRepository<EBook, Integer> {

}
