package com.nemanja97.eBook.service;

import com.nemanja97.eBook.entity.Authority;

public interface AuthorityServiceInterface {
	
	Authority findByName(String name);
	
}
