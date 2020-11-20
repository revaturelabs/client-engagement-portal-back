package com.engagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;

/**
*ClientBatchService provide sevices for 
*mapping batch to client or unmapping 
*batch fom client
*@author daniel constantinescu
*/

@Service
public class ClientBatchService {

	@Autowired
	private ClientBatchRepo clientBatchRepo;
	
	@Autowired
	private ClientRepo clientRepo;
	
	/**
	 * map batch to client
	 * @param batchId
	 * @param email
	 * @return - true for success, 
	 * false for client not found
	 */
	
	public boolean MapBatchtoClient(String batchId, String email) {
		
		boolean ret=false;
		
		Client client = clientRepo.findByEmail(email);
			
		if (client != null) {
				
				ClientBatch cb = new ClientBatch(1000,batchId,client);
				clientBatchRepo.save(cb) ;
				ret=true;
				
		}else 
			    ret= false;
		
		return ret;
	}
	
	
	/**
	 * unmap batch from client
	 * @param batchId
	 * @param email
	 * @return - true for success, 
	 * false for batch not found
	 */
	
	@Transactional
	public boolean UnMapBatchFromClient(String batchId, String email) {
		
		boolean ret=true;
		
		if (clientBatchRepo.findByBatchId(batchId) != null) {
				
			clientBatchRepo.deleteByBatchId(batchId);
			ret=true;
		
		}else
			
			ret=false;
			
		return ret;
					
	}
}
