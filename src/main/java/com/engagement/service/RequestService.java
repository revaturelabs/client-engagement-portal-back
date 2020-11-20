package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Request;
import com.engagement.repo.RequestRepo;

@Service
public class RequestService {
	@Autowired
	private RequestRepo rr;

	public List<Request> findAll() {
		return rr.findAll();
	}

	public Request addIntervention(Request intervention) {
		return rr.save(intervention);
	}

	public Request findByRequestId(int requestId) {
		return rr.findByRequestId(requestId);
	}

	public void updateRequest(Request request) {
		rr.save(request);
	}

	public void deleteByRequestId(int id) {
		rr.deleteById(id);

	}
}
