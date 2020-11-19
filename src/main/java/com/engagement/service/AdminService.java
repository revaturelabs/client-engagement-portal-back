package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Admin;
import com.engagement.model.dto.BatchName;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.caliber.TrainingClient;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepo adminRepository;
	@Autowired
	private TrainingClient bc;
	
	public Admin findByAdminId(Integer id) {
		return adminRepository.findByAdminId(id);
		
	}
	
	public Admin findByUsername(String email) {
		return adminRepository.findByEmail(email);
	}
	
	public boolean save(Admin admin) {
	  boolean ret=false;
	if ( adminRepository.save(admin) != null)
		  ret= true;
	  else
		  ret=false;
	  
	  return ret;
				  
	   
	}
	
	public Admin update(Admin admin) {
		boolean ret=false;
	
		return adminRepository.save(admin);
	}
	
	public void delete(Integer id) {
	
	 adminRepository.deleteById(id);			 

	
	}
	
	//TODO:  need further authentication implemented
	public boolean login(String email) {
		boolean ret=false;
		if (adminRepository.findByEmail(email) != null )
			ret=true;
		else
			ret=false;
		
		return ret;
	}

	public List<Admin> findAll() {
		return adminRepository.findAll();
	}

	/**
	 * Returns a list of all batches from Caliber API
	 * @return List of all batch IDs and names
	 */
	public List<BatchName> getAllBatches() {
		List<BatchName> batches = bc.getBatches();
		for(BatchName b : batches) {
			System.out.println(b);
		}
		return batches;
	}

}
	

	

