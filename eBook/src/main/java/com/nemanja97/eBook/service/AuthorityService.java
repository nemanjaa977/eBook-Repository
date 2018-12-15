package com.nemanja97.eBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemanja97.eBook.entity.Authority;
import com.nemanja97.eBook.repository.AuthorityRepository;

@Service
public class AuthorityService implements AuthorityServiceInterface {

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority findByName(String name) {

        return authorityRepository.findByName(name);
    }


}
