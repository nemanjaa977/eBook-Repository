package com.nemanja97.eBook.service;

import java.util.List;

import com.nemanja97.eBook.entity.User;

public interface UserServiceInterface {
	
	User findOne(int id);
	List<User> findAll();
	User save(User user);
	void remove(int id);
	User findByUsername(String username);
	
}
