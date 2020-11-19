package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

/**
 * This is the repo interface for the Clients. 
 *  Methods like findAll, save, delete, etc are all provided by the JpaRepository hierarchy
 * @author enoch cho
 * 
 *
 */
@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
	
	public boolean deleteByEmail(String email);
	
	public Client findByEmail(String email);
}
