package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Request;
import com.engagement.repo.RequestRepo;

/**
 * Service for handling business logic of intervention requests
 * 
 * @author Robert Porto
 *
 */
@Service
public class RequestService {

	@Autowired
	private RequestRepo rr;

	@Autowired
	public RequestService(RequestRepo rr) {
		super();
		this.rr = rr;

	}

	/**
	 * Returns a list of all requests in the database
	 * 
	 * @return List of all requests
	 */
	public List<Request> findAll() {
		return rr.findAll();
	}

	/**
	 * Saves a request to the database
	 * 
	 * @param intervention An intervention to be saved to the database
	 * @return true if success, false if fail
	 */
	public boolean save(Request intervention) {
		if (intervention == null) {
			return false;
		}

		try {
			rr.save(intervention);
			return true;
		} catch (IllegalArgumentException e) {

			return false;
		}
	}

	/**
	 * Find a request by id
	 * 
	 * @param requestId An id pertaining to a request in the database
	 * @return Request associated with requestId
	 */
	public Request findByRequestId(int requestId) {
		return rr.findByRequestId(requestId);
	}

}
