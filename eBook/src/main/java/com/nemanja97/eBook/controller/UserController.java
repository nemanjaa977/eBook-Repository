package com.nemanja97.eBook.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nemanja97.eBook.dto.UserDTO;
import com.nemanja97.eBook.entity.Authority;
import com.nemanja97.eBook.entity.User;
import com.nemanja97.eBook.repository.AuthorityRepository;
import com.nemanja97.eBook.service.AuthorityServiceInterface;
import com.nemanja97.eBook.service.UserServiceInterface;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserServiceInterface userServiceInterface;
    @Autowired
    AuthorityServiceInterface authorityServiceInterface;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/whoami")
    public UserDTO user(Principal user) {
        User u = userServiceInterface.findByUsername(user.getName());
        return new UserDTO(u);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userServiceInterface.findAll();
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        for (User u : users) {
            usersDTO.add(new UserDTO(u));
        }
        return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id) {
        User user = userServiceInterface.findOne(id);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        User user = userServiceInterface.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
    }

    @RequestMapping(consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User u = new User();
        u.setFirstName(userDTO.getFirstName());
        u.setLastName(userDTO.getLastName());
        u.setUsername(userDTO.getUsername());
        u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        u.setType("User");
        Authority authority = authorityServiceInterface.findByName("ROLE_USER");
        u.getUser_authorities().add(authority);
        u = userServiceInterface.save(u);
        return new ResponseEntity<UserDTO>(new UserDTO(u), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Integer id) {
        User u = userServiceInterface.findOne(id);
        if (u == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
        }
        u.setFirstName(userDTO.getFirstName());
        u.setLastName(userDTO.getLastName());
        u.setUsername(userDTO.getUsername());
        u.setPassword(userDTO.getPassword());
        u.setType(userDTO.getType());

        u = userServiceInterface.save(u);
        return new ResponseEntity<UserDTO>(new UserDTO(u), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        User u = userServiceInterface.findOne(id);
        if (u != null) {
            userServiceInterface.remove(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}
