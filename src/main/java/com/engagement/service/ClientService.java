package com.engagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.engagement.model.Client;
import com.engagement.repo.ClientRepo;

/**
 * Service for handling business logic of client requests
 * @author Tucker Fritz
 *
 */
public class ClientService {

		@Autowired
		ClientRepo cr;
		
		/**
		 * Find a client by clientId
		 * @param id A clientId in the database
		 * @return a Client associated with id
		 */
		public Client findByClientId(int id) {
			return cr.findByClientId(id);
		}
		
		/**
		 * Returns a list of all clients in the database
		 * @return List of all clients
		 */
		public List<Client> findAll() {
			return cr.findAll();
		}
		
		/**
		 * Saves a client to the database
		 * @param c A client to be saved to the database
		 * @return Client that was saved
		 */
		public Client save(Client c) {
			return cr.save(c);
		}
		
		/**
		 * Find a client by email
		 * @param email An email pertaining to a client in the database
		 * @return Client associated with id w/ default values if client is non-existant
		 */
		public Client findByEmail(String email) {
			return cr.findByEmail(email);
		}
}
