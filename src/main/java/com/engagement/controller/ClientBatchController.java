package com.engagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.service.ClientBatchService;

/**
 * ClientBatchController - provide endpoints for 
 * mapping/unmaping  batch to  client
 * @author daniel constantinescu
 */

@RestController
@CrossOrigin
@RequestMapping("/")
public class ClientBatchController {

	@Autowired
	public ClientBatchService cbs ;
	
	/**
	 * call ClientBatch service for
	 * mapping client to batch
	 * @param batchId
	 * @param email
	 * @return String Response and httpStatus
	 */
	
	@PutMapping("/mapBatchToClient")
	public ResponseEntity<?>  MapBatchToClient(@RequestParam  String batchId, @RequestParam String email) {
		
		if(cbs.MapBatchtoClient(batchId, email))
				
			return new ResponseEntity<String>("Map done sucessfuly!", HttpStatus.OK);
			
		else
			
			return new ResponseEntity<String>("Client not found!",  HttpStatus.CONFLICT);
	}
	
	
	  @PutMapping("/unmapBatchFromClient") 
	  public ResponseEntity<?> UnMapBatchFromClient(@RequestParam String batchId, @RequestParam String email){
	  
		  if  (cbs.UnMapBatchFromClient(batchId, email))
		  
			  return new   ResponseEntity<String>("Unmap succesfully!", HttpStatus.OK); 
	  
		  else 
		 
			  return new ResponseEntity<String>("BatchId not found!",HttpStatus.CONFLICT);
	  }
	 
	
	
}
