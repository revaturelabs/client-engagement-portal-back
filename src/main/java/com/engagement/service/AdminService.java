package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Admin;
import com.engagement.model.dto.BatchName;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.caliber.TrainingClient;

/**
 * Service for handling business logic of admin requests
 * @author
 *
 */

@Service
public class AdminService {

	private AdminRepo ar;
	private TrainingClient bc;

	@Autowired
	public AdminService(AdminRepo ar, TrainingClient bc) {
		super();
		this.ar = ar;
		this.bc = bc;
	}
	
	/**
	 * Return a list of all admins
	 * @return
	 */
	public List<Admin> findAll() {
		return ar.findAll();
	}

	/**
	 * Find an admin by id
	 * @param id the admin's id
	 * @return the admin that matches the id
	 */
	public Admin findByAdminId(Integer id) {
		return ar.findByAdminId(id);
	}

	/**
	 * Find an admin by email
	 * @param email the admin's email
	 * @return the admin that matches the email
	 */
	public Admin findByEmail(String email) {
		return ar.findByEmail(email);
	}

	/**
	 * Saves an admin to the database
	 * @param admin admin to save
	 * @return true if success, false if fail
	 */
	public boolean save(Admin admin) {		
		// admin cannot be null
		if (admin == null) {
			return false;
		}
		
		try {
			ar.save(admin);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Update an admin's information
	 * @param admin the admin to update
	 * @return the updated admin or null if failed update
	 */
	public Admin update(Admin admin) {
		// admin cannot be null
		if (admin == null) {
			return null;
		}
		
		try {
			ar.save(admin);
		} catch (Exception e) {
			return null;
		}
		
		return admin;
	}

	/**
	 * Delete an admin by their id
	 * @param id the admin's id
	 */
	public void delete(Integer id) {
		// id cannot be null
		if (id == null) {
			return;
		}
		
		ar.deleteById(id);
	}

	/**
	 * Returns a list of all batches from Caliber API
	 * @return List of all batch IDs and names
	 */

	public List<BatchName> getAllBatches() {
		return bc.getBatches();
		
	}

}