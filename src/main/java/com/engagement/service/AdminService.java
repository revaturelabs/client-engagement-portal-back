package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Admin;
import com.engagement.repo.AdminRepo;

/**
 * 
 * @author
 *
 */

@Service
public class AdminService {

	private AdminRepo ar;

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

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Admin findByAdminId(Integer id) {
		return ar.findByAdminId(id);
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
		boolean successfulSave = false;
		
		if (admin == null) {
			return successfulSave;
		}
		
		try {
			ar.save(admin);
			successfulSave = true;
		} catch (Exception e) {
			return successfulSave;
		}
		
		return successfulSave;
	}

	/**
	 * 
	 * @param admin
	 * @return
	 */
	public Admin update(Admin admin) {
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
		if (id == null) {
			return;
		}
		
		ar.deleteById(id);
	}

	// TODO: need further authentication implemented
	public boolean login(String email) {
		boolean ret = false;
		if (ar.findByEmail(email) != null)
			ret = true;
		else
			ret = false;

		return ret;
	}

}