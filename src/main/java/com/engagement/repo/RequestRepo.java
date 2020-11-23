package com.engagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Request;

/**
 * This interface defines the methods to access the request table in the
 * database This is the repo interface for the Requests.
 * 
 * @author Robert Porto
 */

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {
	Request findByRequestId(int requestId);

	List<Request> findAll();

}
