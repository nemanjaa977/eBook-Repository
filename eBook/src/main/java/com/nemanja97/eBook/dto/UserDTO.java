package com.nemanja97.eBook.dto;

import java.io.Serializable;

import com.nemanja97.eBook.entity.User;

public class UserDTO implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String type;

    public UserDTO() {
        super();
    }

    public UserDTO(int id, String firstName, String lastName, String username, String password, String type) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getType());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

}
