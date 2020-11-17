package com.engagement.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
	
	Client findByClientId(int id);
	Client findByUsername(String username);
}
