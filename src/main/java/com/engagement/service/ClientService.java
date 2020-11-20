package com.engagement.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Client;
import com.engagement.model.dto.ClientName;
import com.engagement.repo.ClientRepo;

/**
 * Service for handling business logic of client requests
 * @author Tucker Fritz, Matt Hartmann
 *
 */
@Service
public class ClientService {

		@Autowired
		ClientRepo cr;
		
		/**
		 * Find a client by clientId
		 * @param id A clientId in the database
		 * @return a Client associated with id
		 */
//		public Client findByClientId(int id) {
//			return cr.findByClientId(id);
//		}
		
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
		
		/**
		 * Find all client names
		 * @param none
		 * @return All clients with only number and name
		 */
		public List<ClientName> ClientNames()
		{
			List<Client> clients = cr.findAll();
			List<ClientName> clientsdto = new LinkedList<ClientName>();
			for(int i = 0; i < clients.size(); i++)
				clientsdto.add(new ClientName(String.valueOf(clients.get(i).getClientId()), clients.get(i).getCompanyName()));
			
			return clientsdto;
		}
		
		
}
