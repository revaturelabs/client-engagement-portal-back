package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.ClientBatch;

/**
 * This interface defines the methods to access the batch table in the database
 * This is the repo interface for the ClientBatch. Methods like findAll, save,
 * delete, etc are all provided by the JpaRepository hierarchy
 * 
 * @author daniel constantinescu
 */ 

@Repository
public interface ClientBatchRepo extends JpaRepository<ClientBatch, String> {
	ClientBatch findByBatchId(String id);
	ClientBatch deleteByBatchId(String id);
	
}