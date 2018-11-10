package com.nemanja97.eBook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemanja97.eBook.entity.User;
import com.nemanja97.eBook.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findOne(int userID) {
		return userRepository.getOne(userID);
	}
	
	@Override
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public void remove(int id) {
		userRepository.delete(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
;	}
}
