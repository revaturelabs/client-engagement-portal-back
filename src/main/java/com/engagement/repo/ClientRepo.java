package com.engagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
	
	public boolean deleteByEmail(String email);
	
	public Client findByEmail(String email);
}
