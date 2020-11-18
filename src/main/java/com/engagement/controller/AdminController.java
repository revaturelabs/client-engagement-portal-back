package com.engagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Admin;
import com.engagement.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService as;
	
	@PostMapping("/new")
	public ResponseEntity<?> create(@RequestBody Admin a) {
		if (as.register(a)) 
			return new ResponseEntity<String>("User succesfully created!", HttpStatus.OK);
		else 
			return new ResponseEntity<String>("User creation failed!!", HttpStatus.CONFLICT);
		}
		
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		if (as.login(username,password))
			 return new ResponseEntity<>("User logged succesfully!",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Wrong username or password !!", HttpStatus.CONFLICT);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody Admin a) {
		if (as.update(a) != null)
			return new ResponseEntity<String>("User updated succesfully!",HttpStatus.OK) ;
		else
			return new ResponseEntity<String>("Update failed", HttpStatus.CONFLICT);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(String username) {
		
		if (as.delete(username))
			return new ResponseEntity<String>("User deleted  succesfully!",HttpStatus.OK) ;
		else
			return new ResponseEntity<String>("Delete failed", HttpStatus.CONFLICT);
	}
	}


