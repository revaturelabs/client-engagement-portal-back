package com.engagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.engagement.model.dto.Batch;
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
import com.engagement.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;



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
	
	@Autowired
	private FirebaseUtil firebaseUtil;

	@Autowired
	private ClientService cs;

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
//			Map<String, Object> claims = new HashMap<String, Object>();
//			claims.put("role", "admin");
//			UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(admin.getEmail());
//			FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), claims);
			firebaseUtil.setCustomClaims(admin.getEmail());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
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
		} catch (IllegalArgumentException e) {
			return null;
		}
		
		return admin;
	}

	/**
	 * Delete an admin by their id
	 * @param id the admin's id
	 */
	public void delete(Integer id) {
		ar.deleteById(id);
	}

	/**
	 * @author Carlo Anselmo
	 * Returns a list of all batches from Caliber API
	 * @return List of all batch IDs and names
	 */

	public List<BatchName> getAllBatchNames() {
		return tc.getBatches();
	}
	
	/**
	 * @author Brooke Wursten
	 * Returns a list of all client-batch mappings
	 * @return list of Client-batch objects showing mappings
	 */
	public Map<String,String> findAllMappings() {
		Map<String,String> mappings = new HashMap<>();
		List<ClientBatch> clientBatchList =  cbr.findAll();
		clientBatchList.forEach(clientBatch->{
			String k=clientBatch.getBatchId();
			String v=clientBatch.getClient().getEmail();
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
	
	public boolean mapBatchtoClient(String batchId, String email) {
		
		boolean ret=false;
		
		Client client = cr.findByEmail(email);
			
		if (client != null) {
			if(cbr.findByBatchIdAndClient(batchId, client) == null)
			{
				ClientBatch cb = new ClientBatch(1000,batchId,client);
				cbr.save(cb) ;
				ret=true;
			}
		}
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
	public boolean unmapBatchFromClient(String batchId, String email) {
		
		boolean ret=true;
		Client c = cr.findByEmail(email);
		ClientBatch cb = cbr.findByBatchIdAndClient(batchId,c);
		
		if (cb != null) {
				
			cbr.delete(cb);
			ret=true;
		
		}else
			
			ret=false;
			
		return ret;
					
	}

	/**
	 * Used by AdminController to return a list of all the batches and their information
	 * @return A List<Batch>
	 * @author Cory Sebastian
	 */
	public List<Batch> getAllBatches() {
		List<BatchName> batchNames = getAllBatchNames();
		List<Batch> batches = new ArrayList<>();
		for (BatchName batchName : batchNames) {
			batches.add(cs.getBatchByBatchId(batchName.getBatchId()));
		}
		return batches;
	}

	public Batch getBatch(String id) {
		return cs.getBatchByBatchId(id);
	}
	
	
	
	
}
