package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Admin;
import com.engagement.model.dto.BatchName;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.caliber.TrainingClient;

/**
 * 
 * @author
 *
 */

@Service
public class AdminService {

	private AdminRepo ar;
	
	@Autowired
	private TrainingClient bc;

	@Autowired
	public AdminService(AdminRepo ar) {
		super();
		this.ar = ar;	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Admin> findAll() {
		return ar.findAll();
	}
<<<<<<< HEAD
	
	public Admin update(Admin admin) {
	
		return adminRepository.save(admin);
=======

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Admin findByAdminId(Integer id) {
		return ar.findByAdminId(id);
>>>>>>> ca32056a7877ed4adad1796d104a9c54306bdd53
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public Admin findByEmail(String email) {
		return ar.findByEmail(email);
	}

	/**
	 * 
	 * @param admin
	 * @return
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
	 * 
	 * @param admin
	 * @return
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
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		// id cannot be null
		if (id == null) {
			return;
		}
		
		ar.deleteById(id);
	}

	/*
	 * Returns a list of all batches from Caliber API
	 * @return List of all batch IDs and names
	 */
	public List<BatchName> getAllBatches() {
		List<BatchName> batches = bc.getBatches();
		return batches;
	}

}