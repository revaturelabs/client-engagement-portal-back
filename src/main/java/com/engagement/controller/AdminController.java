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
		System.out.println(a);
		if (as.register(a)) 
			return new ResponseEntity<String>("User succesfully created!", HttpStatus.OK);
		else 
			return new ResponseEntity<String>("User creation failed!!", HttpStatus.CONFLICT);
		}
		
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email) {
		if (as.login(email))
			 return new ResponseEntity<>("User logged succesfully!",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Email not found", HttpStatus.CONFLICT);
		
	}
	
	//need to be send with id otherwise insert another object
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody Admin a) {
		if (as.update(a) != null)
			return new ResponseEntity<String>("User updated succesfully!",HttpStatus.OK) ;
		else
			return new ResponseEntity<String>("Update failed", HttpStatus.CONFLICT);
	}
	
	//delete doesn't return anything
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Integer id) {
		if (as.findByAdminId(id) == null)
			return new ResponseEntity<String>("User not found!",HttpStatus.CONFLICT) ;
		else {
			as.delete(id);
			return new ResponseEntity<>(HttpStatus.OK) ;
		}
	}
}


