package com.engagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Client;
import com.engagement.model.Request;
import com.engagement.model.Request.RequestTypes;
import com.engagement.model.Request.Status;
import com.engagement.model.dto.RequestDto;
import com.engagement.repo.ClientRepo;
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
	private ClientRepo cr;

	@Autowired
	public RequestService(RequestRepo rr, ClientRepo cr) {
		super();
		this.rr = rr;
		this.cr = cr;

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
	public boolean save(RequestDto requestDTO) {

		if (requestDTO == null) {
			return false;
		}

		Client c = cr.findByEmail(requestDTO.getClientEmail());
		Request persistentRequest = new Request(0, RequestTypes.valueOf(requestDTO.getRequestType()),
				Status.valueOf(requestDTO.getStatus()), requestDTO.getMessage(),
				c, null);

		try {
			rr.save(persistentRequest);
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
