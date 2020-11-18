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

/**
 * AdminController --- backend endpoints for admin/*.
 * @author    Brooke Wursten
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService as;
	
	/**
	  * Creates a new Admin object and persists to the DB 
	  * @param admin- the request body should contain a json 
	  * in the shape of an Admin object
	  * @return ResponseEntity containing status code and message.
	  */ 
	@PostMapping("/new")
	public ResponseEntity<String> create(@RequestBody Admin admin) {
		if (as.register(admin)) 
			return new ResponseEntity<String>("User succesfully created!", HttpStatus.CREATED);
		else 
			return new ResponseEntity<String>("User creation failed!", HttpStatus.CONFLICT);
		}
		
	
	/**
	  * Updates Admin object in the DB 
	  * @param admin- the request body should contain a json 
	  * in the shape of an Admin object
	  * @return ResponseEntity containing status code and message.
	  */ 
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody Admin a) {
		if (as.update(a) != null)
			return new ResponseEntity<String>("User updated succesfully!",HttpStatus.ACCEPTED) ;
		else
			return new ResponseEntity<String>("Update failed", HttpStatus.CONFLICT);
	}
	
	/**
	  * Deletes Admin object from the DB 
	  * @param admin- the request body should contain a json 
	  * in the shape of an Admin object
	  * @return ResponseEntity containing status code and message.
	  */ 
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


