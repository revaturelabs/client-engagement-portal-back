package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer>{
	
	public Admin findByAdminId(Integer i);
	
	public Admin findByUsername(String username);

}
