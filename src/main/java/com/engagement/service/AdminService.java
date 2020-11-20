package com.engagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.ClientBatch;
import com.engagement.model.Admin;
import com.engagement.model.dto.BatchName;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientBatchRepo;
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
	private TrainingClient tc;
	
	@Autowired
	private ClientBatchRepo cbr;
	
	
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
		List<BatchName> batches = tc.getBatches();
		return batches;
	}
	
	/**
	 * @author Brooke Wursten
	 * Returns a list of all client-batch mappings
	 * @return list of Client-batch objects showing mappings
	 */
	public Map<String,Integer> findAllMappings() {
		Map<String,Integer> mappings = new HashMap<String,Integer>();
		List<ClientBatch> clientBatchList =  cbr.findAll();
		clientBatchList.forEach(clientBatch->{
			String k=clientBatch.getBatchId();
			int v=clientBatch.getClient().getClientId();
			mappings.put(k, v);
		});
		return mappings;
	}

	
	
	
	
}