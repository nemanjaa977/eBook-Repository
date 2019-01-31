package com.nemanja97.eBook.search;

public class ResultData {
	
	private String title;
	private String author;
	private String keywords;
	private String location;
	private int categoryId;
	
	public ResultData() {
		super();
	}

	public ResultData(String title, String author, String keywords, String location, int categoryId) {
		super();
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.location = location;
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	

}
