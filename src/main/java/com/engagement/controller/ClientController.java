package com.engagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Client;
import com.engagement.model.dto.ClientName;
import com.engagement.service.ClientService;

/**
 * Controller that handles requests pertaining to clients
 * @author Tucker Fritz, Matt H
 */
@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService cs;
	
	/**
	 * Returns a list of all clients in the database
	 * @return List of all clients
	 */
	@GetMapping
	@ResponseBody
	public List<Client> findAll() {
		return cs.findAll();
	}
	
	/**
	 * Saves a client to the database
	 * @param c A client to be saved to the database
	 * @return Client that was saved
	 */
	@PostMapping
	@ResponseBody
	public Client save(@RequestBody Client c) {
		return cs.save(c);
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
	 * @param email An email pertaining to a client in the database
	 * @return Client associated with id w/ default values if client is non-existant
	 */
	@GetMapping("/email")
	@ResponseBody
	public Client findByEmail(@RequestParam String email) {
		return cs.findByEmail(email);
	}
	
	
	/**
	 * returns clients with just id and name
	 * @param none
	 * @return List of clients with id and name
	 */
	@GetMapping("/clientnames")
	@ResponseBody
	public List<ClientName> findClientNames() {
		return cs.ClientNames();
	}
}
