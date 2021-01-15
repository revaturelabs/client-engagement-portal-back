package com.engagement.controller;

import java.util.List;
import java.util.Map;

import com.engagement.model.dto.Batch;
import com.engagement.repo.caliber.TrainingClient;
import com.engagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Admin;
import com.engagement.model.dto.AdminDto;
import com.engagement.model.dto.BatchName;
import com.engagement.service.AdminService;

import io.swagger.annotations.ApiOperation;

/**
 * AdminController --- backend endpoints for admin/*.
 * 
 * @author Brooke Wursten & Daniel Consantinescu
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	private AdminService as;
	private TrainingClient trainingClient;
	
	@Autowired
	public AdminController(AdminService as , TrainingClient trainingClient) {
		super();
		this.as = as;
		this.trainingClient = trainingClient;
	}
	
	/**
	 * Finds all admin objects - mainly for testing
	 * 
	 * @return List of all admin objects.
	 */
	@ApiOperation(value = "Finds all Admins in the database.")
	@GetMapping("/")
	public List<Admin> findAll() {
		return as.findAll();
	}
	
	/**
	 * Finds admin objects based on email - for use with front end to get admin details from the database
	 * 
	 * @return List of all admin objects.
	 */
	@ApiOperation(value = "Finds an Admin in the database based upon email.")
	@GetMapping("/email/{email:.+}")
	public Admin findAdminByEmail(@PathVariable String email) {
		return as.findByEmail(email);
	}

	/**
	 * Creates a new Admin object and persists to the DB
	 * 
	 * @param admin- the request body should contain a json in the shape of an Admin object
	 * @return ResponseEntity containing status code and message.
	 */
	@ApiOperation(value = "Creates a new Admin object and persists to the DB.", 
			notes = "The request body should contain a json in  the shape of an Admin object.")
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
	@ApiOperation(value = "Updates a Admin object in the DB.",
		notes = "The request body should contain a json in the shape of an Admin object.")
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
	@ApiOperation(value = "Deletes an Admin object from the DB.", 
		notes = "The request body should contain a json in  the shape of an Admin object. Returns a Status code and a message.")
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
	@ApiOperation(value ="Returns a list of all of the batch IDs and names from the Caliber API.")
	@GetMapping("/batch/allNames")
	public List<BatchName> getBatches() {
		return as.getAllBatchNames();
	}
	
	/**
	 * @author Brooke Wursten
	 * Returns a Map showing which batches
	 * are mapped to which clients
	 * @return simple map of <bachid,clientid>
	 */
	@ApiOperation(value = "Returns a map showing which batches are mapped to which clients")
	@GetMapping("/mappedBatchesClients")
	public Map<String,String> mappedBatchesClients() {
			return as.findAllMappings();
	}
	
	/**
	 * map batch to client
	 * @author daniel constantinescu
	 * mapping a client to a batch
	 * @param batchId
	 * @param email
	 * @return Response(String, httpStatus)
	 */
	
	@ApiOperation(value = "Map a batch to a client")
	@PutMapping("/mapBatchToClient")
	public ResponseEntity<String>  mapBatchToClient(@RequestParam  String batchId, @RequestParam String email) {
		
		if(as.mapBatchtoClient(batchId, email))
				
			return new ResponseEntity<>("Map done sucessfuly!", HttpStatus.OK);
			
		else
			
			return new ResponseEntity<>("Client not found!",  HttpStatus.CONFLICT);
	}
	
	/**
	 * Unmap batch from client
	 * @author daniel constantinescu
	 * @param batchId
	 * @param email
	 * @return Response(string, httpStatus)
	 */
	
	  @ApiOperation(value = "UnMap a batch from a client")
	  @PutMapping("/unmapBatchFromClient") 
	  public ResponseEntity<String> unmapBatchFromClient(@RequestParam String batchId, @RequestParam String email){
	  
		  if  (as.unmapBatchFromClient(batchId, email))
		  
			  return new   ResponseEntity<>("Unmap successful!", HttpStatus.OK); 
	  
		  else 
		 
			  return new ResponseEntity<>("BatchId not found!",HttpStatus.CONFLICT);
	  }

	/**
	 * Finds all information about a batch given batchId and returns it for an admin user
	 * @param batchId the identifier in Caliber to find the batch
	 * @return Will return a batch with the matching batchId
	 * @author Cory Sebastian
	 */
  	@ApiOperation(value = "Returns all information about a batch by given id to an admin user")
  	@GetMapping("/batch/{batchId}")
 	 public Batch getBatchById(@PathVariable("batchId") String batchId) {
		return trainingClient.getBatchById(batchId);
  	 }

	/**
	 * Returns all the batches and their information for an admin user
	 * @return A List<Batch> of all the batches
	 * @author Cory Sebastian
	 */
 	@ApiOperation(value = "Returns all the batches along with their information")
 	@GetMapping("/batches")
 	public List<Batch> getAllBatches() {
		return as.getAllBatches();
 	}


	
}
