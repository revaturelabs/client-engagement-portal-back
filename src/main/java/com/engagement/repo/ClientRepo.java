  
package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

/**
 * This is the repo interface for the Clients. Methods like findAll, save,
 * delete, etc are all provided by the JpaRepository hierarchy
 * 
 * @author enoch cho
 * 
 */
@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
	
	/**
	 * Auto-generated JPA method for finding client by email
	 * 
	 * @param email A client email in the database
	 * @return Client associated with email param
	 */
	public Client findByEmail(String email);

	public boolean deleteByEmail(String email);

}

