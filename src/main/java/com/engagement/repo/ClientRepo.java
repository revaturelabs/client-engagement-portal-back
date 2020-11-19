package com.engagement.repo;


import java.util.List;


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
	
	/**
	 * Auto-generated JPA method for finding client by email
	 * @param email A client email in the database
	 * @return Client associated with email param
	 */
	Client findByEmail(String email);
	
	/**
	 * Returns the specific client with the matching id
	 * @param id
	 * The int id of the client 
	 * @return Returns a Client
	 * 
	 */
	Client findByClientId(int id);

	public boolean deleteByEmail(String email);

}
