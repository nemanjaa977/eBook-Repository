package com.nemanja97.eBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemanja97.eBook.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);

}
