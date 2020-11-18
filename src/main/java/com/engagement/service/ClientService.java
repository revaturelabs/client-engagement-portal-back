package com.engagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.engagement.model.Client;
import com.engagement.repo.ClientRepo;

public class ClientService {

		@Autowired
		ClientRepo cr;
		
		public Optional<Client> findById(int id) {
			return cr.findById(id);
		}
		
		public List<Client> findAll() {
			return cr.findAll();
		}
		
		public Client save(Client c) {
			return cr.save(c);
		}
		
		public Client findByEmail(String email) {
			return cr.findByEmail(email);
		}
}
