package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Client;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchOverview;


import com.engagement.model.dto.ClientDto;
import com.engagement.model.dto.ClientName;
import com.engagement.service.ClientService;

import io.swagger.annotations.ApiOperation;



/**
 * Controller that handles requests pertaining to clients
 * 
 * @author Tucker Fritz, Matt Hartmann
 */
@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

	private ClientService cs;
	
	@Autowired
	public ClientController(ClientService cs) {
		super();
		this.cs = cs;
	}

	/**
	 * Returns a list of all clients in the database
	 * 
	 * @return List of all clients
	 */
	@ApiOperation(value = "Returns a list of all clients in the DB")
	@GetMapping("/")
	public List<Client> findAll() {
		return cs.findAll();
	}
	
	/**
	 * Saves a client to the database
	 * @param client A client to be saved to the database
	 * @return ResponseEntity containing status code and message.
	*/

	@ApiOperation(value = "Saves a client to the database.", notes= "Returns the client was saved. May return null if client is yet to be persisted to DB.")

	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody ClientDto client) {
		Client persistentClient = new Client(0, client.getEmail(), client.getCompanyName(), client.getPhoneNumber());
		
		if (cs.save(persistentClient)) {
			return new ResponseEntity<>("Client succesfully created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Client creation failed", HttpStatus.CONFLICT);
		}
	}
	
	/**
     * Find a client by email
     * @param email An email pertaining to a client in the database
     * @return Client associated with id w/ default values if client is non-existant
     */
    @GetMapping("/email/{email:.+}")
	@ApiOperation(value = "Returns a Client with email \"email\".")
    public Client findByEmail(@PathVariable String email) {
        return cs.findByEmail(email);
    }

	/**
	 * Find all information about a batch by the batch id
	 * @param batchId: The identifier in Caliber to identify a batch
	 * @return Returns the Batch associated with the batchId
	 * @author Kelsey Iafrate

	 */
	@ApiOperation(value = "Returns All inforation about a batch by given id.")
	@GetMapping("/batch/{batchId}")
	public Batch getBatchById(@PathVariable("batchId") String batchId) {
		return cs.getBatchByBatchId(batchId);
	}
	
	/**
	 * returns clients with just id and name
	 * 
	 * @return List of clients with id and name
	 */
	@ApiOperation(value="Returns a list of all clients with only atributes \"id\" and \"name\".")
	@GetMapping("/clientnames")
	public List<ClientName> findClientNames() {
		return cs.findClientNames();
	}
	
	
	/**
	 * Returns brief overview of batches mapped to a client
	 * 
	 * @param none
	 * @return List of batch overviews with id, name and skill
	 */
	@ApiOperation(value="Returns a list of batch overviews with  \"id\" and \"name\" and \"specialization\".")
	@GetMapping(value="/batch/email/{email:.+}", produces="application/json")
	public List<BatchOverview> getBatchOverviewbyClient(@PathVariable String email) {
		 return cs.getBatchInfoByEmail(email);
	}
}

