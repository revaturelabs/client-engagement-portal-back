package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Client;

import com.engagement.model.ClientDto;
import com.engagement.model.dto.Batch;

import com.engagement.model.dto.ClientName;
import com.engagement.service.ClientService;

/**
 * Controller that handles requests pertaining to clients
 * 
 * @author Tucker Fritz, Matt Hartmann
 */
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
	@GetMapping("/")
	public List<Client> findAll() {
		return cs.findAll();
	}
	
	/**
	 * Saves a client to the database
	 * @param client A client to be saved to the database
	 * @return ResponseEntity containing status code and message.
	 * 
	 * @param c A client to be saved to the database
	 * @return Client that was saved. May be null if client is yet to be persisted to database.

	 */

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
	 * Find a client by clientId
	 * @param id A clientId in the database
	 * @return The client associated with id
	 */
//	@GetMapping("/id")
//	@ResponseBody
//	public Client findById(@RequestParam int id) {
//		return cs.findByClientId(id);
//	}
	
	/**
	 * Find a client by email
	 * 
	 * @param email An email pertaining to a client in the database
	 * @return Client associated with email w/ default values if client is non-existant
	 */

	@GetMapping("/email/{email}")
	public Client findByEmail(@PathVariable("email") String email) {
		return cs.findByEmail(email);
	}
	
	/**
	 * Find all information about a branch by the batch id
	 * @param batchId: The identifier in Caliber to identify a batch
	 * @return Returns the Batch associated with the batchId
	 * @author Kelsey Iafrate

	 */
	@GetMapping("/batch/{batchId}")
	public Batch getBatchById(@PathVariable("batchId") String batchId) {
		return cs.getBatchByBatchId(batchId);
	}
	
	/**
	 * returns clients with just id and name
	 * 
	 * @param none
	 * @return List of clients with id and name
	 */
	@GetMapping("/clientnames")
	@ResponseBody
	public List<ClientName> findClientNames() {
		return cs.ClientNames();
	}
}

