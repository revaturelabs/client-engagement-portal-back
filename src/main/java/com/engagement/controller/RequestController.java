package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Request;
import com.engagement.service.RequestService;

@RestController
@CrossOrigin
public class RequestController {

	@Autowired
	private RequestService rs;

	@GetMapping("/interventions")
	public List<Request> getAllInterventions() {
		return rs.findAll();
	}

	@GetMapping("/interventions")
//	is this neceessary?
//	@ResponseBody
	public Request getInterventionById(@RequestParam int id) {
		return rs.findByRequestId(id);
	}

	@PostMapping("/interventions")
	public String addIntervention(@RequestBody Request request) {
		rs.addIntervention(request);
		return "Intervention request added";

	}
}
