package com.nemanja97.eBook.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="first_name", columnDefinition="varchar(30)", nullable=false)
	private String firstName;
	
	@Column(name="last_name", columnDefinition="varchar(30)", nullable=false)
	private String lastName;
	
	@Column(name="username", columnDefinition="varchar(10)", nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="type", columnDefinition="varchar(30)", nullable=false)
	private String type;
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="user")
	private Set<EBook> ebooks = new HashSet<>();
	
	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="user")
	private Set<Category> categories = new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> user_authorities = new HashSet<>();
	
	public User() {
		
	}

	public User(Integer id, String firstName, String lastName, String username, String password, String type,
			Set<EBook> ebooks, Set<Category> categories) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.type = type;
		this.ebooks = ebooks;
		this.categories = categories;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<EBook> getEbooks() {
		return ebooks;
	}

	public void setEbooks(Set<EBook> ebooks) {
		this.ebooks = ebooks;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	
	
	public Set<Authority> getUser_authorities() {
		return user_authorities;
	}

	public void setUser_authorities(Set<Authority> user_authorities) {
		this.user_authorities = user_authorities;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user_authorities;
    }
	
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
