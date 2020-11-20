  
package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

/**
<<<<<<< HEAD
 * This interface defines the methods to access the client table in the database
=======
 * This is the repo interface for the Clients. Methods like findAll, save,
 * delete, etc are all provided by the JpaRepository hierarchy
 * 
 * @author enoch cho
 * 
>>>>>>> ca32056a7877ed4adad1796d104a9c54306bdd53
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



