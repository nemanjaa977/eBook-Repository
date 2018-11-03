package com.nemanja97.eBook.dto;

import java.io.Serializable;

import com.nemanja97.eBook.entity.Language;

public class LanguageDTO implements Serializable {
	
	private int id;
	private String name;
	
	public LanguageDTO() {
		super();
	}

	public LanguageDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public LanguageDTO(Language l) {
		this(l.getId(), l.getName());
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
