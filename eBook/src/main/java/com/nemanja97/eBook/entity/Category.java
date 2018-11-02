package com.nemanja97.eBook.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="name", columnDefinition="varchar(30)", nullable=false)
	private String name;
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="category")
	private Set<EBook> ebooks = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="user_id", nullable=true)
	private User user;
	
	public Category() {
		
	}

	public Category(Integer id, String name, Set<EBook> ebooks, User user) {
		super();
		this.id = id;
		this.name = name;
		this.ebooks = ebooks;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<EBook> getEbooks() {
		return ebooks;
	}

	public void setEbooks(Set<EBook> ebooks) {
		this.ebooks = ebooks;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
