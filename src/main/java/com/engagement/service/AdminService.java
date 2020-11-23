package com.engagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.model.dto.BatchName;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.TrainingClient;



/**
 * Service for handling business logic of admin requests
 * @author
 *
 */

@Service
public class AdminService {
	
	@Autowired
	private AdminRepo ar;
	
	@Autowired
	private TrainingClient tc;
	
	@Autowired
	private ClientBatchRepo cbr;
	
	@Autowired 
	private ClientRepo cr;
	
	
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
		} catch (IllegalArgumentException e) {
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
		return tc.getBatches();
	}
	
	/**
	 * @author Brooke Wursten
	 * Returns a list of all client-batch mappings
	 * @return list of Client-batch objects showing mappings
	 */
	public Map<String,Integer> findAllMappings() {
		Map<String,Integer> mappings = new HashMap<>();
		List<ClientBatch> clientBatchList =  cbr.findAll();
		clientBatchList.forEach(clientBatch->{
			String k=clientBatch.getBatchId();
			int v=clientBatch.getClient().getClientId();
			mappings.put(k, v);
		});
		return mappings;
	}

	/**
	 * author daniel constantinescu
	 * map batch to client
	 * @param batchId
	 * @param email
	 * @return - true for success, 
	 * false for client not found
	 */
	
	public boolean MapBatchtoClient(String batchId, String email) {
		
		boolean ret=false;
		
		Client client = cr.findByEmail(email);
			
		if (client != null) {
				
				ClientBatch cb = new ClientBatch(1000,batchId,client);
				cbr.save(cb) ;
				ret=true;
				
		}else 
			    ret= false;
		
		return ret;
	}
	
	
	/**
	 * @author daniel constantinescu
	 * unmap batch from client
	 * @param batchId
	 * @param email
	 * @return - true for success, 
	 * false for batch not found
	 */
	
	@Transactional
	public boolean UnMapBatchFromClient(String batchId, String email) {
		
		boolean ret=true;
		
		if (cbr.findByBatchId(batchId) != null) {
				
			cbr.deleteByBatchId(batchId);
			ret=true;
		
		}else
			
			ret=false;
			
		return ret;
					
	}
	
	
	
	
}