package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Admin;

/**
 * AdminRepo --- accesses the DB, extends JpaRepository.
 * 
 * @author Brooke Wursten
 */
@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

	/**
	 * @param i admin id
	 * @return Admin object
	 */
	public Admin findByAdminId(Integer i);

	/**
	 * 
	 * @param email
	 * @return Admin object
	 */
	public Admin findByEmail(String email);

	/**
	 * 
	 * @param email
	 * @return boolean saying whether it was successful
	 */
	public boolean deleteByEmail(String email);

}