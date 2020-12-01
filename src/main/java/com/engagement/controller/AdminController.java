package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.engagement.model.Admin;
import com.engagement.model.AdminDto;
import com.engagement.model.dto.BatchName;
import com.engagement.service.AdminService;

/**
 * AdminController --- backend endpoints for admin/*.
 * 
 * @author Brooke Wursten & Daniel Consantinescu
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

	private AdminService as;

	@Autowired
	public AdminController(AdminService as) {
		super();
		this.as = as;
	}

	/**
	 * Returns a list of all admins
	 * @return list of all admins
	 */
	@GetMapping("/")
	public List<Admin> findAll() {
		return as.findAll();
	}

	/**
	 * Creates a new Admin object and persists to the DB
	 * 
	 * @param admin- the request body should contain a json in the shape of an Admin object
	 * @return ResponseEntity containing status code and message.
	 */
	@PostMapping("/new")
	public ResponseEntity<String> save(@RequestBody AdminDto admin) {
		Admin persistentAdmin = new Admin(0, admin.getEmail(), admin.getFirstName(), admin.getLastName());

		if (as.save(persistentAdmin)) {
			return new ResponseEntity<>("Admin successfully created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Admin creation failed", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Updates Admin object in the DB
	 * 
	 * @param admin- the request body should contain a json in the shape of an Admin object
	 * @return ResponseEntity containing status code and message.
	 */
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody AdminDto admin) {
		Admin adminInDB = as.findByEmail(admin.getEmail());
		
		if (adminInDB == null) { // admin does not exist
			return new ResponseEntity<>("Admin not found", HttpStatus.CONFLICT);
		}
		
		int adminId = adminInDB.getAdminId();
		Admin persistentAdmin = new Admin(adminId, admin.getEmail(), admin.getFirstName(), admin.getLastName());
		
		if (as.update(persistentAdmin) != null) { // admin successfully updated
			return new ResponseEntity<>("Admin updated succesfully", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Update failed", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Deletes Admin object from the DB
	 * 
	 * @param admin- the request body should contain a json in the shape of an Admin object
	 * @return ResponseEntity containing status code and message.
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam Integer id) {
		if (as.findByAdminId(id) == null) { // admin does not exist
			return new ResponseEntity<>("Admin not found", HttpStatus.CONFLICT);
		} else {
			as.delete(id);
			return new ResponseEntity<>("Admin deleted", HttpStatus.OK);
		}
	}
	
	/**
	 * Returns a list of all batches from Caliber API
	 * @return List of all batch IDs and names
	 */
	@GetMapping("/batch/allNames")
	public List<BatchName> getBatches() {
		return as.getAllBatches();
	}
	
}