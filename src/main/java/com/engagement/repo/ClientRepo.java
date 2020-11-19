  
package com.engagement.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

/**
 * This interface defines the methods to access the client table in the database
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

	public boolean deleteByEmail(String email);

}

